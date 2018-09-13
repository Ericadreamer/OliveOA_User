package com.oliveoa.view.approval;

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

import java.util.Timer;
import java.util.TimerTask;

public class OvertimeDisposedActivity extends AppCompatActivity {

    private ImageView back;
    //申请人，加班时间，加班原因，审批人，审批状态，审批意见
    private TextView tApplicant,ttime,treason,tApprover,tstatus,tadvise;
    private LinearLayout addlistView;//审批列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_disposed);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        tApprover = (TextView) findViewById(R.id.approver);
        tstatus = (TextView) findViewById(R.id.status);
        tadvise = (TextView) findViewById(R.id.advise);
        addlistView = (LinearLayout) findViewById(R.id.approve_list);
        tApplicant = (TextView) findViewById(R.id.name);


        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OvertimeDisposedActivity.this, MyApprovalActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.approval_status_item
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
