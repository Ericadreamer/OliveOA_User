package com.oliveoa.view.documentmanagement;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.Const;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IssueInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttitle,tcontent,tnuclearAdvise,tissueAdvise,tdepartment,tdeid,tneid,tNuclearstaus,tIssuestatus;
    private Button btn_download;
    private OfficialDocument officialDocument;
    private ArrayList<OfficialDocumentIssued> list;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo departmentInfo;
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;

    DownloadService.DownloadBinder downloadBinder;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_info);
        officialDocument = getIntent().getParcelableExtra("info");
        list = getIntent().getParcelableArrayListExtra("list");
        Log.i(TAG,officialDocument.toString());
        Log.i(TAG,list.toString());

        mContext = this;
        path = Const.DOCUMENTFLOW_DOWNLOAD + officialDocument.getOdid();
        Intent intent = new Intent(mContext, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tneid = (TextView)findViewById(R.id.neid);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tnuclearAdvise = (TextView) findViewById(R.id.nuclear_advise);
        tissueAdvise = (TextView) findViewById(R.id.issue_advise);
        tdepartment = (TextView) findViewById(R.id.department);
        btn_download = (Button) findViewById(R.id.download);
        tdeid = (TextView)findViewById(R.id.deid);
        tIssuestatus = (TextView)findViewById(R.id.Issueisapproval);
        tNuclearstaus = (TextView)findViewById(R.id.Nuclearisapproval);

        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueInfoActivity.this, IssueActivity.class);
                intent.putExtra("index", 1);
                startActivity(intent);
                finish();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(IssueInfoActivity.this);
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

    public void initData() {
          contactInfoDao = EntityManager.getInstance().getContactInfo();
          departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
          ContactInfo contactInfo = new ContactInfo();
          contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getDraftEid())).unique();
          if(contactInfo!=null){
              Log.e(TAG,"拟稿人："+contactInfo.toString());
              tdeid.setText(contactInfo.getName());
          }else{
              tdeid.setText("");
          }
            contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getNuclearDraftEid())).unique();
            if(contactInfo!=null){
                Log.e(TAG,"核稿人："+contactInfo.toString());
                tneid.setText(contactInfo.getName());
            }else{
                tneid.setText("");
            }
           ArrayList<String> member = new ArrayList<>();
            if(list.size()!=0){
                for (int i = 0;i<list.size();i++){
                   departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(list.get(i).getDcid())).unique();
                   if(departmentInfo!=null){
                       Log.i(TAG,"签发部门["+i+"]="+departmentInfo.getName());
                       member.add(departmentInfo.getName());
                   }
                }
                //字符串toString会出现[]包括对象，故substring对第一个和最后一个括号隐藏
                Log.i(TAG,member.toString());
                tdepartment.setText(member.toString().substring(1,member.toString().length()-1));
            }else{
                tdepartment.setText("无");
            }

            tissueAdvise.setText(officialDocument.getIssuedOpinion());
            tnuclearAdvise.setText(officialDocument.getNuclearDraftOpinion());
            tcontent.setText(officialDocument.getContent());
            ttitle.setText(officialDocument.getTitle());

        switch (officialDocument.getNuclearDraftIsapproved()) {
            case -2:
                tNuclearstaus.setText("待核稿");
                tNuclearstaus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tNuclearstaus.setText("不同意");
                tNuclearstaus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 1:
                tNuclearstaus.setText("同意");
                tNuclearstaus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 0:
                tNuclearstaus.setText("正在核稿");
                tNuclearstaus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;
        }
        switch (officialDocument.getIssuedIsapproved()) {
            case -2:
                tIssuestatus.setText("待发布");
                tIssuestatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tIssuestatus.setText("不同意");
                tIssuestatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 1:
                tIssuestatus.setText("同意");
                tIssuestatus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 0:
                tIssuestatus.setText("正在发布");
                tIssuestatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
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
