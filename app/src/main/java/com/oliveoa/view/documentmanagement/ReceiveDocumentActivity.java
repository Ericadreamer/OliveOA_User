package com.oliveoa.view.documentmanagement;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.Const;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.OfficialDocumentDao;
import com.oliveoa.greendao.UserInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.pojo.UserInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.SelectPersonApprovingActivity;
import com.oliveoa.widget.LoadingDialog;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReceiveDocumentActivity extends AppCompatActivity {
    private ImageView back,save,readSelect;
    private TextView ttitle,tcontent,issuePerson,readPerson;
    private Button btn_download;

    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private ApproveNumberDao approveNumberDao;
    private OfficialDocument officialDocument;
    private OfficialDocumentDao officialDocumentDao;
    private List<ApproveNumber> list;

    private LinearLayout addlistView;
    private TextView tname; //approve_item信息，tname为审批人名称
    private ImageView delete;
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
        setContentView(R.layout.activity_receive_document);
        officialDocument = getIntent().getParcelableExtra("info");
        index = getIntent().getIntExtra("index",index);//列表0，选择1
        Log.e("IDDEX=", String.valueOf(index));
        Log.e("officialDocument=", officialDocument.toString());

        mContext = this;
        path = Const.DOCUMENTFLOW_DOWNLOAD + officialDocument.getOdid();
        //path = "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";
        Intent intent = new Intent(mContext, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        readSelect = (ImageView) findViewById(R.id.read_select);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        issuePerson = (TextView) findViewById(R.id.issue_person);
        readPerson = (TextView) findViewById(R.id.read_person);
        btn_download = (Button) findViewById(R.id.download);
        addlistView = (LinearLayout)findViewById(R.id.approve_list);

        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiveDocumentActivity.this, ReceiveActivity.class);
                startActivity(intent);
                finish();
            }
        });
        readSelect.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
              epselect();
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(ReceiveDocumentActivity.this);
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
        ContactInfo contactInfo = new ContactInfo();

        contactInfo= contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getIssuedEid())).unique();
        if(contactInfo!=null){
            Log.e(TAG,"签发人："+contactInfo.toString());
            issuePerson.setText(contactInfo.getName());
        }else{
            issuePerson.setText("");
        }
        tcontent.setText(officialDocument.getContent());
        ttitle.setText(officialDocument.getTitle());

        if(index==1) {
            approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
            list = approveNumberDao.queryBuilder().list();
            if (list != null) {
                Log.i(TAG,"ReadMembers==="+list.toString());
                addViewItem(null);
            }

        }
    }

    public void save() {
            if(index==1&&list!=null) {
                Log.i(TAG,list.toString());
                final OfficialDocumentIssued officialDocumentIssued = new OfficialDocumentIssued();
                officialDocumentIssued.setIsreceive(1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        DocumentService service = new DocumentService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentReceive(s, officialDocument.getOdid(),officialDocumentIssued,list.toString());
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "签收成功！点击返回键返回待签收工作列表", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }else{
                Toast.makeText(getApplicationContext(), "请选择办理人", Toast.LENGTH_SHORT).show();
            }

    }

    public void epselect(){
        OfficialDocument od = new OfficialDocument();
        od.setTitle(officialDocument.getTitle());
        od.setContent(officialDocument.getContent());
        od.setOdid(officialDocument.getOdid());
        od.setDraftEid(officialDocument.getDraftEid());
        od.setNuclearDraftIsapproved(officialDocument.getNuclearDraftIsapproved());
        od.setNuclearDraftOpinion(officialDocument.getNuclearDraftOpinion());
        od.setNuclearDraftEid(officialDocument.getNuclearDraftEid());
        od.setIssuedIsapproved(officialDocument.getIssuedIsapproved());
        od.setIssuedOpinion(officialDocument.getIssuedOpinion());
        od.setIssuedEid(officialDocument.getIssuedEid());
        od.setOrderby(officialDocument.getOrderby());
        od.setCreatetime(officialDocument.getCreatetime());
        od.setUpdatetime(officialDocument.getUpdatetime());
        final OfficialDocumentDao officialDocumentDao = EntityManager.getInstance().getOfficialDocumentDao();
        officialDocumentDao.deleteAll();
        officialDocumentDao.insert(od);

        LoadingDialog loadingDialog = new LoadingDialog(ReceiveDocumentActivity.this, "正在加载数据", true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                UserInfoDao userInfoDao = EntityManager.getInstance().getUserInfoDao();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();
                UserInfo userInfo = userInfoDao.queryBuilder().unique();
                if(userInfo!=null) {
                     Log.i(TAG,userInfo.toString());
                    //ToCheck JsonBean.getStatus()
                    if (contactHttpResponseObject.getStatus() == 0) {
                        ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                        Log.d("userinfo", contactInfos.toString());
                        if (contactInfos.size() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            for (int i = 0; i < contactInfos.size(); i++) {
                                if(contactInfos.get(i).getDepartment().getDcid().equals(userInfo.getDcid())) {
                                    Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                                    DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                                    departmentInfoDao.insert(departmentInfo);
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                                    for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                        if (contactInfos.get(i).getEmpContactList().get(j).getEmployee() != null) {
                                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().get(" + j + ").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                            contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                        }
                                    }
                                }
                            }
                            Intent intent = new Intent(ReceiveDocumentActivity.this, SelectPersonApprovingActivity.class);
                            intent.putExtra("index", 16);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循7环，查看消息队列
                    }
                }
            }
        }).start();

    }


    //添加ViewItem
    private void addViewItem(View view) {
        if (list==null) {
            /*Toast.makeText(OvertimeActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();*/
        } else {
            for(int i = 0;i <list.size(); i ++){
                View EvaluateView = View.inflate(ReceiveDocumentActivity.this, R.layout.item_member, null);
                addlistView.addView(EvaluateView);
                InitDataViewItem();
            }
            sortViewItem();
        }
    }
    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            final ImageView delete = (ImageView)childAt.findViewById(R.id.delete);

            final int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.leave_type);
                    Log.e(TAG,"确定删除"+tname.getText().toString()+"??");
                    addlistView.removeView(item);
                    approveNumberDao.deleteAll();
                    ApproveNumber employee = new ApproveNumber();
                    int h=0;
                    for(int j=0;j<list.size();j++){
                        if(!list.get(j).getId().equals(list.get(finalI).getId())){
                            employee.setId(list.get(j).getId());
                            approveNumberDao.insert(employee);
                        }
                    }
                }
            });
        }
    }
    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.leave_type);

            ContactInfo ep = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(list.get(i).getId())).unique();
            if(ep!=null){
                tname.setText(ep.getName());
                Log.e("ep:",ep.getName());
            }else{
                tname.setText("");
            }

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
