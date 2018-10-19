package com.oliveoa.view.documentmanagement;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.Const;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.OfficialDocumentDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.AdjustPostActivity;

import com.oliveoa.view.myapplication.DepartmentSelectActivity;
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

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

public class IssueDocumentActivity extends AppCompatActivity {
    private ImageView back,save,departmentSelect;
    private TextView ttitle,tcontent,tnuclearAdvise,tdepartment,tneid,tdeid;
    private TextView tNuclearstaus,tIssuestatus;
    private EditText tissueAdvise;
    private Button btn_download;

    private Context mContext;
    private RecyclerView mContentRv;

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

    DownloadService.DownloadBinder downloadBinder;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_document);
        this.mContext = this;

        officialDocument = getIntent().getParcelableExtra("info");
        index = getIntent().getIntExtra("index",index);//列表0，选择1
        Log.e("IDDEX=", String.valueOf(index));

        path = Const.DOCUMENTFLOW_DOWNLOAD + officialDocument.getOdid();
        //path = "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";
        Intent intent = new Intent(mContext, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        departmentSelect = (ImageView) findViewById(R.id.department_select);
        tdeid = (TextView)findViewById(R.id.deid);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tnuclearAdvise = (TextView) findViewById(R.id.nuclear_advise);
        tissueAdvise = (EditText) findViewById(R.id.issue_advise);
        tneid = (TextView)findViewById(R.id.neid);
        tIssuestatus = (TextView)findViewById(R.id.isapproval);
        tNuclearstaus = (TextView)findViewById(R.id.Nuclearisapproval);
        tdepartment = (TextView) findViewById(R.id.department);
        btn_download = (Button) findViewById(R.id.download);
        addlistView = (LinearLayout)findViewById(R.id.approve_list);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                approveNumberDao.deleteAll();
                Intent intent = new Intent(IssueDocumentActivity.this, IssueActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
                finish();
            }
        });

        departmentSelect.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                dpselect();
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(IssueDocumentActivity.this);
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

    private void dpselect() {
        OfficialDocument od = new OfficialDocument();
        od.setTitle(officialDocument.getTitle());
        od.setContent(officialDocument.getContent());
        od.setOdid(officialDocument.getOdid());
        od.setDraftEid(officialDocument.getDraftEid());
        od.setNuclearDraftIsapproved(officialDocument.getNuclearDraftIsapproved());
        od.setNuclearDraftOpinion(officialDocument.getNuclearDraftOpinion());
        od.setNuclearDraftEid(officialDocument.getNuclearDraftEid());
        od.setOrderby(officialDocument.getOrderby());
        officialDocument.setIssuedIsapproved(-2);
        od.setCreatetime(officialDocument.getCreatetime());
        od.setUpdatetime(officialDocument.getUpdatetime());
        switch (tIssuestatus.getText().toString().trim()){
            case "同意": od.setIssuedIsapproved(1);
            break;
            case "不同意": od.setIssuedIsapproved(-1);
            break;
            default:break;
        }
        if(!tissueAdvise.getText().toString().trim().equals("")){
            od.setIssuedOpinion(tissueAdvise.getText().toString().trim());
        }
        OfficialDocumentDao officialDocumentDao = EntityManager.getInstance().getOfficialDocumentDao();
        officialDocumentDao.deleteAll();
        officialDocumentDao.insert(od);

        LoadingDialog loadingDialog = new LoadingDialog(IssueDocumentActivity.this, "正在加载数据", true);
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
                departmentInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus() == 0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if (contactInfos.size() == 0) {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和岗位", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    } else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                            DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                            departmentInfoDao.insert(departmentInfo);
                        }
                        Intent intent = new Intent(IssueDocumentActivity.this, DepartmentSelectActivity.class);
                        intent.putExtra("index", 3);
                        startActivity(intent);
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();


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
        DepartmentInfo departmentInfo = new DepartmentInfo();
        contactInfo= contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getDraftEid())).unique();
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
       /* tissueAdvise.setText(officialDocument.getIssuedOpinion());*/
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


       if(index==1) {
            if(officialDocument.getIssuedOpinion()!=null) {
                tissueAdvise.setText(officialDocument.getIssuedOpinion());
            }
           switch (officialDocument.getIssuedIsapproved()) {
               case -1:
                   tIssuestatus.setText("不同意");
                   tIssuestatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                   break;
               case 1:
                   tIssuestatus.setText("同意");
                   tIssuestatus.setTextColor(getResources().getColor(R.color.tv_pass));
                   break;
               default:
                   break;
           }
           approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
           list = approveNumberDao.queryBuilder().list();
           if (list != null) {
               Log.i(TAG,"IssueDepartment==="+list.toString());
               addViewItem(null);
           }

       }
    }

    public void save() {
            if (TextUtils.isEmpty(tissueAdvise.getText().toString().trim()) || TextUtils.isEmpty(tIssuestatus.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
            } else {
                    //Log.i(TAG,list.toString());
                    officialDocument.setIssuedOpinion(tissueAdvise.getText().toString().trim());
                    switch (tIssuestatus.getText().toString().trim()) {
                        case "同意":
                            officialDocument.setIssuedIsapproved(1);
                            break;
                        case "不同意":
                            officialDocument.setIssuedIsapproved(-1);
                            break;
                        default:
                            break;
                    }
                    if(officialDocument.getIssuedIsapproved()==1&&index==0&&list==null){
                        Toast.makeText(getApplicationContext(), "请选择签发部门", Toast.LENGTH_SHORT).show();
                    }

                    if(officialDocument.getIssuedIsapproved()==1&&index==1&&list!=null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //读取SharePreferences的cookies
                                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                                String s = pref.getString("sessionid", "");

                                DocumentService service = new DocumentService();
                                StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentIssue(s, officialDocument,list.toString());
                                if (statusAndMsgJsonBean.getStatus() == 0) {
                                    approveNumberDao.deleteAll();
                                    Intent intent = new Intent(IssueDocumentActivity.this, IssueActivity.class);
                                    intent.putExtra("index", 0);
                                    startActivity(intent);
                                    finish();
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "签发成功！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }).start();
                    }
                    if(officialDocument.getIssuedIsapproved()==-1){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //读取SharePreferences的cookies
                                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                                String s = pref.getString("sessionid", "");

                                DocumentService service = new DocumentService();
                                StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentIssue(s, officialDocument,"");
                                if (statusAndMsgJsonBean.getStatus() == 0) {
                                    approveNumberDao.deleteAll();
                                    Intent intent = new Intent(IssueDocumentActivity.this, IssueActivity.class);
                                    intent.putExtra("index", 0);
                                    startActivity(intent);
                                    finish();
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "签发成功！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }).start();
                    }


            }

    }

    //单项选择器
    public void onOptionPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "同意","不同意"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("核稿意见选择");
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(18);
        picker.setTitleTextSize(16);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tIssuestatus.setText(item);
                //showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }
    //添加ViewItem
    private void addViewItem(View view) {
        if (list==null) {
            /*Toast.makeText(OvertimeActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();*/
        } else {
            for(int i = 0;i <list.size(); i ++){
                View EvaluateView = View.inflate(IssueDocumentActivity.this, R.layout.item_member, null);
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

            DepartmentInfo dp = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(list.get(i).getId())).unique();
            if(dp!=null){
                tname.setText(dp.getName());
                Log.e("dcid:",dp.getName());
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
