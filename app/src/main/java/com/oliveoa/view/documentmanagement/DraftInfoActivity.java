package com.oliveoa.view.documentmanagement;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.Const;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.ScreenSizeUtils;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;


public class DraftInfoActivity extends AppCompatActivity{
    private ImageView back;
    private TextView ttitle, tcontent, tnuclearPerson, tnuclearStatus, tnuclearAdvise, tissuePerson, tissueStatus, tissueAdvise;
    private Button btn_download;
    private OfficialDocument officialDocument;
    private LoadingDialog loadingDialog;
    public  String TAG = DraftInfoActivity.this.getClass().getSimpleName();

    private Context mContext;

    public DownloadService.DownloadBinder downloadBinder;

    private String url ="http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private String fileName;
    private String path;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                fileName = (String)msg.obj;
                Log.i(TAG,fileName+"==="+path);
                downloadBinder.startDownload(
                        Environment.getExternalStorageDirectory() + "/OliveOA_User/OfficialsDocument/",
                        fileName,
                        path,
                        (int) System.currentTimeMillis());
            }
        }
    };
  /*  Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                long filesize = (long)msg.obj;
                Log.i(TAG,fileName+"==="+path);
                downloadBinder.startDownload(
                        Environment.getExternalStorageDirectory() + "/OliveOA_User/OfficialsDocument/",
                        fileName,
                        path,
                        (int) System.currentTimeMillis(),
                        filesize);
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_info);
        officialDocument = getIntent().getParcelableExtra("info");
        mContext = this;
        path = Const.DOCUMENTFLOW_DOWNLOAD + officialDocument.getOdid();
        //path = "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";
        Intent intent = new Intent(mContext, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tnuclearPerson = (TextView) findViewById(R.id.nuclear_person);
        tnuclearStatus = (TextView) findViewById(R.id.nuclear_status);
        tnuclearAdvise = (TextView) findViewById(R.id.nuclear_advise);
        tissuePerson = (TextView) findViewById(R.id.issue_person);
        tissueStatus = (TextView) findViewById(R.id.issue_status);
        tissueAdvise = (TextView) findViewById(R.id.issue_advise);
        btn_download = (Button) findViewById(R.id.download);


        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                back();
            }
        });



        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DraftInfoActivity.this);
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


    private void download() {
           new Thread(new Runnable() {
                       @Override
                       public void run() {
                           URL murl = null;
                           String fileName ="";
                           try {
                               //获取文件名
                               murl = new URL(path);
                               URLConnection con = murl.openConnection();
                               fileName = con.getHeaderField("Content-Disposition");
                               fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
                               fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
                               Log.i(getClass().getSimpleName(), "文件名为：" + fileName);
                               Log.i(getPackageName(),"size="+con.getHeaderField("Content-Length"));
                               Log.i(getPackageName(),"size="+con.getContentLength());

                               Message msg = handler.obtainMessage();
                               msg.what = 2;
                               msg.obj = fileName;
                               handler.sendMessage(msg);
                              /* Message msg1 = handler.obtainMessage();
                               msg1.what = 1;
                               //msg1.obj = totalsize;
                               handler1.sendMessage(msg1);*/

                           } catch (MalformedURLException e) {
                               e.printStackTrace();
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   }).start();

    }


    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                DocumentService service = new DocumentService();
                //Todo Service.Method
                StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>> statusAndMsgAndDataHttpResponseObject = service.getdocumentIDraft(s);
                //ToCheck JsonBean.getStatus()
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    //Log.i("DOCUMENT=",statusAndMsgAndDataHttpResponseObject.getData().toString());
                    Intent intent = new Intent(mContext, DraftActivity.class);
                    intent.putParcelableArrayListExtra("list",statusAndMsgAndDataHttpResponseObject.getData());
                    startActivity(intent);
                    finish();
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndMsgAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();

    }

    public void initData() {
        ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
        ContactInfo ci = new ContactInfo();

        ttitle.setText(officialDocument.getTitle());
        tcontent.setText(officialDocument.getContent());

        ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getNuclearDraftEid())).unique();
        if(ci!=null){
            tnuclearPerson.setText(ci.getName());
        }else{
            tnuclearPerson.setText("");
        }

        ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getIssuedEid())).unique();
        if(ci!=null){
            tissuePerson.setText(ci.getName());
        }else{
            tissuePerson.setText("");
        }

        switch (officialDocument.getNuclearDraftIsapproved()){
            case -2:
                tnuclearStatus.setText("待审核");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tnuclearStatus.setText("不同意");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 1:
                tnuclearStatus.setText("同意");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 0:
                tnuclearStatus.setText("正在审核");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;
        }

        switch (officialDocument.getIssuedIsapproved()){
            case -2:
                tissueStatus.setText("待审核");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tissueStatus.setText("不同意");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 1:
                tissueStatus.setText("同意");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 0:
                tissueStatus.setText("正在审核");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;
        }

        if(officialDocument.getNuclearDraftOpinion()==null) {
           tnuclearAdvise.setText("");
        }else{
           tnuclearAdvise.setText(officialDocument.getNuclearDraftOpinion());
        }
        if(officialDocument.getIssuedOpinion()==null) {
            tissueAdvise.setText("");
        }else{
            tissueAdvise.setText(officialDocument.getIssuedOpinion());
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

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
