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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LeaveInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tname,ttype,tstatus,ttime,treason;
    private LinearLayout addLAlistView;

    private ContactInfoDao cidao;
    private ContactInfo ci;
    private LeaveApplication la;
    private ArrayList<LeaveApplicationApprovedOpinionList> laaol;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_info);

        la = getIntent().getParcelableExtra("la");
        laaol = getIntent().getParcelableArrayListExtra("laaol");
        Log.i(TAG,"la="+la+"---laaol="+laaol);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        ttype = (TextView) findViewById(R.id.leave_type);
        tstatus = (TextView) findViewById(R.id.status);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        addLAlistView = (LinearLayout)findViewById(R.id.approve_list);
        loadingDialog = new LoadingDialog(LeaveInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
              loadingDialog.show();back();
            }
        });
        initData();
        addViewItem(null);
    }
    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveApplicationService leaveApplicationService = new LeaveApplicationService();
                //加班
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
                            if(laaol.getLeaveApplicationApprovedOpinionLists()!=null) {
                                application.setAid(leaveApplicationInfoJsonBean.getData().get(i).getLaid());
                                application.setDescribe(leaveApplicationInfoJsonBean.getData().get(i).getReason());
                                application.setType(2);
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
                    startActivity(new Intent(LeaveInfoActivity.this, MyApplicationActivity.class).putExtra("index",2));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), leaveApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
    private void initData(){
        cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();

        ttime.setText(dateFormat.LongtoDatemm(la.getBegintime())+"--"+dateFormat.LongtoDatemm(la.getEndtime()));
        treason.setText(la.getReason());
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

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addLAlistView.getChildCount(); i++) {
            final View childAt = addLAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.person_approving);
                    Log.e(TAG,"tname="+tname.getText().toString());
                    String epname =tname.getText().toString().trim();
                    Application application = new Application();
                    application.setDescribe(laaol.get(finalI).getOpinion());
                    application.setType(2);
                    application.setAid(laaol.get(finalI).getLaid());
                    application.setStatus(laaol.get(finalI).getIsapproved());
                    Intent intent = new Intent(LeaveInfoActivity.this, ApprovedInfoActivity.class);
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

        if (laaol==null) {
            Toast.makeText(LeaveInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <laaol.size(); i ++){
                View EvaluateView = View.inflate(LeaveInfoActivity.this, R.layout.approveapplication_listitem, null);
                addLAlistView.addView(EvaluateView);
                InitLADataViewItem();
            }
            sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitLADataViewItem() {
        int i;
        for (i = 0; i < addLAlistView.getChildCount(); i++) {
            View childAt = addLAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(laaol.get(i).getEid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }
            int flag = laaol.get(i).getIsapproved();
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
