package com.oliveoa.view.approval;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.StatusJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.JobTransferApplicationApprovedOpinion;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.pojo.RecruitmentApplicationApprovedOpinion;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.ApprovedInfoActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;
import com.oliveoa.view.myapplication.OvertimeActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;

public class ApprovalAdviseActivity extends AppCompatActivity {

    private ImageView back,save;
    private int index;
    private EditText econtent;
    private View TextArea;
    private String aid;
    private int isApproved;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private ContactInfoDao contactInfoDao;
    private String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_advise);

        index = getIntent().getIntExtra("index",index);//十位数1-9 各申请及公告类型，个位数0为不同意，1为同意
        aid = getIntent().getStringExtra("aid");
        Log.i(TAG,"index="+index);
        Log.i(TAG,"aid="+aid);
        initView();

    }
    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        TextArea = (LinesEditView)findViewById(R.id.reason);
        econtent = TextArea.findViewById(R.id.id_et_input);


        LinesEditView linesEditView = new LinesEditView(ApprovalAdviseActivity.this);
        String test = linesEditView.getContentText();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void back() {
        LoadingDialog loadingDialog = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        if(index==10||index==11){
            getovertimeapplicationinfo();
        }
        if(index==20||index==21){
            getleaveapplicationinfo();
        }
        if(index==30||index==31){
            getbusinessapplicationinfo();
        }
        if(index==40||index==41){
            getmeetingapplicationinfo();
        }
        if(index==50||index==51){
            getdimissionapplicationinfo();
        }
        if(index==60||index==61){
            getregularapplicationinfo();
        }
        if(index==70||index==71){
            getadjustapplicationinfo();
        }
        if(index==80||index==81){
            getrecruitmentapplicationinfo();
        }
        if(index==90||index==91){
            getnoticceinfo();
        }
    }

    private void save() {
        Log.e(TAG,econtent.getText().toString().trim());
        if(TextUtils.isEmpty(econtent.getText().toString().trim())||econtent.getText().toString().trim().equals("请输入内容")){
            Toast.makeText(getApplicationContext(), "信息不得为空", Toast.LENGTH_SHORT).show();
        }else{
            LoadingDialog loadingDialog = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
            loadingDialog.show();
            if(index==10||index==11){
                switch (index){
                    case 10:isApproved=-1;break;
                    case 11:isApproved =1;break;
                    default:break;
                }
                saveovertimeapplicationapproval();
            }
            if(index==20||index==21){
                switch (index){
                    case 20:isApproved=-1;break;
                    case 21:isApproved =1;break;
                    default:break;
                }
                saveleaveapplicationapproval();
            }
            if(index==30||index==31){
                switch (index){
                    case 30:isApproved=-1;break;
                    case 31:isApproved =1;break;
                    default:break;
                }
                savebusinessapplicationapproval();
            }
            if(index==40||index==41){
                switch (index){
                    case 40:isApproved=-1;break;
                    case 41:isApproved =1;break;
                    default:break;
                }
                savemeetingapplicationapproval();
            }
            if(index==50||index==51){
                switch (index){
                    case 50:isApproved=-1;break;
                    case 51:isApproved =1;break;
                    default:break;
                }
                savedimissionapplicationapproval();
            }
            if(index==60||index==61){
                switch (index){
                    case 60:isApproved=-1;break;
                    case 61:isApproved =1;break;
                    default:break;
                }
                saveregularapplicationapproval();
            }
            if(index==70||index==71){
                switch (index){
                    case 70:isApproved=-1;break;
                    case 71:isApproved =1;break;
                    default:break;
                }
                saveadjustapplicationapproval();
            }
            if(index==80||index==81){
                switch (index){
                    case 80:isApproved=-1;break;
                    case 81:isApproved =1;break;
                    default:break;
                }
                saverecruitmentapplicationapproval();
            }
            if(index==90||index==91){
                switch (index){
                    case 90: isApproved=-1;break;
                    case 91: isApproved=1;break;
                    default:break;
                }
                savenoticceapproval();
            }
        }

    }
    private void saveovertimeapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                OvertimeApplicationApprovedOpinionList approval = new OvertimeApplicationApprovedOpinionList();
                approval.setIsapproved(isApproved);
                approval.setOaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                OvertimeApplictionService service = new OvertimeApplictionService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approvedapplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    OvertimeApplicationInfoJsonBean overtimeApplicationInfoJsonBean = service.unapprovedotapplication(s); //获取待我审核的申请
                    Log.e(TAG, overtimeApplicationInfoJsonBean.toString());
                    if (overtimeApplicationInfoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                            ap.setAid( overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                            ap.setSeid(overtimeApplicationInfoJsonBean.getData().get(i).getEid());
                            ap.setContent(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                            ap.setStatus(0);
                            ap.setType(1);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",1));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    overtimeApplicationInfoJsonBean = service.approvedotapplication(s); //获取我已经审核的申请
                    Log.e(TAG, overtimeApplicationInfoJsonBean.toString());
                    if (overtimeApplicationInfoJsonBean.getStatus() == 0) {
                        for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                            OvertimeApplicationHttpResponseObject httpResponseObject = service.overtimeapplication(s,overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                            if(httpResponseObject.getStatus()==0){
                                ap.setAid( overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                                ap.setSeid(overtimeApplicationInfoJsonBean.getData().get(i).getEid());
                                ap.setContent(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                                ap.setStatus(1);
                                ap.setType(1);
                                for (j = 0;j<httpResponseObject.getOvertimeApplicationJsonBean().getOvertimeApplicationApprovedOpinionLists().size();j++){
                                    switch (httpResponseObject.getOvertimeApplicationJsonBean().getOvertimeApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }else{
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getApplicationContext(), httpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",1));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();

    }



    private void saveleaveapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                LeaveApplicationApprovedOpinionList approval = new LeaveApplicationApprovedOpinionList();
                approval.setIsapproved(isApproved);
                approval.setLaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                LeaveApplicationService service = new LeaveApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approvedapplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    LeaveApplicationJsonBean infoJsonBean = service.getlapplicationunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getLaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getReason());
                            ap.setStatus(0);
                            ap.setType(2);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
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
                                ap.setAid(infoJsonBean.getData().get(i).getLaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getReason());
                                ap.setStatus(1);
                                ap.setType(2);
                                for (j = 0; j < httpResponseObject.getData().getLeaveApplicationApprovedOpinionLists().size(); j++) {
                                    switch (httpResponseObject.getData().getLeaveApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",2));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),infoJsonBean .getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();

    }

    private void savebusinessapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                BusinessTripApplicationApprovedOpinionList approval = new BusinessTripApplicationApprovedOpinionList();
                approval.setIsapproved(isApproved);
                approval.setBtaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                BusinessTripApplicationService service = new BusinessTripApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approvedbtapplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    BusinessTripApplicationJsonBean infoJsonBean = service.getbtapplicationunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getBtaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getTask());
                            ap.setStatus(0);
                            ap.setType(3);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getbtapplicationapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            BusinessTripApplicationHttpResponseObject httpResponseObject = service.getbtapplicationinfo(s,infoJsonBean.getData().get(i).getBtaid());
                            if(httpResponseObject.getStatus()==0) {
                                ap.setAid(infoJsonBean.getData().get(i).getBtaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getTask());
                                ap.setStatus(1);
                                ap.setType(3);
                                for (j = 0; j < httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                    switch (httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",3));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),infoJsonBean .getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();

    }

    private void savemeetingapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                MeetingApplication approval = new MeetingApplication();
                approval.setIsapproved(isApproved);
                approval.setMaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG, approval.toString());
                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approveApplication(s, approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid(infoJsonBean.getData().get(i).getMaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getTheme());
                            ap.setStatus(0);
                            ap.setType(4);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid(infoJsonBean.getData().get(i).getMaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getTheme());
                            ap.setStatus(1);
                            ap.setType(4);
                            switch (infoJsonBean.getData().get(i).getIsapproved()) {
                                case -2:
                                    ap.setIsapprove(-2);
                                    break;
                                case -1:
                                    ap.setIsapprove(-1);
                                    break;
                                case 0:
                                    ap.setIsapprove(0);
                                    break;
                                case 1:
                                    ap.setIsapprove(1);
                                    break;
                                default:
                                    break;
                            }
                            apDao.insert(ap);
                        }
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index", 4));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取已审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                }
            }
            }).start();
    }

    private void savedimissionapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                LeaveOfficeApplicationApprovedOpinion approval = new LeaveOfficeApplicationApprovedOpinion();
                approval.setIsapproved(isApproved);
                approval.setLoaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approveApplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getLoaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getReason());
                            ap.setStatus(0);
                            ap.setType(5);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getLoaid());
                            if(httpResponseObject.getStatus()==0) {
                                ap.setAid(infoJsonBean.getData().get(i).getLoaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getReason());
                                ap.setStatus(1);
                                ap.setType(5);
                                for (j = 0; j < httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().size(); j++) {
                                    switch (httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",5));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void saveregularapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                FulltimeApplicationApprovedOpinion approval = new FulltimeApplicationApprovedOpinion();
                approval.setIsapproved(isApproved);
                approval.setFaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                FulltimeApplicationService service = new FulltimeApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approveApplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    StatusAndDataHttpResponseObject<ArrayList<FulltimeApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getFaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getPersonalSummary());
                            ap.setStatus(0);
                            ap.setType(6);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getFaid());
                            if(httpResponseObject.getStatus()==0) {
                                ap.setAid(infoJsonBean.getData().get(i).getFaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getPersonalSummary());
                                ap.setStatus(1);
                                ap.setType(6);
                                for (j = 0; j < httpResponseObject.getData().getFulltimeApplicationApprovedOpinionList().size(); j++) {
                                    switch (httpResponseObject.getData().getFulltimeApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",6));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"获取已审批转正申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void saveadjustapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                JobTransferApplicationApprovedOpinion approval = new JobTransferApplicationApprovedOpinion();
                approval.setIsapproved(isApproved);
                approval.setJtaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                JobTransferApplicationService service = new JobTransferApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approveApplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    StatusAndDataHttpResponseObject<ArrayList<JobTransferApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getJtaid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getReason());
                            ap.setStatus(0);
                            ap.setType(7);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批调岗申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getJtaid());
                            if(httpResponseObject.getStatus()==0) {
                                ap.setAid(infoJsonBean.getData().get(i).getJtaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getReason());
                                ap.setStatus(1);
                                ap.setType(7);
                                for (j = 0; j < httpResponseObject.getData().getJobTransferApplicationApprovedOpinionList().size(); j++) {
                                    switch (httpResponseObject.getData().getJobTransferApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",7));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"获取已审批调岗申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void saverecruitmentapplicationapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                RecruitmentApplicationApprovedOpinion approval = new RecruitmentApplicationApprovedOpinion();
                approval.setIsapproved(isApproved);
                approval.setRaid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                RecruitmentApplicationService service = new RecruitmentApplicationService();
                //Todo Service.Method
                StatusAndMsgJsonBean statusAndMsgJsonBean = service.approveApplication(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    StatusAndDataHttpResponseObject<ArrayList<RecruitmentApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> applcaitoninfo = service.getApplicationInfo(s, infoJsonBean.getData().get(i).getRaid());
                            if (applcaitoninfo.getStatus() == 0) {
                                ap.setAid(infoJsonBean.getData().get(i).getRaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(applcaitoninfo.getData().getRecruitmentApplicationItem().getPositionDescribe());
                                ap.setStatus(0);
                                ap.setType(8);
                                ap.setIsapprove(-2);
                                apDao.insert(ap);
                            } else {
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getApplicationContext(), "获取待审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                                break;
                            }
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",8));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> applcaitoninfo = service.getApplicationInfo(s, infoJsonBean.getData().get(i).getRaid());
                            if (applcaitoninfo.getStatus() == 0) {
                                StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> httpResponseObject = service.getApplicationInfo(s, infoJsonBean.getData().get(i).getRaid());
                                if (httpResponseObject.getStatus() == 0) {
                                    ap.setAid(infoJsonBean.getData().get(i).getRaid());
                                    ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                    ap.setContent(applcaitoninfo.getData().getRecruitmentApplicationItem().getPositionDescribe());
                                    ap.setStatus(1);
                                    ap.setType(8);
                                    for (j = 0; j < httpResponseObject.getData().getRecruitmentApplicationApprovedOpinions().size(); j++) {
                                        switch (httpResponseObject.getData().getRecruitmentApplicationApprovedOpinions().get(j).getIsapproved()) {
                                            case -2:
                                                ap.setIsapprove(-2);
                                                break;
                                            case -1:
                                                ap.setIsapprove(-1);
                                                break;
                                            case 0:
                                                ap.setIsapprove(0);
                                                break;
                                            case 1:
                                                ap.setIsapprove(1);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                    apDao.insert(ap);
                                } else {
                                    Looper.prepare();//解决子线程弹toast问题
                                    Toast.makeText(getApplicationContext(), "获取已审批申请详情数据失败", Toast.LENGTH_SHORT).show();
                                    Looper.loop();// 进入loop中的循环，查看消息队列
                                }
                            } else {
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getApplicationContext(), "获取已审批招聘申请数据失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                                break;
                            }
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",8));
                }else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
            }
        }).start();
    }

    private void savenoticceapproval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                AnnouncementApprovedOpinionList approval = new AnnouncementApprovedOpinionList();
                approval.setIsapproved(isApproved);
                approval.setAid(aid);
                approval.setOpinion(econtent.getText().toString().trim());
                Log.e(TAG,approval.toString());
                //Todo Service
                AnnouncementService service = new AnnouncementService();
                //Todo Service.Method
                StatusJsonBean statusAndMsgJsonBean = service.approved_annoucements(s,approval);

                //ToCheck JsonBean.getStatus()
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                    Approval ap = new Approval();
                    int i, j = 0;
                    AnnouncementJsonBean infoJsonBean = service.get_unapprovedannoucement(s); //获取待我审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        apDao.deleteAll();
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            ap.setAid( infoJsonBean.getData().get(i).getAid());
                            ap.setSeid(infoJsonBean.getData().get(i).getEid());
                            ap.setContent(infoJsonBean.getData().get(i).getContent());
                            ap.setStatus(0);
                            ap.setType(9);
                            ap.setIsapprove(-2);
                            apDao.insert(ap);
                        }
                        //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "获取待审批公告数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    infoJsonBean = service.get_approvedannoucement(s); //获取我已经审核的申请
                    Log.e(TAG, infoJsonBean.toString());
                    if (infoJsonBean.getStatus() == 0) {
                        for (i = 0; i < infoJsonBean.getData().size(); i++) {
                            StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> httpResponseObject = service.get_annoucementinfo(s,infoJsonBean.getData().get(i).getAid());
                            if(httpResponseObject.getStatus()==0) {
                                ap.setAid(infoJsonBean.getData().get(i).getAid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getContent());
                                ap.setStatus(1);
                                ap.setType(9);
                                for (j = 0; j < httpResponseObject.getData().getAnnouncementApprovedOpinionList().size(); j++) {
                                    switch (httpResponseObject.getData().getAnnouncementApprovedOpinionList().get(j).getIsapproved()) {
                                        case -2:
                                            ap.setIsapprove(-2);
                                            break;
                                        case -1:
                                            ap.setIsapprove(-1);
                                            break;
                                        case 0:
                                            ap.setIsapprove(0);
                                            break;
                                        case 1:
                                            ap.setIsapprove(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                apDao.insert(ap);
                            }
                        }
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ApprovalAdviseActivity.this, MyApprovalActivity.class).putExtra("index",9));
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"获取已审批公告数据失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"保存失败，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }


    private void getovertimeapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                OvertimeApplicationHttpResponseObject overtimeApplicationHttpResponseObject = overtimeApplictionService.overtimeapplication(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (overtimeApplicationHttpResponseObject.getStatus() == 0) {
                    OvertimeApplicationJsonBean overtimeApplicationJsonBean = overtimeApplicationHttpResponseObject.getOvertimeApplicationJsonBean();
                    OvertimeApplication overtimeApplication = overtimeApplicationJsonBean.getOvertimeApplications();
                    ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionLists  = overtimeApplicationJsonBean.getOvertimeApplicationApprovedOpinionLists();
                    startActivity( new Intent(getApplicationContext(), OvertimeUndisposedActivity.class)
                            .putExtra("oa",overtimeApplication)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),overtimeApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getleaveapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                LeaveApplicationService leaveApplicationService = new LeaveApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                LeaveApplicationHttpResponseObject leaveApplicationHttpResponseObject = leaveApplicationService.getlapplicationinfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (leaveApplicationHttpResponseObject.getStatus() == 0) {
                    LeaveApplicationInfoJsonBean leaveApplicationInfoJsonBean = leaveApplicationHttpResponseObject.getData();
                    LeaveApplication leaveApplication =leaveApplicationInfoJsonBean.getLeaveApplication();
                    ArrayList<LeaveApplicationApprovedOpinionList> leaveApplicationApprovedOpinionLists  = leaveApplicationInfoJsonBean.getLeaveApplicationApprovedOpinionLists();
                    startActivity( new Intent(getApplicationContext(), LeaveUndisposedActivity.class)
                            .putExtra("la",leaveApplication)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),leaveApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getbusinessapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (businessTripApplicationHttpResponseObject.getStatus() == 0) {
                    BusinessTripApplicationInfoJsonBean businessTripApplicationInfoJsonBean  = businessTripApplicationHttpResponseObject.getData();
                    BusinessTripApplication businessTripApplication = businessTripApplicationInfoJsonBean.getBusinessTripApplication();
                    ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists = businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists();
                    startActivity( new Intent(getApplicationContext(), BusinessUndisposedActivity.class)
                            .putExtra("bta",businessTripApplication)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),businessTripApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getmeetingapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    MeetingApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    MeetingApplication meetingApplication = aaol.getMeetingApplication();
                    ArrayList<MeetingMember> list = aaol.getMeetingMembers();
                    startActivity( new Intent(getApplicationContext(), MeetingUndisposedActivity.class)
                            .putExtra("ap",meetingApplication)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getdimissionapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    LeaveOfficeApplicationJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    LeaveOfficeApplication ap = aaol.getLeaveOfficeApplication();
                    ArrayList<LeaveOfficeApplicationApprovedOpinion> list = aaol.getLeaveOfficeApplicationApprovedOpinionList();
                    startActivity( new Intent(getApplicationContext(), DimissionUndisposedActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getregularapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                FulltimeApplicationService service = new FulltimeApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    FulltimeApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    FulltimeApplication ap = aaol.getFulltimeApplication();
                    ArrayList<FulltimeApplicationApprovedOpinion> list = aaol.getFulltimeApplicationApprovedOpinionList();
                    startActivity( new Intent(getApplicationContext(), RegularWorkerUndisposedActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getadjustapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                JobTransferApplicationService service = new JobTransferApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    JobTransferApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    JobTransferApplication ap = aaol.getJobTransferApplication();
                    ArrayList<JobTransferApplicationApprovedOpinion> list = aaol.getJobTransferApplicationApprovedOpinionList();
                    startActivity( new Intent(getApplicationContext(), AdjustPostUndisposedActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取调岗申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getrecruitmentapplicationinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                RecruitmentApplicationService service = new RecruitmentApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    RecruitmentApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    RecruitmentApplication ap = aaol.getRecruitmentApplication();
                    RecruitmentApplicationItem apitem = aaol.getRecruitmentApplicationItem();
                    ArrayList<RecruitmentApplicationApprovedOpinion> list = aaol.getRecruitmentApplicationApprovedOpinions();
                    startActivity( new Intent(getApplicationContext(), RecruitmentUndisposedActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("apitem",apitem)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取招聘申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
    private void getnoticceinfo() {
        LoadingDialog loadingDialog  = new LoadingDialog(ApprovalAdviseActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                AnnouncementService service = new AnnouncementService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> statusAndDataHttpResponseObject = service.get_annoucementinfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    AnnouncementInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    AnnouncementInfo ap = aaol.getAnnouncement();
                    ArrayList<AnnouncementApprovedOpinionList> list = aaol.getAnnouncementApprovedOpinionList();
                    startActivity( new Intent(getApplicationContext(), NoticeUndisposedActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",0));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }


}
