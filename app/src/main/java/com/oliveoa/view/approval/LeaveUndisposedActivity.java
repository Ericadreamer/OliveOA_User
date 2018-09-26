package com.oliveoa.view.approval;

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

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LeaveUndisposedActivity extends AppCompatActivity {

    private ImageView back;
    //申请人，请假类型，请假时间，请假原因
    private TextView tApplicant,ttype,ttime,treason;
    private Button bagree,bdisagree;
    private LeaveApplication la;
    private ContactInfoDao contactInfoDao;
    private ContactInfo ci;
    private String TAG = this.getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private int index;
    private DutyInfoDao dutyInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_undisposed);
        la = getIntent().getParcelableExtra("la");
        index = getIntent().getIntExtra("index",index);//0为列表，1为审批意见返回
        Log.i(TAG,"la="+la);
        Log.i(TAG,"index="+index);
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tApplicant = (TextView) findViewById(R.id.name);
        ttime = (TextView) findViewById(R.id.time);
        ttype = (TextView) findViewById(R.id.leave_type);
        treason = (TextView) findViewById(R.id.reason);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);

        loadingDialog = new LoadingDialog(LeaveUndisposedActivity.this,"正在加载数据",true);
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
                Intent intent = new Intent(LeaveUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",21);//1同意
                intent.putExtra("aid",la.getLaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",20);//0不同意
                intent.putExtra("aid",la.getLaid());
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
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                LeaveApplicationService service = new LeaveApplicationService();
                //Todo Service.Method
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
                    startActivity(new Intent(LeaveUndisposedActivity.this, MyApprovalActivity.class).putExtra("index",2));
                    Looper.loop();// 进入loop中的循环，查看消息队列
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),infoJsonBean .getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(la.getBegintime())+"--"+dateFormat.LongtoDatemm(la.getEndtime()));
        treason.setText(la.getReason());
        ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(la.getEid())).unique();

        if(ci!=null) {
            tApplicant.setText(ci.getName());
        }else{
            tApplicant.setText("");
        }

        switch(la.getType()){
            case 1:
                ttype.setText("事假");
                break;
            case 2:
                ttype.setText("病假");
                break;
            case 3:
                ttype.setText("婚假");
                break;
            case 4:
                ttype.setText("丧假");
                break;
            case 5:
                ttype.setText("公假");
                break;
            case 6:
                ttype.setText("年假");
                break;
            case 7:
                ttype.setText("产假");
                break;
            case 8:
                ttype.setText("护理假");
                break;
            case 9:
                ttype.setText("工伤假");
                break;
            default:
                ttype.setText("其他");
                break;
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
