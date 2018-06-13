package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class AddApplicationActivity extends AppCompatActivity {

    private LinearLayout overtime,leave,businessTrip,meeting,dimission,regularWorker,adjustPost,recruitment,goods;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        overtime = (LinearLayout) findViewById(R.id.overtime);
        leave = (LinearLayout) findViewById(R.id.leave);
        businessTrip = (LinearLayout) findViewById(R.id.business);
        meeting = (LinearLayout) findViewById(R.id.meeting);
        dimission = (LinearLayout) findViewById(R.id.dimission);
        regularWorker = (LinearLayout) findViewById(R.id.regular_work);
        adjustPost = (LinearLayout) findViewById(R.id.adjust_post);
        recruitment = (LinearLayout) findViewById(R.id.recruitment);
        goods = (LinearLayout) findViewById(R.id.goods);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();

            }
        });
        overtime.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, OvertimeActivity.class);
                startActivity(intent);
                finish();

            }
        });
        leave.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, LeaveActivity.class);
                startActivity(intent);
                finish();

            }
        });
        businessTrip.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, BusinessActivity.class);
                startActivity(intent);
                finish();

            }
        });
        meeting.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, MeetingActivity.class);
                startActivity(intent);
                finish();

            }
        });
        dimission.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, DimissionActivity.class);
                startActivity(intent);
                finish();

            }
        });
        regularWorker.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, RegularWorkerActivity.class);
                startActivity(intent);
                finish();

            }
        });
        adjustPost.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, AdjustPostActivity.class);
                startActivity(intent);
                finish();

            }
        });
        recruitment.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, RecruitmentActivity.class);
                startActivity(intent);
                finish();

            }
        });
        goods.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddApplicationActivity.this, GoodsActivity.class);
                startActivity(intent);
                finish();

            }
        });

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
