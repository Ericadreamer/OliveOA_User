package com.oliveoa.view.documentmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.AnnouncementService;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.DutySelectActivity;
import com.oliveoa.view.myapplication.SelectPersonApprovingActivity;
import com.oliveoa.view.note.MyNoteActivity;
import com.oliveoa.view.notice.MySubmissionActivity;
import com.oliveoa.view.notice.NoticeInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DraftActivity extends AppCompatActivity {
    private RecyclerView mContentRv;
    private ImageView back, add;
    private TextView tvtip;
    private View listview;
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private DutyInfoDao dutyInfoDao;


    private ArrayList<OfficialDocument> officialDocuments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        officialDocuments = getIntent().getParcelableArrayListExtra("list");
        //Log.i("officialDocuments=",officialDocuments.toString());
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        add = (ImageView) findViewById(R.id.add);
        tvtip = (TextView)findViewById(R.id.tvtip);

        if(officialDocuments==null||officialDocuments.size()==0){
            tvtip.setVisibility(View.VISIBLE);
        }

        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new DraftContentAdapter(officialDocuments));


        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraftActivity.this, DocumentManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraftActivity.this, DraftAddActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });

    }

    public void initData() {

    }

    private class DraftContentAdapter extends RecyclerView.Adapter<DraftContentAdapter.ContentHolder> {

        private List<OfficialDocument> documents;
        public DraftContentAdapter(List<OfficialDocument> documents){
            this.documents= documents;
        }


        @Override
        public DraftContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DraftContentAdapter.ContentHolder(LayoutInflater.from(DraftActivity.this)
                    .inflate(R.layout.item_document, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, final int position) {
            //Log.i(getLocalClassName(),"document["+position+"]="+documents.get(position).toString());
            holder.itemContext.setText(documents.get(position).getContent());
            holder.itemTitle.setText(documents.get(position).getTitle());
            holder.item_document.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();

                    LoadingDialog loadingDialog = new LoadingDialog(DraftActivity.this, "正在加载数据", true);
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
                                        //Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                                        for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                            if (contactInfos.get(i).getEmpContactList().get(j).getEmployee() != null) {
                                               // Log.d(TAG, "contactInfos.get(i).getEmpContactList().get(" + j + ").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                            }
                                        }
                                    }
                                    Intent intent = new Intent(DraftActivity.this, DraftInfoActivity.class);
                                    intent.putExtra("info",documents.get(position));
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
            });
        }

        @Override
        public int getItemCount() {
            return documents.size();
        }


        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView itemTitle, itemContext;
            private CardView item_document;

            public ContentHolder(View itemView) {
                super(itemView);
                itemTitle = (TextView) itemView.findViewById(R.id.title);
                itemContext = (TextView) itemView.findViewById(R.id.content);
                item_document = (CardView) itemView.findViewById(R.id.card_view);
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
