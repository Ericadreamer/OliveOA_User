package com.oliveoa.view.documentmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.OfficialDocumentDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class NuclearDraftInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttitle,tcontent,tadvise,tdeid,tstatus;
    private Button btn_download;
    private OfficialDocument officialDocument;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuclear_draft_info);

        officialDocument = getIntent().getParcelableExtra("info");
        Log.i(TAG,officialDocument.toString());
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tdeid = (TextView)findViewById(R.id.deid);
        tadvise = (TextView) findViewById(R.id.nuclear_advise);
        btn_download = (Button) findViewById(R.id.download);
        tstatus = (TextView)findViewById(R.id.isapproval);

        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuclearDraftInfoActivity.this, NuclearDraftActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                finish();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NuclearDraftInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否下载附件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

    }

    public void download() {

    }

    public void initData() {
         contactInfoDao = EntityManager.getInstance().getContactInfo();
         contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getDraftEid())).unique();
         if(contactInfo!=null){
             tdeid.setText(contactInfo.getName());
         }else{
             tdeid.setText("");
         }

         tadvise.setText(officialDocument.getNuclearDraftOpinion());
         tcontent.setText(officialDocument.getContent());
         ttitle.setText(officialDocument.getTitle());
            switch (officialDocument.getNuclearDraftIsapproved()) {
                case -2:
                    tstatus.setText("待核稿");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                case -1:
                    tstatus.setText("不同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                    break;
                case 1:
                    tstatus.setText("同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                    break;
                case 0:
                    tstatus.setText("正在核稿");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                default:
                    break;
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
