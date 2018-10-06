package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class ToMyWorkInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttime,tcontext,tname;
    private IssueWork workDetail;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_my_work_info);
        workDetail = getIntent().getParcelableExtra("work");
        Log.e(TAG,workDetail.toString());
        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.work_time);
        tcontext = (TextView) findViewById(R.id.work_content);
        tname = (TextView) findViewById(R.id.name);
        initData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToMyWorkInfoActivity.this, MyWorkActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatedd(workDetail.getBegintime())+"--"+dateFormat.LongtoDatedd(workDetail.getEndtime()));
        tcontext.setText(workDetail.getContent());

        contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(workDetail.getEid())).unique();
        if(contactInfo!=null) {
            tname.setText(contactInfo.getName());
        }

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
