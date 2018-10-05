package com.oliveoa.view.documentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReceiveActivity extends AppCompatActivity {
    private RecyclerView mContentRv;
    private ImageView back;
    private View listview;
    private Context mcontext;

    private TextView ttitle,tcontext,tvtip;
    private String TAG = this.getClass().getSimpleName();
    private List<OfficialDocument> list;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<OfficialDocument>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(list!=null&list.size()!=0){
                        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(mcontext));
                        mContentRv.setAdapter(new ReceiveActivity.ReceiveContentAdapter(list));
                    }else{
                        tvtip.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    tvtip.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        mcontext =this;
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tvtip = (TextView)findViewById(R.id.tvtip);

//        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
//        mContentRv.setLayoutManager(new LinearLayoutManager(this));
//        mContentRv.setAdapter(new ReceiveContentAdapter());
        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiveActivity.this, DocumentManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                ArrayList<OfficialDocument> temp = new ArrayList<>();
                //Todo Service
                DocumentService service = new DocumentService();
                //Todo Service.Method
                StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>  statusAndMsgAndDataHttpResponseObject = service.getdocumentNeedReceive(s);
                //ToCheck JsonBean.getStatus()
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    //Log.i("DOCUMENT=",statusAndMsgAndDataHttpResponseObject.getData().toString());
                    if(statusAndMsgAndDataHttpResponseObject.getData()!=null) {
                        temp = statusAndMsgAndDataHttpResponseObject.getData();
                        Log.e(TAG, temp.toString());
                        //新建一个Message对象，存储需要发送的消息
                        Message message = new Message();
                        message.what = 1;
                        message.obj = temp;
                        //然后将消息发送出去
                        handler.sendMessage(message);
                    }else{
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mcontext,statusAndMsgAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private class ReceiveContentAdapter extends RecyclerView.Adapter<ReceiveContentAdapter.ContentHolder> {
        private List<OfficialDocument> documents;
        public ReceiveContentAdapter(List<OfficialDocument> documents) {
            this.documents = documents;
        }

        @Override
        public ReceiveContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ReceiveContentAdapter.ContentHolder(LayoutInflater.from(ReceiveActivity.this)
                    .inflate(R.layout.item_document, parent, false));
        }

        @Override
        public void onBindViewHolder(final ReceiveContentAdapter.ContentHolder holder, final int position) {
            holder.tvcontent.setText(documents.get(position).getContent());
            holder.tvtitle.setText(documents.get(position).getTitle());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tvtitle.getText().toString().trim() + "----" + documents.get(position).toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            receive(position);
                        }
                    }).start();
                }
            });
        }
        private void receive(int position) {
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            String s = pref.getString("sessionid", "");

            //Todo Service
            UserInfoService userInfoService = new UserInfoService();
            //Todo Service.Method
            ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

            DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
            ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
            departmentInfoDao.deleteAll();
            contactInfoDao.deleteAll();

            //ToCheck JsonBean.getStatus()
            if (contactHttpResponseObject.getStatus() == 0) {
                ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                Log.d("userinfo", contactInfos.toString());
                if (contactInfos.size() == 0) {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mcontext, "该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
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
                }
            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(mcontext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循7环，查看消息队列
            }

            //Todo Service
            DocumentService service = new DocumentService();
            //Todo Service.Method
            StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> statusAndDataHttpResponseObject = service.getdocumentInfo(s, documents.get(position).getOdid());
            //ToCheck JsonBean.getStatus()
            if (statusAndDataHttpResponseObject.getStatus() == 0) {
                                /*run = false;
                                task.run();*/
                OfficialDocument officialDocument = statusAndDataHttpResponseObject.getData().getOfficialDocument();
                Log.i(TAG,officialDocument.toString());
                Intent intent = new Intent(mcontext,ReceiveDocumentActivity.class);
                intent.putExtra("info",officialDocument);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();

            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(mcontext, statusAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
        @Override
        public int getItemCount() {
            return documents.size();
        }


        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView tvtitle, tvcontent;
            private CardView cardView;

            public ContentHolder(View itemView) {
                super(itemView);
                tvtitle = (TextView) itemView.findViewById(R.id.title);
                tvcontent = (TextView) itemView.findViewById(R.id.content);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
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
