package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.Adapter.GridViewAdapter;
import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.FulltimeApplicationService;
import com.oliveoa.controller.JobTransferApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.RecruitmentApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplicationActivity extends AppCompatActivity {
    GridViewAdapter adapter;
    GridView gridView;
    private ImageView back,add;
    private TextView tvtitle;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application ;
    private  LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_application);


        adapter = new GridViewAdapter(this);

        initView();
        initData();
    }

    public void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        back = (ImageView)findViewById(R.id.iback);
        add = (ImageView)findViewById(R.id.iadd);
        tvtitle =(TextView)findViewById(R.id.tvtitle);
        loadingDialog = new LoadingDialog(MainApplicationActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainApplicationActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainApplicationActivity.this, AddApplicationActivity.class);
                /*intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfos);
                intent.putExtra("index",departmentInfos.size());
                setAddDepartmentinfo(departmentInfos.size());*/
                startActivity(intent);
                finish();
            }
        });

        //九宫格跳转
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0://点击图片加班跳转
                    { loadingDialog.show();
                        GetMyOvertimeApplicationSubmited();
                    }
                    break;
                    case 1://点击图片请假跳转
                    { loadingDialog.show();
                        GetMyLeaveApplicationSubmited();
                    }
                    break;
                    case 2://点击图片出差跳转
                    { loadingDialog.show();
                        GetMyBussinessApplicationSubmited();
                    }
                    break;
                    case 3://点击图片会议跳转
                    { loadingDialog.show();
                        GetMyMeetingApplicationSubmited();
                    }
                    break;
                    case 4://点击图片离职跳转
                    { loadingDialog.show();
                      GetMyDismissionApplicationSubmited();
                     }
                    break;
                    case 5://点击图片转正跳转
                    { loadingDialog.show();
                       GetMyRegularApplicationSubmited();
                           }
                    break;
                    case 6://点击图片调岗跳转
                    {
                        loadingDialog.show();
                        GetMyAdjustApplicationSubmited();
                    }
                    break;
                    case 7://点击图片招聘跳转
                    {
                       loadingDialog.show();
                        GetMyRecruitmentApplicationSubmited();
                    }
                    break;
                    default:
                    break;
                }
            }

        });
    }


    public void initData() {

    }

    private void GetMyOvertimeApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                //加班
                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                OvertimeApplicationInfoJsonBean overtimeApplicationInfoJsonBean = overtimeApplictionService.submitotapplication(s); //获取我提交的申请
                Log.e(TAG, overtimeApplicationInfoJsonBean.toString());
                if (overtimeApplicationInfoJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                        OvertimeApplicationHttpResponseObject oahro = overtimeApplictionService.overtimeapplication(s, overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                        Log.e(TAG, "oahro" + oahro.toString());
                        String oaid = overtimeApplicationInfoJsonBean.getData().get(i).getOaid();
                        if (oahro.getStatus() == 0) {
                            OvertimeApplicationJsonBean oaaol = oahro.getOvertimeApplicationJsonBean(); //获取oaid的审核列表结果及oaid申请详情
                            Log.d(TAG, "oaaol" + oaaol.toString());
                            application.setAid(oaid);
                            application.setDescribe(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                            application.setType(1);
                            if (oaaol.getOvertimeApplicationApprovedOpinionLists() != null) {
                                Log.d(TAG, "oaaol.getOvertimeApplicationApprovedOpinionLists():" + oaaol.getOvertimeApplicationApprovedOpinionLists().toString());
                                Log.d(TAG, "oaid=" + oaid);
                                 for (j = 0; j < oaaol.getOvertimeApplicationApprovedOpinionLists().size(); j++) { //循环审核列表
                                    int flag = oaaol.getOvertimeApplicationApprovedOpinionLists().get(j).getIsapproved();
                                    switch (flag) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), oahro.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyLeaveApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                LeaveApplicationService leaveApplicationService = new LeaveApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                LeaveApplicationJsonBean leaveApplicationInfoJsonBean = leaveApplicationService.getlapplicationsubmited(s);
                if (leaveApplicationInfoJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0;i<leaveApplicationInfoJsonBean.getData().size();i++){
                        LeaveApplicationHttpResponseObject leaveApplicationHttpResponseObject = leaveApplicationService.getlapplicationinfo(s,leaveApplicationInfoJsonBean.getData().get(i).getLaid());
                        //String laid = leaveApplicationInfoJsonBean.getData().get(i).getOaid();
                        if(leaveApplicationHttpResponseObject.getStatus()==0){
                            LeaveApplicationInfoJsonBean laaol = leaveApplicationHttpResponseObject.getData();
                            Log.i(TAG,"laaol:"+laaol);
                            application.setAid(leaveApplicationInfoJsonBean.getData().get(i).getLaid());
                            application.setDescribe(leaveApplicationInfoJsonBean.getData().get(i).getReason());
                            application.setType(2);
                            if(laaol.getLeaveApplicationApprovedOpinionLists()!=null) {
                                for (j = 0; j < laaol.getLeaveApplicationApprovedOpinionLists().size(); j++) {
                                    int flag = laaol.getLeaveApplicationApprovedOpinionLists().get(j).getIsapproved();
                                    switch (flag) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),leaveApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",2));
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),leaveApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyBussinessApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");


                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                BusinessTripApplicationJsonBean businessTripApplicationJsonBean = businessTripApplicationService.getbtapplicationsubmited(s);
                if (businessTripApplicationJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < businessTripApplicationJsonBean.getData().size(); i++) {
                        BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s, businessTripApplicationJsonBean.getData().get(i).getBtaid());
                        if (businessTripApplicationHttpResponseObject.getStatus() == 0) {
                            BusinessTripApplicationInfoJsonBean btaaol = businessTripApplicationHttpResponseObject.getData();
                            Log.i(TAG, "btaaol:" + btaaol);
                            application.setAid(businessTripApplicationJsonBean.getData().get(i).getBtaid());
                            application.setDescribe(businessTripApplicationJsonBean.getData().get(i).getTask());
                            application.setType(3);
                            if (btaaol.getBusinessTripApplicationApprovedOpinionLists() != null) {
                                for (j = 0; j < btaaol.getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                    int flag = btaaol.getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved();
                                    switch (flag) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), businessTripApplicationJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void GetMyMeetingApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                MeetingApplicationService service = new MeetingApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);

                                application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                                application.setDescribe(arrayListStatusAndDataHttpResponseObject.getData().get(i).getTheme());
                                application.setType(4);
                                    switch (aaol.getMeetingApplication().getIsapproved()) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                            }
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void GetMyDismissionApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");


                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                ArrayList<String> member = null;
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getLoaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            LeaveOfficeApplicationJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            //Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getLoaid());
                            application.setDescribe(arrayListStatusAndDataHttpResponseObject.getData().get(i).getReason());
                            application.setType(5);
                            if (aaol.getLeaveOfficeApplicationApprovedOpinions() != null) {
                                Log.i(TAG, "aaol.getLeaveOfficeApplicationApprovedOpinions()====" + aaol.getLeaveOfficeApplicationApprovedOpinions());
                                for (j=0;j<aaol.getLeaveOfficeApplicationApprovedOpinions().size();j++) {
                                    switch (aaol.getLeaveOfficeApplicationApprovedOpinions().get(j).getIsapproved()) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            applicationDao.insert(application);
                            //Log.e(TAG,"application["+i+"]-------"+application.toString());
                        }

                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void GetMyRegularApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                FulltimeApplicationService service = new FulltimeApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();

                List<Application> ap = new ArrayList<>();
                application = new Application();
                ArrayList<String> member = null;
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getFaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            FulltimeApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getFaid());
                            application.setDescribe(arrayListStatusAndDataHttpResponseObject.getData().get(i).getPersonalSummary());
                            application.setType(6);
                            if (aaol.getFulltimeApplicationApprovedOpinions() != null) {
                                for (j=0;j<aaol.getFulltimeApplicationApprovedOpinions().size();j++) {
                                    switch (aaol.getFulltimeApplicationApprovedOpinions().get(j).getIsapproved()) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        applicationDao.insert(application);
                        Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",6));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void GetMyAdjustApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                JobTransferApplicationService service = new JobTransferApplicationService ();
                applicationDao = EntityManager.getInstance().getApplicationDao();

                List<Application> ap = new ArrayList<>();
                application = new Application();
                ArrayList<String> member = null;
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(
                                                                                                                                        s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getJtaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            JobTransferApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getJtaid());
                            application.setDescribe(arrayListStatusAndDataHttpResponseObject.getData().get(i).getReason());
                            application.setType(7);
                            if (aaol.getJobTransferApplicationApprovedOpinions() != null) {
                                for (j=0;j<aaol.getJobTransferApplicationApprovedOpinions().size();j++) {
                                    switch (aaol.getJobTransferApplicationApprovedOpinions().get(j).getIsapproved()) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        applicationDao.insert(application);
                        Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",7));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void GetMyRecruitmentApplicationSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");


                RecruitmentApplicationService service = new RecruitmentApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();

                List<Application> ap = new ArrayList<>();
                application = new Application();
                ArrayList<String> member = null;
                int i, j = 0;

                StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getRaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            RecruitmentApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                           // Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getRaid());
                            application.setDescribe(aaol.getRecruitmentApplicationItem().getPositionDescribe());
                            application.setType(8);
                            if (aaol.getRecruitmentApplicationItem() != null) {
                                Log.i(TAG, "aaol:" + aaol);
                                for (j=0;j<aaol.getRecruitmentApplicationApprovedOpinions().size();j++) {
                                    switch (aaol.getRecruitmentApplicationApprovedOpinions().get(j).getIsapproved()) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        applicationDao.insert(application);
                        Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MainApplicationActivity.this, MyApplicationActivity.class).putExtra("index",8));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
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



