package com.oliveoa.view.documentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class DraftAddActivity extends AppCompatActivity {
    private ImageView back,save,nuclearSelect,issueSelect;
    private EditText etitle,econtent;
    private TextView tnuclear,tissue,fileName;
    private LinearLayout file,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_add);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        nuclearSelect = (ImageView) findViewById(R.id.nuclear_select);
        issueSelect = (ImageView) findViewById(R.id.issue_select);
        etitle = (EditText) findViewById(R.id.title);
        econtent = (EditText) findViewById(R.id.content);
        tnuclear = (TextView) findViewById(R.id.nuclear_person);
        tissue = (TextView) findViewById(R.id.issue_person);
        file = (LinearLayout) findViewById(R.id.select_file);
        cancel = (LinearLayout) findViewById(R.id.cancel);
        fileName = (TextView) findViewById(R.id.file_name);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraftAddActivity.this, DraftActivity.class);
                startActivity(intent);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                save();
            }
        });

    }

    public void initData() {

    }

    public void save() {

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
