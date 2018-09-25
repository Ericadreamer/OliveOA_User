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

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;
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

public class MeetingInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView ttopic,ttime,tplace,tmembers;  //会议主题，会议时间，会议地点，出席人员
    private TextView tname,tstatus;  //审批进度item，审批人和审批状态
    private LinearLayout addlistView;  //添加审批进度列表

    private ContactInfoDao cidao;
    private ContactInfo ci;
    private MeetingApplication ap;
    private ArrayList<MeetingMember> list;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);

        ap = getIntent().getParcelableExtra("ap");
        list = getIntent().getParcelableArrayListExtra("list");
        index = getIntent().getIntExtra("index",index);
        Log.e(TAG,"index="+index);
        Log.i(TAG,"ap="+ap);
        Log.i(TAG,"list="+list);
        initView();
    }

    public void initView(){
        ttopic = (TextView) findViewById(R.id.meeting_topic);
        ttime = (TextView) findViewById(R.id.time);
        tplace = (TextView) findViewById(R.id.meeting_place);
        tmembers = (TextView) findViewById(R.id.members_list);
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);
        addlistView = (LinearLayout) findViewById(R.id.approve_list);
        loadingDialog = new LoadingDialog(MeetingInfoActivity.this,"正在加载数据",true);

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
                    approvalDao.deleteAll();
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
                    startActivity(new Intent(MeetingInfoActivity.this, MyApprovalActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取已审批会议申请数据失败", Toast.LENGTH_SHORT).show();
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
                MeetingApplicationService service = new MeetingApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();
                //applicationDao.deleteAll();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> statusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0;i<statusAndDataHttpResponseObject.getData().size();i++){
                                application.setAid(statusAndDataHttpResponseObject.getData().get(i).getMaid());
                                application.setDescribe(statusAndDataHttpResponseObject.getData().get(i).getTheme());
                                application.setType(4);
                                    switch (statusAndDataHttpResponseObject.getData().get(i).getIsapproved()) {
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
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(MeetingInfoActivity.this, MyApplicationActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
    private void initData(){
        cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();

        ttopic.setText(ap.getTheme());
        ttime.setText(dateFormat.LongtoDatemm(ap.getBegintime())+"--"+dateFormat.LongtoDatemm(ap.getEndtime()));
        tplace.setText(ap.getPlace());
        ArrayList<String> member = new ArrayList<>();
        if(list!=null) {
            for (int i = 0; i < list.size(); i++) {
                ContactInfo ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(list.get(i).getEid())).unique();
                if (ci != null) {
                    Log.e(TAG,ci.getName());
                    member.add(ci.getName());
                }
            }
            tmembers.setText(member.toString().substring(1,member.toString().length()-1));//字符串toString会出现[]包括对象，故substring对第一个和最后一个括号隐藏
        }else{
            tmembers.setText("");
        }


    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.person_approving);
                    Log.e(TAG,"tname="+tname.getText().toString());
                    String epname =tname.getText().toString().trim();
                    Application application = new Application();
                    application.setDescribe(ap.getOpinion());
                    application.setType(4);
                    application.setAid(ap.getMaid());
                    application.setStatus(ap.getIsapproved());
                    Intent intent = new Intent(MeetingInfoActivity.this, ApprovedInfoActivity.class);
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

        if (ap==null) {
            Toast.makeText(MeetingInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
                View EvaluateView = View.inflate(MeetingInfoActivity.this, R.layout.approveapplication_listitem, null);
                addlistView.addView(EvaluateView);
                InitLADataViewItem();
                sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitLADataViewItem() {
        int i;
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(ap.getAeid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }
            int flag = ap.getIsapproved();
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
