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
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.approval.MyApprovalActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OvertimeInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView tname,tstatus,ttime,treason;

    private OvertimeApplication oa;
    private ArrayList<OvertimeApplicationApprovedOpinionList> oaaol;
    private ContactInfoDao cidao;
    private ContactInfo ci;
    private LinearLayout addOAlistView;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_info);

        oa = getIntent().getParcelableExtra("oa");
        oaaol = getIntent().getParcelableArrayListExtra("oaaol");
        index = getIntent().getIntExtra("index",index);//0申请，1审批
        Log.e(TAG,"index="+index);
        Log.i(TAG,"oa="+oa);
        Log.i(TAG,"oaaol="+oaaol);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        addOAlistView = (LinearLayout)findViewById(R.id.approve_list);

        loadingDialog = new LoadingDialog(OvertimeInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
               loadingDialog.show();
               if(index==0){
                   back();
                }else{
                   backAppoval();
                }
            }
        });

        initData();
        addViewItem(null);

    }

    private void backAppoval() {
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
                    startActivity(new Intent(OvertimeInfoActivity.this, MyApprovalActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
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
                applicationDao = EntityManager.getInstance().getApplicationDao();
               // applicationDao.deleteAll();
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
                            if (oaaol.getOvertimeApplicationApprovedOpinionLists() != null) {
                                Log.d(TAG, "oaaol.getOvertimeApplicationApprovedOpinionLists():" + oaaol.getOvertimeApplicationApprovedOpinionLists().toString());
                                Log.d(TAG, "oaid=" + oaid);
                                application.setAid(oaid);
                                application.setDescribe(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                                application.setType(1);
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
                    startActivity(new Intent(OvertimeInfoActivity.this, MyApplicationActivity.class).putExtra("index",1));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void initData(){
        cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(oa.getBegintime())+"--"+dateFormat.LongtoDatemm(oa.getEndtime()));
        treason.setText(oa.getReason());
        LinearLayout seid = (LinearLayout)findViewById(R.id.seid);
        if(index==0){
            seid.setVisibility(View.GONE);
        }else{
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(oa.getEid())).unique();
            TextView seidname = (TextView)findViewById(R.id.seidname);
            if(ci!=null){
                seidname.setText(ci.getName());
            }
        }
    }
    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addOAlistView.getChildCount(); i++) {
            final View childAt = addOAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.person_approving);
                    Log.e(TAG,"tname="+tname.getText().toString());
                    String epname =tname.getText().toString().trim();
                    Application application = new Application();
                    application.setDescribe(oaaol.get(finalI).getOpinion());
                    if(index==1){
                        application.setType(11);
                    }else{
                        application.setType(1);
                    }
                    application.setAid(oaaol.get(finalI).getOaid());
                    application.setStatus(oaaol.get(finalI).getIsapproved());
                    Log.e(TAG,"APPLICATION="+application.toString());
                    Intent intent = new Intent(OvertimeInfoActivity.this, ApprovedInfoActivity.class);
                    intent.putExtra("epname",epname);
                    intent.putExtra("ap",application);
                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (oaaol==null) {
            Toast.makeText(OvertimeInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <oaaol.size(); i ++){
                View EvaluateView = View.inflate(OvertimeInfoActivity.this, R.layout.approveapplication_listitem, null);
                addOAlistView.addView(EvaluateView);
                InitOADataViewItem();
            }
            sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitOADataViewItem() {
        int i;
        for (i = 0; i < addOAlistView.getChildCount(); i++) {
            View childAt = addOAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);

            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(oaaol.get(i).getEid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }
            int flag = oaaol.get(i).getIsapproved();
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

