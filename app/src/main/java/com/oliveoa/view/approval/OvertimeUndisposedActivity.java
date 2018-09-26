package com.oliveoa.view.approval;

import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.ContactInfo;
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
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.OvertimeInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OvertimeUndisposedActivity extends AppCompatActivity {

    private ImageView back;
    //申请人，加班时间，加班原因
    private TextView tApplicant,ttime,treason;
    private Button bagree,bdisagree;
    private OvertimeApplication oa;
    private ContactInfoDao contactInfoDao;
    private ContactInfo ci;
    private String TAG = this.getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_undisposed);
        oa = getIntent().getParcelableExtra("oa");
        index = getIntent().getIntExtra("index",index);//0为列表，1为审批意见返回
        Log.i(TAG,"oa="+oa);
        Log.i(TAG,"index="+index);
        initView();
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        tApplicant = (TextView) findViewById(R.id.name);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);

        loadingDialog = new LoadingDialog(OvertimeUndisposedActivity.this,"正在加载数据",true);
        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
              back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OvertimeUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",11);//1同意
                intent.putExtra("aid",oa.getOaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OvertimeUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",10);//0不同意
                intent.putExtra("aid",oa.getOaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void back() {
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
                    startActivity(new Intent(OvertimeUndisposedActivity.this, MyApprovalActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(oa.getBegintime())+"--"+dateFormat.LongtoDatemm(oa.getEndtime()));
        treason.setText(oa.getReason());
        ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(oa.getEid())).unique();

        if(ci!=null) {
            tApplicant.setText(ci.getName());
        }else{
            tApplicant.setText("");
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
