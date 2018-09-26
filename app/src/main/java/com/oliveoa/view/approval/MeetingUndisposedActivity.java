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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.MeetingInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MeetingUndisposedActivity extends AppCompatActivity {
    private ImageView back;
    //申请人，会议主题，会议时间，会议地点，出席人员
    private TextView tApplicant,ttopic,ttime,tplace,tmembers;
    private Button bagree,bdisagree; //同意，不同意
    private MeetingApplication ap;
    private ContactInfoDao contactInfoDao;
    private ContactInfo ci;
    private String TAG = this.getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private int index;
    private ArrayList<MeetingMember> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_undisposed);
        ap = getIntent().getParcelableExtra("ap");
        list = getIntent().getParcelableArrayListExtra("list");
        index = getIntent().getIntExtra("index",index);//0为列表，1为审批意见返回
        Log.i(TAG,"ap="+ap);
        Log.i(TAG,"list="+list);
        Log.i(TAG,"index="+index);
        initView();
    }

    public void initView(){
        ttopic = (TextView) findViewById(R.id.meeting_topic);
        ttime = (TextView) findViewById(R.id.meeting_time);
        tplace = (TextView) findViewById(R.id.meeting_place);
        tmembers = (TextView) findViewById(R.id.members_list);
        back = (ImageView) findViewById(R.id.iback);
        tApplicant = (TextView) findViewById(R.id.name);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);
        loadingDialog = new LoadingDialog(MeetingUndisposedActivity.this,"正在加载数据",true);
        initData();
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                back();
            }
        });

        bagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",41);//1同意
                intent.putExtra("aid",ap.getMaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",40);//0不同意
                intent.putExtra("aid",ap.getMaid());
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
                        approval.setIsapprove(-2);
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
                        approvalDao.insert(approval);
                    }
                    startActivity(new Intent(MeetingUndisposedActivity.this, MyApprovalActivity.class).putExtra("index",4));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取已审批会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    public void initData(){
        ContactInfoDao cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();

        ttopic.setText(ap.getTheme());
        ttime.setText(dateFormat.LongtoDatemm(ap.getBegintime())+"--"+dateFormat.LongtoDatemm(ap.getEndtime()));
        tplace.setText(ap.getPlace());
        ContactInfo contactInfo = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(ap.getEid())).unique();
        if(contactInfo!=null){
            tApplicant.setText(contactInfo.getName());
        }else{
            tApplicant.setText("");
        }

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
