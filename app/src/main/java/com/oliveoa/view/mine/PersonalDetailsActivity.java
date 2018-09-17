package com.oliveoa.view.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PersonalDetailsActivity extends AppCompatActivity {
    private ImageView back,call;
    private TextView tname,tid,tsex,ttel,tduty,tbirth,tmail,taddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        //隐藏状态栏和actionbar
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        call = (ImageView) findViewById(R.id.call);
        tid = (TextView) findViewById(R.id.person_id);
        tsex = (TextView) findViewById(R.id.person_sex);
        ttel = (TextView) findViewById(R.id.tel);
        tduty = (TextView) findViewById(R.id.department_duty);
        tbirth = (TextView) findViewById(R.id.birth);
        tmail = (TextView) findViewById(R.id.mail);
        taddress = (TextView) findViewById(R.id.address);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetailsActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",3);
                startActivity(intent);
                finish();
            }
        });

        //呼叫
        call.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalDetailsActivity.this);
//                dialog.setTitle("呼叫");
                dialog.setMessage("呼叫");
//                dialog.setCancelable(false);
//                dialog.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //back();
//                    }
//                });
                /*dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/
                dialog.show();
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
