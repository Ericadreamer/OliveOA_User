package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.oliveoa.controller.RecruitmentApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.RecruitmentApplicationItemDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentAndDuty;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecruitmentActivity extends AppCompatActivity {
    private ImageView back,save,delete,dpdtselect;
    private TextView tDepartment,tDuty,taddPerson,tname; //申请部门，招聘岗位，添加审批人
    private EditText eQuantity,ePosition,eDemand,eSalary;  //招聘人数，岗位描述，招聘要求，薪资标准
    private LinearLayout addPersonList;  //审批人列表

    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private RecruitmentApplicationItemDao applicationDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private RecruitmentApplicationItem application;
    private ContactInfo ci;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private DepartmentAndDuty departmentAndDuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);
        index = getIntent().getIntExtra("index",index);//审批人选择1，列表0，
        Log.e("IDDEX=", String.valueOf(index));
        initView(); }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tDuty = (TextView) findViewById(R.id.position);
        dpdtselect = (ImageView) findViewById(R.id.position_list);
        eQuantity = (EditText) findViewById(R.id.quantity);
        ePosition = (EditText) findViewById(R.id.position_content);
        eDemand = (EditText) findViewById(R.id.demand_content);
        eSalary = (EditText) findViewById(R.id.salary);
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
                        departmentAndDutyDao.deleteAll();
                    }
                }
                Intent intent = new Intent(RecruitmentActivity.this,MainApplicationActivity.class);
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

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addPerson();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dpdtselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectdpdt();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        tDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    selectdpdt();
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
        applicationDao = EntityManager.getInstance().getRecruitmentApplicationItemDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        application = new RecruitmentApplicationItem();
        departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
        departmentAndDuty = new DepartmentAndDuty();
        ci = new ContactInfo();
        DateFormat dateFormat= new DateFormat();
        application = applicationDao.queryBuilder().unique();
        departmentAndDuty = departmentAndDutyDao.queryBuilder().unique();
        //Log.e(TAG,application.toString());
        //加载暂存本地表的数据
        if(index==1) {
            eps = approveNumberDao.queryBuilder().list();

            Log.e(TAG,"EPS="+eps);
            if(application!=null){
               eQuantity.setText(String.valueOf(application.getNumber()));
               eDemand.setText(application.getPositionRequest());
               eSalary.setText(application.getSalary());
               ePosition.setText(application.getPositionDescribe());
                Log.e(TAG,"Application="+application.toString());
            }
            if(departmentAndDuty!=null){
                tDuty.setText(departmentAndDuty.getDpname()+""+departmentAndDuty.getPname());
            }

        }
    }

    //添加审批人操作
    private void addPerson() throws ParseException {
        //保存表单数据
        //JobTransferApplication ap = new JobTransferApplication();
        if(application!=null) {
            application.setNumber(Integer.valueOf(eQuantity.getText().toString().trim()));
            application.setSalary(eSalary.getText().toString().trim());
            application.setPositionDescribe(ePosition.getText().toString().trim());
            application.setPositionRequest(eDemand.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);

            LoadingDialog loadingDialog = new LoadingDialog(RecruitmentActivity.this, "正在加载数据", true);
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    //Todo Service
                    UserInfoService userInfoService = new UserInfoService();
                    //Todo Service.Method
                    ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                    departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                    contactInfoDao = EntityManager.getInstance().getContactInfo();
                    departmentInfoDao.deleteAll();
                    contactInfoDao.deleteAll();

                    //ToCheck JsonBean.getStatus()
                    if (contactHttpResponseObject.getStatus() == 0) {
                        ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                        Log.d("userinfo", contactInfos.toString());
                        if (contactInfos.size() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            for (int i = 0; i < contactInfos.size(); i++) {
                                Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                                DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                                departmentInfoDao.insert(departmentInfo);
                                Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                                for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                    if (contactInfos.get(i).getEmpContactList().get(j).getEmployee() != null) {
                                        Log.d(TAG, "contactInfos.get(i).getEmpContactList().get(" + j + ").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                        contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                    }
                                }
                            }
                            Intent intent = new Intent(RecruitmentActivity.this, SelectPersonApprovingActivity.class);
                            intent.putExtra("index", 8);
                            startActivity(intent);
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循7环，查看消息队列
                    }

                }
            }).start();
        }
    }


    //添加职位
    private void selectdpdt() throws ParseException {
        Log.e(TAG,"点击招聘职位");
        //保存表单数据
        if(application!=null) {
            if(eQuantity.getText().toString().trim().equals("")){
                application.setNumber(0);
            }else{
                application.setNumber(Integer.valueOf(eQuantity.getText().toString().trim()));
            }
            if(eSalary.getText().toString().trim().equals("")){
                application.setSalary("0");
            }else{
                application.setSalary(eSalary.getText().toString().trim());
            }
            application.setPositionDescribe(ePosition.getText().toString().trim());
            application.setPositionRequest(eDemand.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);


            LoadingDialog loadingDialog = new LoadingDialog(RecruitmentActivity.this, "正在加载数据", true);
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    //Todo Service
                    UserInfoService userInfoService = new UserInfoService();
                    //Todo Service.Method
                    ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                    departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                    departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
                    departmentInfoDao.deleteAll();
                    departmentAndDutyDao.deleteAll();

                    //ToCheck JsonBean.getStatus()
                    if (contactHttpResponseObject.getStatus() == 0) {
                        ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                        Log.d("userinfo", contactInfos.toString());
                        if (contactInfos.size() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和岗位", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            for (int i = 0; i < contactInfos.size(); i++) {
                                Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                                DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                                departmentInfoDao.insert(departmentInfo);
                            }
                            Intent intent = new Intent(RecruitmentActivity.this, DepartmentSelectActivity.class);
                            intent.putExtra("index", 1);
                            startActivity(intent);
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }


    private void save() throws ParseException {
        //Toast.makeText(getApplicationContext(), "你点击了保存", Toast.LENGTH_SHORT).show();
        if(departmentAndDuty!=null) {
            Log.e(TAG,departmentAndDuty.toString());
            DateFormat dateFormat = new DateFormat();
            if (TextUtils.isEmpty(ePosition.getText().toString().trim())||TextUtils.isEmpty(eDemand.getText().toString().trim())
                    ||TextUtils.isEmpty(eSalary.getText().toString().trim())||TextUtils.isEmpty(eQuantity.getText().toString().trim())){
                Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
            } else if (departmentAndDuty.getDcid() == null || departmentAndDuty.getPcid() == null || departmentAndDuty.getDcid().equals("") || departmentAndDuty.getPcid().equals("")) {
                Toast.makeText(getApplicationContext(), "请选择目标岗位！", Toast.LENGTH_SHORT).show();
            }else if(Integer.parseInt(eSalary.getText().toString().trim())<=0||Integer.parseInt(eQuantity.getText().toString().trim())<=0){
                Toast.makeText(getApplicationContext(), "工资和人数数额需大于0", Toast.LENGTH_SHORT).show();
            } else {
                application.setNumber(Integer.valueOf(eQuantity.getText().toString().trim()));
                application.setSalary(eSalary.getText().toString().trim());
                application.setPositionDescribe(ePosition.getText().toString().trim());
                application.setPositionRequest(eDemand.getText().toString().trim());
                application.setPcid(departmentAndDuty.getPcid());
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

                            RecruitmentApplicationService service = new RecruitmentApplicationService();
                            StatusAndMsgJsonBean statusAndMsgJsonBean = service.submitApplication(s, application,departmentAndDuty.getDcid(),eps.toString());
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
        }else{
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
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
                View EvaluateView = View.inflate(RecruitmentActivity.this, R.layout.item_member, null);
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
