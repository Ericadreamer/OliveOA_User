package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.LeaveOfficeApplicationDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class DimissionActivity extends AppCompatActivity {

    private ImageView back,save,delete;
    private EditText eReason,eMatter;  //离职员工编号，离职原因，交代事项
    private TextView tDate,taddPerson,tname;   //离职日期，所属部门职务，添加审批人

    private String tmpdname;
    private String tmppname;

    private DateTimePicker picker;

    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
     private LeaveOfficeApplicationDao applicationDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private LeaveOfficeApplication application;
    private ContactInfo ci;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission);
        index = getIntent().getIntExtra("index",index);//选择1，列表0
        Log.e("IDDEX=", String.valueOf(index));
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        //eNumber = (EditText) findViewById(R.id.employee_num);
        eReason = (EditText) findViewById(R.id.reason);
        eMatter = (EditText) findViewById(R.id.matter);
        tDate = (TextView) findViewById(R.id.dimission_date);
       // tDuty = (TextView) findViewById(R.id.content_dpcid);
        taddPerson = (TextView) findViewById(R.id.person_add);
        addlistView = (LinearLayout) findViewById(R.id.approve_list);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==1){
                    if(eps!=null){
                        approveNumberDao.deleteAll();
                    }
                }
                Intent intent = new Intent(DimissionActivity.this, MainApplicationActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        taddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addPerson();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // 默认添加一个Item
        if(index==1){
            addViewItem(null);
        }

    }


    private void initData() {
        applicationDao = EntityManager.getInstance().getLeaveOfficeApplicationDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        application = new LeaveOfficeApplication();
        ci = new ContactInfo();
        DateFormat dateFormat= new DateFormat();
        if(index==1) {
            eps = approveNumberDao.queryBuilder().list();
            Log.e(TAG,"EPS="+eps);
            application = applicationDao.queryBuilder().unique();
            if(application!=null){
               eMatter.setText(application.getHandoverMatters());
               eReason.setText(application.getReason());

                if(application.getLeavetime()==0){
                    //startTime.setText("");
                }else{
                    tDate.setText(dateFormat.LongtoDatedd(application.getLeavetime()));
                }
            }
        }
    }


    //添加审批人操作
    private void addPerson() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        LeaveOfficeApplication ap = new LeaveOfficeApplication();
        Log.e(TAG,tDate.getText().toString().trim());
        if(tDate.getText().toString().trim().equals("")||tDate.getText().toString().trim().equals("请选择离职日期")){

        }else{
            ap.setLeavetime(dateFormat.stringToLong(tDate.getText().toString().trim(),"yyyy-MM-dd"));
        }
        ap.setHandoverMatters(eMatter.getText().toString().trim());
        ap.setReason(eReason.getText().toString().trim());
        Log.e(TAG,ap.toString());
        applicationDao.deleteAll();
        applicationDao.insert(ap);

        LoadingDialog loadingDialog  = new LoadingDialog(DimissionActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();
                 //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if(contactInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                            DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                            departmentInfoDao.insert(departmentInfo);
                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().get("+j+").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }

                        }
                        Intent intent = new Intent(DimissionActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",5);
                        startActivity(intent);
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }
    private void save() throws ParseException {
        //Toast.makeText(getApplicationContext(), "你点击了保存", Toast.LENGTH_SHORT).show();

        DateFormat dateFormat = new DateFormat();
        if(TextUtils.isEmpty(tDate.getText().toString().trim())
                ||TextUtils.isEmpty(eReason.getText().toString().trim())||TextUtils.isEmpty(eMatter.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
                application.setReason(eReason.getText().toString().trim());
                application.setHandoverMatters(eMatter.getText().toString().trim());
                application.setLeavetime(dateFormat.stringToLong(tDate.getText().toString().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));

                if (eps == null) {
                    Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
                } else if (eps.size() == 0) {
                    Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //读取SharePreferences的cookies
                            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                            String s = pref.getString("sessionid", "");

                            LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                            StatusAndMsgJsonBean statusAndMsgJsonBean = service.submitApplication(s, application, eps.toString());
                            if (statusAndMsgJsonBean.getStatus() == 0) {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "添加成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }

        }
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            final ImageView delete = (ImageView)childAt.findViewById(R.id.delete);

            final int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.leave_type);
                    Log.e(TAG,"确定删除"+tname.getText().toString()+"??");
                    addlistView.removeView(item);
                    approveNumberDao.deleteAll();
                    ApproveNumber employee = new ApproveNumber();
                    int h=0;
                    for(int j=0;j<eps.size();j++){
                        if(!eps.get(j).getId().equals(eps.get(finalI).getId())){
                            employee.setId(eps.get(j).getId());
                            approveNumberDao.insert(employee);
                        }
                    }
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (eps==null) {
            /*Toast.makeText(OvertimeActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();*/
        } else {
            for(int i = 0;i <eps.size(); i ++){
                View EvaluateView = View.inflate(DimissionActivity.this, R.layout.item_member, null);
                addlistView.addView(EvaluateView);
                InitDataViewItem();
            }
            sortViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.leave_type);

            ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(eps.get(i).getId())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }

        }
    }


    //离职日期选择
    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTitleText("离职日期选择");
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2111, 1, 30);
        picker.setRangeStart(2018, 8, 1);
//        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tDate.setText(year + "-" + month + "-" + day);
                // showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 以下部门职务的联动直接从公司端复制过来的，缺少一些东西会标红，所以先注释掉了
     * @param
     */
    /*//多级联动，选择部门职务
    public void onLinkagePicker(View view) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(DimissionActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + ":" + options2Items.get(options1).get(option2);
                 + options3Items.get(options1).get(options2).get(options3).getPickerViewText()
                ;
                tDuty.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)
                .setTitleText("部门职务选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.rgb(0, 178, 238))//设置分割线的颜色
                .setDividerType(com.contrarywind.view.WheelView.DividerType.WRAP)
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleSize(16)
                .setCancelColor(Color.rgb(0, 178, 238))
                .setSubmitColor(Color.rgb(0, 178, 238))
                .setSubCalSize(16)
                .setTextColorCenter(Color.rgb(0, 178, 238))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        //String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        // Toast.makeText(EditEmployeeinfoActivity.this, str, Toast.LENGTH_SHORT).show();
                        tDuty.setText(options1Items.get(options1) + ":" + options2Items.get(options2));
                        tmpdname = options1Items.get(options1);
                        tmppname = options2Items.get(options1).get(options2);
                    }
                })
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    //联动数据获取
    private void getOptionData() {
        DepartmentDAOImpl departmentDAO = new DepartmentDAOImpl(AddEmployeeinfoActivity.this);
        DutyDAOImpl dutyDAO = new DutyDAOImpl(AddEmployeeinfoActivity.this);

        departmentInfos = departmentDAO.getDepartments();
        //选项1
        for (int i = 0; i < departmentInfos.size(); i++) {
            options1Items.add(departmentInfos.get(i).getName());
        }

        //选项2
        for (int i = 0; i < departmentInfos.size(); i++) {
            dutyInfos = dutyDAO.getDutys(departmentInfos.get(i).getDcid());
            //Log.i("EditEmployeeinfo.DTInfo",dutyInfos.toString());
            ArrayList<String> options2Item = new ArrayList<>();
            for (int j = 1; j < dutyInfos.size(); j++) {
                options2Item.add(dutyInfos.get(j).getName());
            }
            options2Items.add(options2Item);
        }

        --------数据源添加完毕---------
    }*/


    //重写showToast
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
