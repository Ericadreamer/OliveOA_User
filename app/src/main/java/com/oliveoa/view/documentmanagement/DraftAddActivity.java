package com.oliveoa.view.documentmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.OfficialDocumentDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.SelectPersonApprovingActivity;
import com.oliveoa.widget.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class DraftAddActivity extends AppCompatActivity {
    private ImageView back,save,nuclearSelect,issueSelect;
    private EditText etitle,econtent;
    private TextView tnuclear,tissue,fileName;
    private LinearLayout file,cancel;

    private String TAG = this.getClass().getSimpleName();
    private static final int FILE_SELECT_CODE = 0;
    private File uploadfile;


    private OfficialDocumentDao officialDocumentDao;
    private OfficialDocument officialDocument;
    private ContactInfo ci;
    private ContactInfoDao cidao;
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private DutyInfoDao dutyInfoDao;
    private LoadingDialog loadingDialog;
    private int index=0;
    private String path = null;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String path = (String) msg.obj;
                uploadfile = new File(path);
                String fname = uploadfile.getName();
                fileName.setText(fname);
            }
            if (msg.what == 2) {
                String tip = (String)msg.obj;
                loadingDialog = new LoadingDialog(DraftAddActivity.this,tip,true);
                loadingDialog.show();
            }
            if (msg.what == 3) {
                String tip = (String)msg.obj;
                Toast.makeText(DraftAddActivity.this,tip,Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_add);
        index = getIntent().getIntExtra("index",index);//0为列表，1为选择
        Log.e(TAG, "index"+String.valueOf(index));
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        nuclearSelect = (ImageView) findViewById(R.id.nuclear_select);
        issueSelect = (ImageView) findViewById(R.id.issue_select);
        etitle = (EditText) findViewById(R.id.title);
        econtent = (EditText) findViewById(R.id.content);
        tnuclear = (TextView) findViewById(R.id.nuclear_person);
        tissue = (TextView) findViewById(R.id.issue_person);
        file = (LinearLayout) findViewById(R.id.select_file);
        cancel = (LinearLayout) findViewById(R.id.cancel);
        fileName = (TextView) findViewById(R.id.file_name);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
              back();
            }
        });
        issueSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                issueSelect();
            }
        });
        nuclearSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuclearSelect();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                fileName.setText("请选择文件");
                path = null;
                uploadfile = null;
            }
        });

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
                    Intent intent = new Intent(DraftAddActivity.this, DraftActivity.class);
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
        officialDocumentDao = EntityManager.getInstance().getOfficialDocumentDao();
        cidao = EntityManager.getInstance().getContactInfo();

        if(index==0){
            officialDocument = new OfficialDocument();
            officialDocumentDao.deleteAll();
            officialDocumentDao.insert(officialDocument);
        }
        if(index==1){
            SharedPreferences pref = getSharedPreferences("filepath", MODE_PRIVATE);
            path = pref.getString("filepath", "");


            officialDocument = officialDocumentDao.queryBuilder().unique();
            if(officialDocument!=null){
                econtent.setText(officialDocument.getContent());
                etitle.setText(officialDocument.getTitle());
                if(officialDocument.getNuclearDraftEid()!=null){
                    ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getNuclearDraftEid())).unique();
                    if (ci != null) {
                        tnuclear.setText(ci.getName());
                    }
                }
                if(officialDocument.getIssuedEid()!=null) {
                    ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getIssuedEid())).unique();
                    if (ci != null) {
                        tissue.setText(ci.getName());
                    }
                }
            }
        }
    }

    public void save() {
        if(TextUtils.isEmpty(econtent.getText().toString())||TextUtils.isEmpty(etitle.getText().toString().trim())){
            Toast.makeText(DraftAddActivity.this,"信息不得为空！",Toast.LENGTH_SHORT).show();
        }else{
            officialDocument.setTitle(etitle.getText().toString().trim());
            officialDocument.setContent(econtent.getText().toString().trim());
            if(officialDocument.getNuclearDraftEid()==null){
                Toast.makeText(DraftAddActivity.this,"请选择核稿人",Toast.LENGTH_SHORT).show();
            }else if(officialDocument.getIssuedEid()==null){
                Toast.makeText(DraftAddActivity.this,"请选择发布人",Toast.LENGTH_SHORT).show();
            }else if(uploadfile==null) {
                Toast.makeText(DraftAddActivity.this,"请上传公文附件",Toast.LENGTH_SHORT).show();
            }else {
                LoadingDialog loadingDialog = new LoadingDialog(DraftAddActivity.this, "正在上传文件，请稍候……", true);
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        //Todo Service
                        DocumentService service = new DocumentService();
                        //Todo Service.Method
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentDraft(s, officialDocument, uploadfile);
                        //ToCheck JsonBean.getStatus()
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                }).start();
            }
        }
    }



    /**
     * 选择文件
     */
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//过滤文件类型（所有）
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
           // startActivityForResult(Intent.createChooser(intent, "请选择文件！"), FILE_SELECT_CODE);
            startActivityForResult(intent, 1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "未安装文件管理器！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = path;
                handler.sendMessage(msg);
                //Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = path;
                handler.sendMessage(msg);
                //Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = path;
                handler.sendMessage(msg);
                //Toast.makeText(this, path+"222222", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     *  数据存储到SharedPreferences文件中
     *
     */
    public void savePath( String path){
        SharedPreferences.Editor editor = getSharedPreferences("filepath",MODE_PRIVATE).edit();
        editor.putString("filepath",path);
        editor.apply();
    }

    public void nuclearSelect(){
        //index 14为核稿人选择，15为发布人
        officialDocument.setContent(econtent.getText().toString().trim());
        officialDocument.setTitle(etitle.getText().toString().trim());
        officialDocumentDao.deleteAll();
        officialDocumentDao.insert(officialDocument);
        if(!fileName.getText().toString().trim().equals("")&&!fileName.getText().toString().trim().equals("请选择文件")){
            if(path!=null) {
                savePath(path);
            }
        }
        LoadingDialog loadingDialog = new LoadingDialog(DraftAddActivity.this, "正在加载数据", true);
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
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();

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
                        Intent intent = new Intent(DraftAddActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",14);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循7环，查看消息队列
                }
            }
        }).start();
    }

    public void issueSelect(){
        //index 14为核稿人选择，15为发布人
        if(officialDocument!=null) {
            officialDocument.setContent(econtent.getText().toString().trim());
            officialDocument.setTitle(etitle.getText().toString().trim());
            officialDocumentDao.deleteAll();
            officialDocumentDao.insert(officialDocument);
            if(!fileName.getText().toString().trim().equals("")&&!fileName.getText().toString().trim().equals("请选择文件")){
                if(path!=null) {
                    savePath(path);
                }
            }
            LoadingDialog loadingDialog = new LoadingDialog(DraftAddActivity.this, "正在加载数据", true);
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
                    departmentInfoDao.deleteAll();
                    contactInfoDao.deleteAll();

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
                            Intent intent = new Intent(DraftAddActivity.this, SelectPersonApprovingActivity.class);
                            intent.putExtra("index", 15);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循7环，查看消息队列
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
