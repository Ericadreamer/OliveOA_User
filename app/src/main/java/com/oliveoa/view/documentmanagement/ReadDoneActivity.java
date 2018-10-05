package com.oliveoa.view.documentmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.UserInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.pojo.UserInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.addressbook.User;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ReadDoneActivity extends Fragment {
    private Context mContext;
    private View rootview;
    private RecyclerView mContentRv;
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
                        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mContentRv.setAdapter(new ReadDoneActivity.ContentAdapter(list));
                    }else{
                        tvtip.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_read_done, container, false);
        this.mContext = getActivity();
        initViews();
        return rootview;
    }

    public void initViews() {
        ttitle = (TextView) rootview.findViewById(R.id.title);
        tcontext = (TextView) rootview.findViewById(R.id.content);
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        initData();

    }

    private class ContentAdapter extends RecyclerView.Adapter<ReadDoneActivity.ContentAdapter.ContentHolder> {
        private List<OfficialDocument> documents;
        public ContentAdapter(List<OfficialDocument> documents) {
            this.documents = documents;
        }

        @Override
        public ReadDoneActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (getItemCount() == 0) {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.notice_null, parent, false));
            } else {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_document, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(final ReadDoneActivity.ContentAdapter.ContentHolder holder, final int position) {
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
                            info(position);
                        }
                    }).start();
                }
            });
        }
        private void info(int position) {
            SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
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
                    Toast.makeText(getActivity(), "该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
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
                ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads = statusAndDataHttpResponseObject.getData().getOfficialDocumentCirculreads();
                UserInfoDao userInfoDao  = EntityManager.getInstance().getUserInfoDao();
                UserInfo userInfo = userInfoDao.queryBuilder().unique();
                if(userInfo!=null) {
                    Log.i(TAG,userInfo.toString());
                    for (int i = 0; i < officialDocumentCirculreads.size(); i++) {
                        if (officialDocumentCirculreads.get(i).getEid().equals(userInfo.getEid())) {
                            Intent intent = new Intent(getActivity(), ReadInfoActivity.class);
                            intent.putExtra("info", officialDocument);
                            intent.putExtra("read", statusAndDataHttpResponseObject.getData().getOfficialDocumentCirculreads().get(i));
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                }

            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getActivity(), statusAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
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

    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                ArrayList<OfficialDocument> temp = new ArrayList<>();
                //Todo Service
                DocumentService service = new DocumentService();
                //Todo Service.Method
                StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocument>>  statusAndMsgAndDataHttpResponseObject = service.getdocumentReaded(s);
                //ToCheck JsonBean.getStatus()
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    //Log.i("DOCUMENT=",statusAndMsgAndDataHttpResponseObject.getData().toString());
                    temp = statusAndMsgAndDataHttpResponseObject.getData();
                    Log.e(TAG,temp.toString());
                    //新建一个Message对象，存储需要发送的消息
                    Message message=new Message();
                    message.what=1;
                    message.obj=temp;
                    //然后将消息发送出去
                    handler.sendMessage(message);
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getActivity(),statusAndMsgAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
}
