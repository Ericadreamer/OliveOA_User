package com.oliveoa.view.notice;

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
import com.oliveoa.controller.AnnouncementService;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.approval.MyApprovalActivity;
import com.oliveoa.view.myapplication.ApprovedInfoActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NoticeInfoActivity extends AppCompatActivity {
    private TextView ttitle,tcontent,tvsignature;
    private LinearLayout addlistView;  //审批人列表
    private TextView tname,tstatus;  //审批进度item，审批人和审批状态
    private ImageView back;
    private AnnouncementInfo announcementInfo;
    private ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionLists;
    private ContactInfo ci;
    private ContactInfoDao cidao;
    private String TAG = this.getClass().getSimpleName();
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_info);
        announcementInfo = getIntent().getParcelableExtra("notice");
        announcementApprovedOpinionLists = getIntent().getParcelableArrayListExtra("list");
        index = getIntent().getIntExtra("index",index);
        Log.e(TAG,announcementInfo.toString());
        Log.e(TAG,announcementApprovedOpinionLists.toString());
        initView();
    }

    public void initView(){
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        addlistView = (LinearLayout)findViewById(R.id.approve_list);
        tvsignature =(TextView)findViewById(R.id.signature);
        initData();

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                if(index==0) {
                    Intent intent = new Intent(NoticeInfoActivity.this, TabLayoutBottomActivity.class);
                    intent.putExtra("index", 1);
                    startActivity(intent);
                    finish();
                }else{
                    backApproval();
                }

            }
        });

        addViewItem(null);
    }

    private void backApproval() {
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
                    approvalDao.deleteAll();
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
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(NoticeInfoActivity.this, MyApprovalActivity.class).putExtra("index",9));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void initData() {
        cidao = EntityManager.getInstance().getContactInfo();

        if(announcementInfo!=null){
            ttitle.setText(announcementInfo.getTitle());
            tcontent.setText(announcementInfo.getContent());
            tvsignature.setText(announcementInfo.getSignature());
        }
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i <addlistView.getChildCount(); i++) {
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
                    application.setDescribe(announcementApprovedOpinionLists.get(finalI).getOpinion());
                    application.setType(9);
                    application.setAid(announcementApprovedOpinionLists.get(finalI).getAid());
                    application.setStatus(announcementApprovedOpinionLists.get(finalI).getIsapproved());
                    Intent intent = new Intent(NoticeInfoActivity.this, ApprovedInfoActivity.class);
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

        if (announcementApprovedOpinionLists==null) {
            Toast.makeText(NoticeInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <announcementApprovedOpinionLists.size(); i ++){
                View EvaluateView = View.inflate(NoticeInfoActivity.this, R.layout.approveapplication_listitem, null);
                 addlistView.addView(EvaluateView);
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
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(announcementApprovedOpinionLists.get(i).getEid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }
            int flag = announcementApprovedOpinionLists.get(i).getIsapproved();
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
