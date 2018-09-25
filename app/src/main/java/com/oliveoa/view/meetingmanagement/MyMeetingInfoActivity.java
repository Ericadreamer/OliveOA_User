package com.oliveoa.view.meetingmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyMeetingInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttopic,ttime,tplace,tmembers;  //会议主题，会议时间，会议地点，出席人员
    private ContactInfoDao cidao;
    private ContactInfo ci;
    private MeetingApplication ap;
    private ArrayList<MeetingMember> list;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meeting_info);
        ap = getIntent().getParcelableExtra("ap");
        list = getIntent().getParcelableArrayListExtra("list");
        Log.i(TAG,"ap="+ap);
        Log.i(TAG,"list="+list);
        initView();
    }

    public void initView(){
        ttopic = (TextView) findViewById(R.id.meeting_topic);
        ttime = (TextView) findViewById(R.id.meeting_time);
        tplace = (TextView) findViewById(R.id.meeting_place);
        tmembers = (TextView) findViewById(R.id.members_list);
        back = (ImageView) findViewById(R.id.iback);
       loadingDialog = new LoadingDialog(MyMeetingInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMeetingInfoActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initData();
        //addViewItem(null);
    }
    private void back() {

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
