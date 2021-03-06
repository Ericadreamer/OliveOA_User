package com.oliveoa.view.workschedule;

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

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.SelectPersonApprovingActivity;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class ProtocolWorkActivity extends AppCompatActivity {

    private TextView tstartTime,tendTime,taddPerson,tname;    //开始时间，结束时间,批阅人
    private ImageView back,save,next;
    private EditText econtent;
    private View textAreaView;
    private DateTimePicker picker;

    //private LinearLayout addlistView;
    private LinearLayout tmember;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private WorkDetail workDetail;
    private WorkDetailDao workDetailDao;
    private ContactInfo ci;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_work);
        index = getIntent().getIntExtra("index",index);//0为列表 1为员工选择
        initView();
    }

    private void initView() {
        tstartTime = (TextView) findViewById(R.id.start);
        tendTime = (TextView) findViewById(R.id.end);
        textAreaView = (LinesEditView)findViewById(R.id.reason);
        econtent = textAreaView.findViewById(R.id.id_et_input);

        next = (ImageView) findViewById(R.id.inext);
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tname = (TextView)findViewById(R.id.approve_person);
        initData();

       /* LinesEditView linesEditView = new LinesEditView(ProtocolWorkActivity.this);
        String test = linesEditView.getContentText();*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog  = new LoadingDialog(ProtocolWorkActivity.this,"正在加载数据",true);
                loadingDialog.show();
                back();
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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addPerson();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref =getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                Log.d(TAG,"cookie=="+s);
                WorkDetailService service = new WorkDetailService();
                StatusAndMsgAndDataHttpResponseObject<ArrayList<WorkDetail>> statusAndMsgAndDataHttpResponseObject = service.getsubmitedwork(s, 0);
                WorkdetailAndStatus workdetailAndStatus = new WorkdetailAndStatus();
                WorkdetailAndStatusDao workdetailAndStatusDao = EntityManager.getInstance().getWorkdetailAndStatusDao();
                workdetailAndStatusDao.deleteAll();
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    List<WorkDetail> workDetails = statusAndMsgAndDataHttpResponseObject.getData();
                    Log.e(TAG, workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for (int i = 0; i < workDetails.size(); i++) {
                        workdetailAndStatus.setWaid(workDetails.get(i).getSwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(0);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    // startActivity(new Intent(getActivity(), MyWorkActivity.class));

                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取我的拟定工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                StatusAndMsgAndDataHttpResponseObject<ArrayList<IssueWork>> isswork = service.getIssueworktome(s,0);
                if (isswork.getStatus()==0){
                    List<IssueWork> workDetails = isswork.getData();
                    Log.e(TAG,workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for(int i =0;i<workDetails.size();i++){
                        workdetailAndStatus.setWaid(workDetails.get(i).getIwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(1);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    startActivity(new Intent(ProtocolWorkActivity.this, MyWorkActivity.class));
                    finish();
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取分配与我工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void initData() {
        workDetailDao = EntityManager.getInstance().getWorkDetailDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        workDetail = new WorkDetail();
        ci = new ContactInfo();
        DateFormat dateFormat= new DateFormat();
        if(index==1) {
            workDetail = workDetailDao.queryBuilder().unique();
            if(workDetail!=null){
                econtent.setText(workDetail.getContent());
                if(workDetail.getBegintime()==0){
                    //startTime.setText("");
                }else{
                    tstartTime.setText(dateFormat.LongtoDatedd(workDetail.getBegintime()));
                }
                if(workDetail.getEndtime()==0){
                    //startTime.setText("");
                }else{
                    tendTime.setText(dateFormat.LongtoDatedd(workDetail.getEndtime()));
                }
                if(workDetail.getAeid()!=null&&!workDetail.getAeid().equals("")) {
                    ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(workDetail.getAeid())).unique();
                    if (ci != null) {
                        tname.setText(ci.getName());
                    }
                }
            }
        }
    }

    //添加审批人操作
    private void addPerson() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        WorkDetail work = new WorkDetail();
        Log.e(TAG,tstartTime.getText().toString().trim()+"-------"+tendTime.getText().toString().trim());
        if(tstartTime.getText().toString().trim().equals("")){

        }else{
            work.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim(),"yyyy-MM-dd"));
        }
        if(tendTime.getText().toString().trim().equals("")){

        }else{
            work.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim(),"yyyy-MM-dd"));
        }
        work.setContent(econtent.getText().toString().trim());
        Log.e(TAG,work.toString());
        workDetailDao.deleteAll();
        workDetailDao.insert(work);

        LoadingDialog loadingDialog  = new LoadingDialog(ProtocolWorkActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(ProtocolWorkActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",12);
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

    private void save()throws ParseException  {
        DateFormat dateFormat = new DateFormat();
        if(TextUtils.isEmpty(tstartTime.getText().toString().trim())
                ||TextUtils.isEmpty(econtent.getText().toString().trim())||TextUtils.isEmpty(tendTime.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            workDetail.setContent(econtent.getText().toString().trim());
            workDetail.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            workDetail.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));

            if (workDetail.getAeid() == null) {
                Toast.makeText(getApplicationContext(), "请选择批阅人", Toast.LENGTH_SHORT).show();
            } else if (workDetail.getAeid().equals("")) {
                Toast.makeText(getApplicationContext(), "请选择批阅人", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");
                        Log.e(TAG,"S="+s);
                        WorkDetailService service = new WorkDetailService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.submitwork(s,workDetail);
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            LoadingDialog loadingDialog  = new LoadingDialog(ProtocolWorkActivity.this,"正在加载数据",true);
                            loadingDialog.show();
                            back();
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "拟定工作成功！", Toast.LENGTH_LONG).show();
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

    //开始时间
    public void onYearMonthDayPicker1(View view) {
        tendTime.setText("");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy", Locale.CHINA);// 输出北京时间
        SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.CHINA);//
        SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.CHINA);// 输出北京时间

        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2025, 12, 31);
        picker.setRangeStart(2018,1,1);
        picker.setSelectedItem(Integer.parseInt(yyyy.format(date)),Integer.parseInt(mm.format(date)),Integer.parseInt(dd.format(date)));
        picker.setResetWhileWheel(false);
        picker.setLabelTextColor(Color.GRAY);
        picker.setTopLineColor(Color.GRAY);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("选择开始时间");
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tstartTime.setText(year + "-" + month + "-" + day);
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

    //结束时间
    public void onYearMonthDayPicker2(View view) {
        if(!tstartTime.getText().toString().equals("")) {
            final DatePicker picker = new DatePicker(this);
            picker.setCanceledOnTouchOutside(true);
            picker.setUseWeight(true);
            picker.setTopPadding(ConvertUtils.toPx(this, 10));
            picker.setRangeEnd(2111, 12, 31);
            picker.setRangeStart(Integer.parseInt(tstartTime.getText().toString().trim().substring(0, 4)), Integer.parseInt(tstartTime.getText().toString().trim().substring(5, 7)), Integer.parseInt(tstartTime.getText().toString().trim().substring(8, 10)));
            //picker.setSelectedItem(2050, 10, 14);
            picker.setResetWhileWheel(false);
            picker.setLabelTextColor(Color.GRAY);
            picker.setTopLineColor(Color.GRAY);
            picker.setSubmitTextSize(16);
            picker.setCancelTextSize(16);
            picker.setTitleTextColor(Color.BLACK);
            picker.setTitleText("选择结束时间");
            picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    tendTime.setText(year + "-" + month + "-" + day);
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
        }else{
            showToast("请选择开始时间！");
        }
   }

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
        Timer tExit ;
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
