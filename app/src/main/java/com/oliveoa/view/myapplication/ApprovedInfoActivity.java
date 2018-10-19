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
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.AnnouncementService;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.FulltimeApplicationService;
import com.oliveoa.controller.JobTransferApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.RecruitmentApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.AnnouncementInfoDao;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.approval.MyApprovalActivity;
import com.oliveoa.view.notice.NoticeInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ApprovedInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tname,tstatus,topinion;
    private String epname;
    private int index;

    private Application ap;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_info);

        epname = getIntent().getStringExtra("epname");
        ap = getIntent().getParcelableExtra("ap");

        Log.d(TAG,"EPNAME="+epname+"---------APPLICATION="+ap);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.tname);
        tstatus = (TextView) findViewById(R.id.status);
        topinion = (TextView)findViewById(R.id.opinion);
        loadingDialog = new LoadingDialog(ApprovedInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                back();
            }
        });

        initData();

    }
    private void initData(){
        if(ap.getDescribe()==null){
            topinion.setText("无");
        }else{
            topinion.setText(ap.getDescribe());
        }
        tname.setText(epname);

        int flag = ap.getStatus();
        switch (flag) {
            case -2:
                tstatus.setText("待审核");
                tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tstatus.setText("不同意");
                tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 1:
                tstatus.setText("同意");
                tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 0:
                tstatus.setText("正在审核");
                tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;
        }
    }

    private void back() {
        if(ap.getType()==1){
            backMyOvertimeApplication();
        }
        if(ap.getType()==2){
            backMyLeaveApplication();
        }
        if(ap.getType()==3){
            backMyBusinessApplication();
        }
        if(ap.getType()==4){
            backMyMeetingApplication();
        }
        if(ap.getType()==5){
            backMyDimissApplication();
        }
        if(ap.getType()==6){
            backMyRegularApplication();
        }
        if(ap.getType()==7){
            backMyAdjustApplication();
        }
        if(ap.getType()==8){
            backMyRecruitmentApplication();
        }
        if(ap.getType()==9){
            LoadingDialog loadingDialog  = new LoadingDialog(ApprovedInfoActivity.this,"正在加载数据",true);
            loadingDialog.show();
            backMyNotice();
        }
        if(ap.getType()==11){
            backMyOvertimeApproval();
        }
        if(ap.getType()==12){
            backMyLeaveApproval();
        }
        if(ap.getType()==13){
            backMyBusinessApproval();
        }
        if(ap.getType()==14){
            backMyMeetingApproval();
        }
        if(ap.getType()==15){
            backMyDimissApproval();
        }
        if(ap.getType()==16){
            backMyRegularApproval();
        }
        if(ap.getType()==17){
            backMyAdjustApproval();
        }
        if(ap.getType()==18){
            backMyRecruitmentApproval();
        }
        if(ap.getType()==19){
            LoadingDialog loadingDialog  = new LoadingDialog(ApprovedInfoActivity.this,"正在加载数据",true);
            loadingDialog.show();
            backMyNoticeApproval();
        }

    }

    private void backMyOvertimeApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                //加班
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i, j = 0;
                OvertimeApplicationInfoJsonBean overtimeApplicationInfoJsonBean = overtimeApplictionService.unapprovedotapplication(s); //获取待我审核的申请
                Log.e(TAG, overtimeApplicationInfoJsonBean.toString());
                if (overtimeApplicationInfoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                        approval.setAid( overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                        approval.setSeid(overtimeApplicationInfoJsonBean.getData().get(i).getEid());
                        approval.setContent(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                        approval.setStatus(0);
                        approval.setType(1);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                overtimeApplicationInfoJsonBean = overtimeApplictionService.approvedotapplication(s); //获取我已经审核的申请
                Log.e(TAG, overtimeApplicationInfoJsonBean.toString());
                if (overtimeApplicationInfoJsonBean.getStatus() == 0) {
                    for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                        OvertimeApplicationHttpResponseObject httpResponseObject = overtimeApplictionService.overtimeapplication(s,overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                            approval.setSeid(overtimeApplicationInfoJsonBean.getData().get(i).getEid());
                            approval.setContent(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                            approval.setStatus(1);
                            approval.setType(1);
                            for (j = 0;j<httpResponseObject.getOvertimeApplicationJsonBean().getOvertimeApplicationApprovedOpinionLists().size();j++){
                                switch (httpResponseObject.getOvertimeApplicationJsonBean().getOvertimeApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), httpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyLeaveApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveApplicationService service = new LeaveApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i, j = 0;
                LeaveApplicationJsonBean infoJsonBean = service.getlapplicationunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getLaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(0);
                        approval.setType(2);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getlapplicationapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        LeaveApplicationHttpResponseObject httpResponseObject = service.getlapplicationinfo(s,infoJsonBean.getData().get(i).getLaid());
                        if(httpResponseObject.getStatus()==0) {
                            approval.setAid(infoJsonBean.getData().get(i).getLaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getReason());
                            approval.setStatus(1);
                            approval.setType(2);
                            for (j = 0; j < httpResponseObject.getData().getLeaveApplicationApprovedOpinionLists().size(); j++) {
                                switch (httpResponseObject.getData().getLeaveApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",2));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyBusinessApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                BusinessTripApplicationService service = new BusinessTripApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i, j = 0;
                BusinessTripApplicationJsonBean infoJsonBean = service.getbtapplicationunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getBtaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getTask());
                        approval.setStatus(0);
                        approval.setType(3);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);

                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getbtapplicationapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        BusinessTripApplicationHttpResponseObject httpResponseObject = service.getbtapplicationinfo(s, infoJsonBean.getData().get(i).getBtaid());
                        if (httpResponseObject.getStatus() == 0) {
                            approval.setAid(infoJsonBean.getData().get(i).getBtaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getTask());
                            approval.setStatus(1);
                            approval.setType(3);
                            for (j = 0; j < httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                switch (httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyMeetingApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getMaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getTheme());
                        approval.setStatus(0);
                        approval.setType(4);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取待审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getMaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getTheme());
                        approval.setStatus(1);
                        approval.setType(4);
                        switch (infoJsonBean.getData().get(i).getIsapproved()) {
                            case -2:
                                approval.setStatus(-2);
                                break;
                            case -1:
                                approval.setStatus(-1);
                                break;
                            case 0:
                                approval.setStatus(0);
                                break;
                            case 1:
                                approval.setStatus(1);
                                break;
                            default:
                                break;
                        }
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取已审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyDimissApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i,j= 0;
                StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getLoaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(0);
                        approval.setType(5);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getLoaid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getLoaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getReason());
                            approval.setStatus(1);
                            approval.setType(5);

                            for (j = 0; j < httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().size(); j++) {
                                switch (httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyRegularApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                FulltimeApplicationService service = new FulltimeApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i,j= 0;
                StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getFaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getPersonalSummary());
                        approval.setStatus(0);
                        approval.setType(6);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",6));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getFaid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getFaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getPersonalSummary());
                            approval.setStatus(1);
                            approval.setType(6);
                            for (j = 0; j < httpResponseObject.getData().getFulltimeApplicationApprovedOpinionList().size(); j++) {
                                switch (httpResponseObject.getData().getFulltimeApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",6));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyAdjustApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                JobTransferApplicationService service = new JobTransferApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i,j = 0;
                StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getJtaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(0);
                        approval.setType(7);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",7));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批调岗申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getJtaid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getJtaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getReason());
                            approval.setStatus(1);
                            approval.setType(7);
                            for (j = 0; j < httpResponseObject.getData().getJobTransferApplicationApprovedOpinionList().size(); j++) {
                                switch (httpResponseObject.getData().getJobTransferApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",7));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批调岗申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyRecruitmentApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                RecruitmentApplicationService service = new RecruitmentApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i ,j= 0;
                StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> applcaitoninfo = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getRaid());
                        if(applcaitoninfo.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getRaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(applcaitoninfo.getData().getRecruitmentApplicationItem().getPositionDescribe());
                            approval.setStatus(0);
                            approval.setType(8);
                            approval.setIsapprove(-2);
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取待审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                            break;
                        }
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",8));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> applcaitoninfo = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getRaid());
                        if(applcaitoninfo.getStatus()==0){
                            StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getRaid());
                            if(httpResponseObject.getStatus()==0){
                                approval.setAid( infoJsonBean.getData().get(i).getRaid());
                                approval.setSeid(infoJsonBean.getData().get(i).getEid());
                                approval.setContent(applcaitoninfo.getData().getRecruitmentApplicationItem().getPositionDescribe());
                                approval.setStatus(1);
                                approval.setType(8);
                                for (j = 0; j < httpResponseObject.getData().getRecruitmentApplicationApprovedOpinions().size(); j++) {
                                    switch (httpResponseObject.getData().getRecruitmentApplicationApprovedOpinions().get(j).getIsapproved()) {
                                        case -2:
                                            approval.setIsapprove(-2);
                                            break;
                                        case -1:
                                            approval.setIsapprove(-1);
                                            break;
                                        case 0:
                                            approval.setIsapprove(0);
                                            break;
                                        case 1:
                                            approval.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                approvalDao.insert(approval);
                            }else{
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                            break;
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",8));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyNoticeApproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                AnnouncementService service = new AnnouncementService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i ,j= 0;
                AnnouncementJsonBean infoJsonBean = service.get_unapprovedannoucement(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getAid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getContent());
                        approval.setStatus(0);
                        approval.setType(9);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    // startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",9));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.get_approvedannoucement(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> httpResponseObject = service.get_annoucementinfo(s,infoJsonBean.getData().get(i).getAid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getAid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getContent());
                            approval.setStatus(1);
                            approval.setType(9);

                            for (j = 0; j < httpResponseObject.getData().getAnnouncementApprovedOpinionList().size(); j++) {
                                switch (httpResponseObject.getData().getAnnouncementApprovedOpinionList().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApprovalActivity.class).putExtra("index",9));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyNotice() {
           new Thread(new Runnable() {
                       @Override
                       public void run() {
                           SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                           String s = pref.getString("sessionid","");

                           //Todo Service
                           UserInfoService userInfoService = new UserInfoService();
                           //Todo Service.Method
                           ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                           DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                           ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
                           AnnouncementInfoDao announcementInfoDao = EntityManager.getInstance().getAnnouncementInfoDao();
                           departmentInfoDao.deleteAll();
                           contactInfoDao.deleteAll();
                           announcementInfoDao.deleteAll();
                           //ToCheck JsonBean.getStatus()
                           if(contactHttpResponseObject.getStatus()==0){
                               ArrayList<ContactJsonBean> contactJsonBean = contactHttpResponseObject.getData();
                               for(int i =0;i<contactJsonBean.size();i++){
                                   departmentInfoDao.insert(contactJsonBean.get(i).getDepartment());
                                   for(int j=0;j<contactJsonBean.get(i).getEmpContactList().size();j++){
                                       contactInfoDao.insert(contactJsonBean.get(i).getEmpContactList().get(j).getEmployee());
                                   }
                               }
                           }else{
                               Looper.prepare();//解决子线程弹toast问题
                               Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                               Looper.loop();// 进入loop中的循环，查看消息队列
                           }
                           AnnouncementService announcementService = new AnnouncementService();
                           AnnouncementJsonBean announcementJsonBean = announcementService.get_published_annoucements(s);
                           if(announcementJsonBean.getStatus()==0){
                               List<AnnouncementInfo> announcementInfos = announcementJsonBean.getData();
                               Log.e(TAG,announcementInfos.toString());
                               for (int i=0;i<announcementInfos.size();i++){
                                   announcementInfoDao.insert(announcementInfos.get(i));
                               }
                               Intent intent = new Intent(ApprovedInfoActivity.this, TabLayoutBottomActivity.class);
                               intent.putExtra("index",1);
                               startActivity(intent);
                               finish();
                           }else{
                               Looper.prepare();//解决子线程弹toast问题
                               Toast.makeText(getApplicationContext(), "网络错误，获取公告信息失败", Toast.LENGTH_SHORT).show();
                               Looper.loop();// 进入loop中的循环，查看消息队列
                           }

                       }
                   }).start();
    }

    private void backMyOvertimeApplication() {
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
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyLeaveApplication() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
               LeaveApplicationService leaveApplicationService = new LeaveApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                //Todo Method
                LeaveApplicationJsonBean leaveApplicationJsonBean = leaveApplicationService.getlapplicationsubmited(s);
                Log.e(TAG,"leaveApplicationJsonBean="+leaveApplicationJsonBean.toString());
                if (leaveApplicationJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0; i <leaveApplicationJsonBean.getData().size(); i++) {
                        LeaveApplicationHttpResponseObject lahro = leaveApplicationService.getlapplicationinfo(s,leaveApplicationJsonBean.getData().get(i).getLaid());
                        Log.e(TAG, "lahro=" +lahro.toString());

                        if (lahro.getStatus() == 0) {
                            LeaveApplicationInfoJsonBean la = lahro.getData();
                            Log.d(TAG, "la" + la.toString());
                            application.setAid(leaveApplicationJsonBean.getData().get(i).getLaid());
                            application.setDescribe(leaveApplicationJsonBean.getData().get(i).getReason());
                            application.setType(2);
                            if (la.getLeaveApplicationApprovedOpinionLists() != null) {
                                Log.d(TAG, "btaaol.getOvertimeApplicationApprovedOpinionLists():" +la.getLeaveApplicationApprovedOpinionLists().toString());

                                for (j = 0; j < la.getLeaveApplicationApprovedOpinionLists().size(); j++) { //循环审核列表
                                    int flag = la.getLeaveApplicationApprovedOpinionLists().get(j).getIsapproved();
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
                            Toast.makeText(getApplicationContext(), lahro.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",2));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), leaveApplicationJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyBusinessApplication() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                //Todo Method
                BusinessTripApplicationJsonBean businessTripApplicationJsonBean =businessTripApplicationService.getbtapplicationsubmited(s);
                Log.e(TAG,"businessTripApplicationJsonBean="+businessTripApplicationJsonBean.toString());
                if (businessTripApplicationJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0; i <businessTripApplicationJsonBean.getData().size(); i++) {
                        BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject =businessTripApplicationService.getbtapplicationinfo(s,businessTripApplicationJsonBean.getData().get(i).getBtaid());
                        Log.e(TAG, "businessTripApplicationHttpResponseObject=" +businessTripApplicationHttpResponseObject.toString());

                        if (businessTripApplicationHttpResponseObject.getStatus() == 0) {
                            BusinessTripApplicationInfoJsonBean businessTripApplicationInfoJsonBean = businessTripApplicationHttpResponseObject.getData();
                            Log.d(TAG, "bta" + businessTripApplicationInfoJsonBean.toString());
                            application.setAid(businessTripApplicationJsonBean.getData().get(i).getBtaid());
                            application.setDescribe(businessTripApplicationJsonBean.getData().get(i).getTask());
                            application.setType(3);
                            if (businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists() != null) {
                                Log.d(TAG, "btaaol.getOvertimeApplicationApprovedOpinionLists():" +businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists().toString());

                                for (j = 0; j < businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists().size(); j++) { //循环审核列表
                                    int flag = businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved();
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
                            Toast.makeText(getApplicationContext(), businessTripApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        applicationDao.insert(application);
                        Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), businessTripApplicationJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyMeetingApplication() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                //Todo Method
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> statusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                Log.e(TAG,"statusAndDataHttpResponseObject="+statusAndDataHttpResponseObject.toString());
                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0; i <statusAndDataHttpResponseObject.getData().size(); i++) {
                       StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> jsonbean = service.getApplicationInfo(s,statusAndDataHttpResponseObject.getData().get(i).getMaid());
                        Log.e(TAG, "jsonbean=" +jsonbean.toString());
                        if (jsonbean.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = jsonbean.getData();
                            Log.i(TAG, "aaol:" + aaol);
                        application.setAid(statusAndDataHttpResponseObject.getData().get(i).getMaid());
                        application.setDescribe(statusAndDataHttpResponseObject.getData().get(i).getTheme());
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
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void backMyDimissApplication() {
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
                            Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getLoaid());
                            application.setDescribe(arrayListStatusAndDataHttpResponseObject.getData().get(i).getReason());
                            application.setType(5);
                            if (aaol.getLeaveOfficeApplicationApprovedOpinionList() != null) {

                                for (j=0;j<aaol.getLeaveOfficeApplicationApprovedOpinionList().size();j++) {
                                    switch (aaol.getLeaveOfficeApplicationApprovedOpinionList().get(j).getIsapproved()) {
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
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void backMyRegularApplication() {
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
                            if (aaol.getFulltimeApplicationApprovedOpinionList() != null) {

                                for (j=0;j<aaol.getFulltimeApplicationApprovedOpinionList().size();j++) {
                                    switch (aaol.getFulltimeApplicationApprovedOpinionList().get(j).getIsapproved()) {
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
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",6));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void backMyAdjustApplication() {
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
                            if (aaol.getJobTransferApplicationApprovedOpinionList() != null) {

                                for (j=0;j<aaol.getJobTransferApplicationApprovedOpinionList().size();j++) {
                                    switch (aaol.getJobTransferApplicationApprovedOpinionList().get(j).getIsapproved()) {
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
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",7));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    private void backMyRecruitmentApplication() {
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
                            Log.i(TAG, "aaol:" + aaol);
                            application.setAid(arrayListStatusAndDataHttpResponseObject.getData().get(i).getRaid());
                            application.setDescribe(aaol.getRecruitmentApplicationItem().getPositionDescribe());
                            application.setType(8);
                            if (aaol.getRecruitmentApplicationItem() != null) {

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
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(ApprovedInfoActivity.this, MyApplicationActivity.class).putExtra("index",8));
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
