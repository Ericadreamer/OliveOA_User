package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.greendao.RecruitmentApplicationItemDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AddApplicationActivity extends AppCompatActivity {

    private LinearLayout overtime,leave,businessTrip,meeting,dimission,regularWorker,adjustPost,recruitment,goods;
    private ImageView back;
    private LoadingDialog loadingDialog;
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private DutyInfoDao dutyInfoDao;
    private OvertimeApplicationDao overtimeApplicationDao;
    private ArrayList<String> eps;
    private String TAG = this.getClass().getSimpleName();
    private MeetingApplicationDao applicationDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        overtime = (LinearLayout) findViewById(R.id.overtime);
        leave = (LinearLayout) findViewById(R.id.leave);
        businessTrip = (LinearLayout) findViewById(R.id.business);
        meeting = (LinearLayout) findViewById(R.id.meeting);
        dimission = (LinearLayout) findViewById(R.id.dimission);
        regularWorker = (LinearLayout) findViewById(R.id.regular_work);
        adjustPost = (LinearLayout) findViewById(R.id.adjust_post);
        recruitment = (LinearLayout) findViewById(R.id.recruitment);
        //goods = (LinearLayout) findViewById(R.id.goods);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, MainApplicationActivity.class);
                startActivity(intent);
                finish();

            }
        });
        overtime.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addOvertimeApplication();
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
              addLeaveApplication();

            }
        });
        businessTrip.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addBusinessTripApplication();

            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addMeetingApplication();

            }
        });
        dimission.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addLeaveWorkApplication();
            }
        });
        regularWorker.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addFulltimeApplication();
            }
        });
        adjustPost.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addJobTransferApplication();

            }
        });
        recruitment.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addRecruitmentApplication();
            }
        });
       /* goods.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, GoodsActivity.class);
                startActivity(intent);
                finish();

            }
        });*/

    }

    private void addOvertimeApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(AddApplicationActivity.this, OvertimeActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addLeaveApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(AddApplicationActivity.this, LeaveActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addBusinessTripApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(AddApplicationActivity.this, BusinessActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addMeetingApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        applicationDao = EntityManager.getInstance().getMeetingApplicationDao();
                        MeetingApplication application = new MeetingApplication();
                        application.setTheme("");
                        applicationDao.insert(application);
                        Intent intent = new Intent(AddApplicationActivity.this, MeetingActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addLeaveWorkApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(AddApplicationActivity.this, DimissionActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addFulltimeApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                //dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();
                //dutyInfoDao.deleteAll();

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
                            /*DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                            if(dutyInfoJsonBean.getStatus()==0){
                                for(int k=0;k<dutyInfoJsonBean.getData().size();k++){
                                    dutyInfoDao.insert(dutyInfoJsonBean.getData().get(k));
                                }
                            }else{
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                            }*/
                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().get("+j+").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }
                        }
                        Intent intent = new Intent(AddApplicationActivity.this, RegularWorkerActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addJobTransferApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(AddApplicationActivity.this, AdjustPostActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    private void addRecruitmentApplication() {
        LoadingDialog loadingDialog  = new LoadingDialog(AddApplicationActivity.this,"正在加载数据",true);
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
                        RecruitmentApplicationItem applicationItem = new RecruitmentApplicationItem();
                        RecruitmentApplicationItemDao recruitmentApplicationItemDao = EntityManager.getInstance().getRecruitmentApplicationItemDao();
                        recruitmentApplicationItemDao.deleteAll();
                        recruitmentApplicationItemDao.insert(applicationItem);
                        Intent intent = new Intent(AddApplicationActivity.this, RecruitmentActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
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
