package com.oliveoa.view.meetingmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.MeetingActivity;
import com.oliveoa.view.workschedule.ApprovalWorkActivity;
import com.oliveoa.view.workschedule.LeadershipApprovalActivity;
import com.oliveoa.view.workschedule.MyWorkActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MyMeetingActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttoptic,ttime; //会议主题，更新时间
    private LinearLayout addmyMeetingitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meeting);

        initView();
        initData();

        //默认添加一个Item
        addViewItem(null);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        addmyMeetingitem = (LinearLayout) findViewById(R.id.my_meeting_list);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMeetingActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addmyMeetingitem.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMeetingActivity.this, MyMeetingInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData(){

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.my_meeting_item
     * @param view
     */
    private void addViewItem(View view){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

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
