package com.oliveoa.view.myapplication;

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

public class RegularWorkerInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tContentDcpid,tstartDate,tendDate,tsummary;  //所属部门职位，到岗日期，截止日期，试用期总结
    private TextView tname,tstatus;  //审批进度item，审批人和审批状态
    private LinearLayout addlistView;  //添加审批进度列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_worker_info);

        initView();
    }

    public void initView(){
        tContentDcpid = (TextView) findViewById(R.id.content_dpcid);
        tstartDate = (TextView) findViewById(R.id.start_date);
        tendDate = (TextView) findViewById(R.id.end_date);
        tsummary = (TextView) findViewById(R.id.summary);
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularWorkerInfoActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.approveapplication_listitem
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
