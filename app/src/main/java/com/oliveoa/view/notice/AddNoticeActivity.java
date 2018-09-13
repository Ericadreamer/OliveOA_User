package com.oliveoa.view.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AddNoticeActivity extends AppCompatActivity {
    private EditText etitle,econtent;
    private LinearLayout addPersonList;  //审批人列表
    private ImageView back,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        etitle = (EditText) findViewById(R.id.title);
        econtent = (EditText) findViewById(R.id.content);
        addPersonList = (LinearLayout) findViewById(R.id.approve_list);



        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNoticeActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


        // 默认添加一个Item
        addViewItem(null);

    }

    private void initData() {

    }
    private void save() {

    }

    //添加审批人操作
    public void personadd(){

    }

    //加载R.layout.item_approve列表
    private void addViewItem(View view) {

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem(){

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
