package com.oliveoa.view.approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class GoodsUndisposedActivity extends AppCompatActivity {
    //申请人，选择物品，取用时间，归还时间
    private TextView tApplicant,tGoodsName,tstartTime,treturnTime;
    private ImageView back;
    private Button bagree,bdisagree; //同意，不同意

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_undisposed);

        initView();
        initData();
    }

    public void initView(){
        tGoodsName = (TextView) findViewById(R.id.goods);
        tstartTime = (TextView) findViewById(R.id.start_time);
        treturnTime = (TextView) findViewById(R.id.return_time);
        back = (ImageView) findViewById(R.id.iback);
        tApplicant = (TextView) findViewById(R.id.name);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodsUndisposedActivity.this, MyApprovalActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });

        bagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodsUndisposedActivity.this, ApprovalAdviseActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodsUndisposedActivity.this, ApprovalAdviseActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initData(){

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
