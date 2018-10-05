package com.oliveoa.view.documentmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.controller.DocumentService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.OvertimeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ReadReportActivity extends AppCompatActivity {
    private ImageView back,save;
    private TextView ttitle,tcontent,issuePerson;
    private EditText readReport;
    private Button btn_download;
    private OfficialDocument officialDocument;
    private String TAG = this.getClass().getSimpleName();
    //private OfficialDocumentCirculread officialDocumentCirculread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_report);

        officialDocument = getIntent().getParcelableExtra("info");
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        issuePerson = (TextView) findViewById(R.id.issue_person);
        readReport = (EditText) findViewById(R.id.report);
        btn_download = (Button) findViewById(R.id.download);
        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadReportActivity.this, ReadDocumentActivity.class);
                intent.putExtra("index", 0);
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

        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReadReportActivity.this);
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
          ttitle.setText(officialDocument.getTitle());
          tcontent.setText(officialDocument.getContent());
            ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
            ContactInfo contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getIssuedEid())).unique();
            if(contactInfo!=null){
                issuePerson.setText(contactInfo.getName());
            }else{
                issuePerson.setText("无");
            }
    }

    public void save() {
           if(TextUtils.isEmpty(readReport.getText().toString().trim())){
               Toast.makeText(ReadReportActivity.this, "信息不得为空！", Toast.LENGTH_SHORT).show();
           }else{
               Log.i(TAG,readReport.getText().toString().trim());
                   final OfficialDocumentCirculread officialDocumentCirculread = new OfficialDocumentCirculread();
                   officialDocumentCirculread.setReport(readReport.getText().toString().trim());
                   new Thread(new Runnable() {
                               @Override
                               public void run() {
                                   SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                                   String s = pref.getString("sessionid","");

                                   //Todo Service
                                   DocumentService service = new DocumentService();
                                   //Todo Service.Method
                                   StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentRead(s,officialDocument.getOdid(),officialDocumentCirculread);
                                   //ToCheck JsonBean.getStatus()
                                   if (statusAndMsgJsonBean.getStatus() == 0) {
                                       Looper.prepare();
                                       Toast.makeText(getApplicationContext(), "办理成功！点击返回键返回待办理公文列表", Toast.LENGTH_SHORT).show();
                                       Looper.loop();
                                   } else {
                                       Looper.prepare();//解决子线程弹toast问题
                                       Toast.makeText(getApplicationContext(),statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                       Looper.loop();// 进入loop中的循环，查看消息队列
                                   }
                               }
                           }).start();
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
