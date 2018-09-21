package com.oliveoa.view.documentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.AddApplicationActivity;
import com.oliveoa.view.myapplication.RecruitmentActivity;

import java.util.Timer;
import java.util.TimerTask;

public class DocumentManagementActivity extends AppCompatActivity {
    private LinearLayout ldraft,lnuclear,lissue,lreceive,lread;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_management);
        initViews();
    }

    public void initViews() {
        ldraft = (LinearLayout) findViewById(R.id.draft);
        lnuclear = (LinearLayout) findViewById(R.id.nuclear);
        lissue = (LinearLayout) findViewById(R.id.issue);
        lreceive = (LinearLayout) findViewById(R.id.receive);
        lread = (LinearLayout) findViewById(R.id.read);
        back = (ImageView) findViewById(R.id.iback);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentManagementActivity.this, TabLayoutBottomActivity.class);
                intent.getIntExtra("index",0);
                startActivity(intent);
                finish();
            }
        });

        ldraft.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addDraft();
            }
        });

        lnuclear.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addNuclearDraft();
            }
        });

        lissue.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addIssue();
            }
        });

        lreceive.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addReceive();
            }
        });

        lread.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                addReadDocument();
            }
        });

    }

    private void addDraft() {
        Intent intent = new Intent(DocumentManagementActivity.this, DraftActivity.class);
        startActivity(intent);
        finish();

    }

    private void addNuclearDraft() {
        Intent intent = new Intent(DocumentManagementActivity.this, NuclearDraftActivity.class);
        intent.getIntExtra("index",0);
        startActivity(intent);
        finish();

    }

    private void addIssue() {
        Intent intent = new Intent(DocumentManagementActivity.this, IssueActivity.class);
        intent.getIntExtra("index",0);
        startActivity(intent);
        finish();

    }

    private void addReceive() {
        Intent intent = new Intent(DocumentManagementActivity.this, ReceiveActivity.class);
        startActivity(intent);
        finish();

    }

    private void addReadDocument() {
        Intent intent = new Intent(DocumentManagementActivity.this, ReadDocumentActivity.class);
        intent.getIntExtra("index",0);
        startActivity(intent);
        finish();

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
