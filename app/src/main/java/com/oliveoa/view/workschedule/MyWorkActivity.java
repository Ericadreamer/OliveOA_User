package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MyWorkActivity extends AppCompatActivity {

    private ImageView back;
    private RadioButton protocolWork, leadershipApproval, workAllocation;  //工作拟定，领导审批，工作审批
    private LinearLayout addMyWorkItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        //默认添加一个Item
        addViewItem(null);
        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        protocolWork = (RadioButton) findViewById(R.id.protocol_work);
        leadershipApproval = (RadioButton) findViewById(R.id.leadership_approval);
        workAllocation = (RadioButton) findViewById(R.id.allocation);
        addMyWorkItem = (LinearLayout) findViewById(R.id.my_work_list);


        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        protocolWork.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, ProtocolWorkActivity.class);
                startActivity(intent);
                finish();
            }
        });
        leadershipApproval.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, LeadershipApprovalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        workAllocation.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, WorkAllocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawableApplication = getResources().getDrawable(R.drawable.protocol_work_pic);
        drawableApplication.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        protocolWork.setCompoundDrawables(null, drawableApplication, null, null);//只放上面

        Drawable drawableSchedule = getResources().getDrawable(R.drawable.leadership_approval_pic);
        drawableSchedule.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        leadershipApproval.setCompoundDrawables(null, drawableSchedule, null, null);//只放上面

        Drawable drawableApproval = getResources().getDrawable(R.drawable.allocation_pic);
        drawableApproval.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        workAllocation.setCompoundDrawables(null, drawableApproval, null, null);//只放上面
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.my_work_item
     * @param view
     */
    private void addViewItem(View view){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

    }

    private void initData(){

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
