package com.oliveoa.view.approval;

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
import android.widget.Toast;

import com.oliveoa.Adapter.ApprovalGridViewAdapter;
import com.oliveoa.Adapter.GridViewAdapter;
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
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationApprovedOpinionListInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.MainApplicationActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainApprovalActivity extends AppCompatActivity {
    ApprovalGridViewAdapter adapter;
    GridView gridView;
    private ImageView back;
    private LoadingDialog loadingDialog;
    private ApprovalDao approvalDao;
    private Approval approval;
    private String  TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_approval);

        adapter = new ApprovalGridViewAdapter(this);

        initView();

    }

    public void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        back = (ImageView) findViewById(R.id.iback);
        loadingDialog = new LoadingDialog(MainApprovalActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainApprovalActivity.this, TabLayoutBottomActivity.class);
                intent.getIntExtra("index", 0);
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
                    {
                        loadingDialog.show();
                        GetMyOvertimeApplicationApproved();
                    }
                    break;
                    case 1://点击图片请假跳转
                    {
                        loadingDialog.show();
                        GetMyLeaveApplicationApproved();
                    }
                    break;
                    case 2://点击图片出差跳转
                    {
                        loadingDialog.show();
                        GetMyBussinessApplicationApproved();
                    }
                    break;
                    case 3://点击图片会议跳转
                    {
                        loadingDialog.show();
                        GetMyMeetingApplicationApproved();
                    }
                    break;
                    case 4://点击图片离职跳转
                    {
                        loadingDialog.show();
                        GetMyDismissionApplicationApproved();
                    }
                    break;
                    case 5://点击图片转正跳转
                    {
                        loadingDialog.show();
                        GetMyRegularApplicationApproved();
                    }
                    break;
                    case 6://点击图片调岗跳转
                    {
                        loadingDialog.show();
                        GetMyAdjustApplicationApproved();
                    }
                    break;
                    case 7://点击图片招聘跳转
                    {
                        loadingDialog.show();
                        GetMyRecruitmentApplicationApproved();
                    }
                    case 8://点击图片招聘跳转
                    {
                        loadingDialog.show();
                        GetMyRecruitmentApplicationApproved();
                    }
                    case 9://点击图片招聘跳转
                    {
                        loadingDialog.show();
                        GetMyNoticeApproved();
                    }
                    break;
                    default:
                        break;
                }
            }

        });
    }

    private void GetMyOvertimeApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                //加班
               approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
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
                    approvalDao.deleteAll();
                    for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                        approval.setAid( overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                        approval.setSeid(overtimeApplicationInfoJsonBean.getData().get(i).getEid());
                        approval.setContent(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                        approval.setStatus(1);
                        approval.setType(1);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();

    }

    private void GetMyLeaveApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveApplicationService service = new LeaveApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
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
                        approval.setAid( infoJsonBean.getData().get(i).getLaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(1);
                        approval.setType(2);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyBussinessApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                BusinessTripApplicationService service = new BusinessTripApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getBtaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getTask());
                        approval.setStatus(1);
                        approval.setType(3);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyMeetingApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getMaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getTheme());
                        approval.setStatus(1);
                        approval.setType(4);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取已审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyDismissionApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
                int i = 0;
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getLoaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(1);
                        approval.setType(5);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyRegularApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                FulltimeApplicationService service = new FulltimeApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
                int i = 0;
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getFaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getPersonalSummary());
                        approval.setStatus(1);
                        approval.setType(6);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",6));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyAdjustApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                JobTransferApplicationService service = new JobTransferApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
                int i = 0;
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getJtaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(1);
                        approval.setType(7);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",7));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批调岗申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetMyRecruitmentApplicationApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                RecruitmentApplicationService service = new RecruitmentApplicationService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
                int i = 0;
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
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> applcaitoninfo = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getRaid());
                        if(applcaitoninfo.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getRaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(applcaitoninfo.getData().getRecruitmentApplicationItem().getPositionDescribe());
                            approval.setStatus(1);
                            approval.setType(8);
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                            break;
                        }
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",8));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void  GetMyNoticeApproved() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                AnnouncementService service = new AnnouncementService();
                //审批
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approval = new Approval();
                int i = 0;
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
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",9));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                /*infoJsonBean = service.get_approvedannoucement(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getAid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getContent());
                        approval.setStatus(1);
                        approval.setType(9);
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",9));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }*/
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
