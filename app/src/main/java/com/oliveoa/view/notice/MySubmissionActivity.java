package com.oliveoa.view.notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.AnnouncementService;
import com.oliveoa.greendao.AnnouncementInfoDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MySubmissionActivity extends Fragment {
    private View rootview;
    private Context mContext;
    private RecyclerView mContentRv;
    private TextView tvtitle, tvcontent, tvtime, tvname,tvtip;
    private CardView cardView;
    private AnnouncementInfoDao announcementInfoDao;
    private List<AnnouncementInfo> notice;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notice = (List<AnnouncementInfo>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(notice!=null&notice.size()!=0){
                        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mContentRv.setAdapter(new MySubmissionActivity.ContentAdapter(notice));
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
        rootview = inflater.inflate(R.layout.activity_my_submission, container, false);
        this.mContext = getActivity();

        initView();

        return rootview;
    }

    public void initView() {
        tvtitle = (TextView) rootview.findViewById(R.id.title);
        tvcontent = (TextView) rootview.findViewById(R.id.content);
        tvtime = (TextView) rootview.findViewById(R.id.time);
        cardView = (CardView) rootview.findViewById(R.id.card_view);
        tvname = (TextView) rootview.findViewById(R.id.name);
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        initData();
    }

    private class ContentAdapter extends RecyclerView.Adapter<MySubmissionActivity.ContentAdapter.ContentHolder> {
        private List<AnnouncementInfo> announcementInfos;
        public ContentAdapter(List<AnnouncementInfo> announcementInfos) {
            this.announcementInfos = announcementInfos;
        }
        @Override
        public MySubmissionActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (getItemCount() == 0) {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.notice_null, parent, false));
            } else {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_notice, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(final MySubmissionActivity.ContentAdapter.ContentHolder holder, final int position) {
            DateFormat dateFormat = new DateFormat();
            holder.tvcontent.setText(announcementInfos.get(position).getContent());
            holder.tvtime.setText(dateFormat.LongtoDatemm(announcementInfos.get(position).getPublishtime()));
            holder.tvtitle.setText(announcementInfos.get(position).getTitle());
            holder.tvname.setText(announcementInfos.get(position).getSignature());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tvtitle.getText().toString().trim() + "----" + announcementInfos.get(position).toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                            String s = pref.getString("sessionid", "");

                            //Todo Service
                            AnnouncementService service = new AnnouncementService();
                            //Todo Service.Method
                            StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> announcementInfoJsonBeanStatusAndDataHttpResponseObject = service.get_annoucementinfo(s, announcementInfos.get(position).getAid());
                            //ToCheck JsonBean.getStatus()
                            if (announcementInfoJsonBeanStatusAndDataHttpResponseObject.getStatus() == 0) {
                                /*run = false;
                                task.run();*/
                                ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionLists = announcementInfoJsonBeanStatusAndDataHttpResponseObject.getData().getAnnouncementApprovedOpinionList();
                                Intent intent = new Intent(getActivity(), NoticeInfoActivity.class);
                                intent.putExtra("notice", announcementInfos.get(position));
                                intent.putExtra("list", announcementApprovedOpinionLists);
                                startActivity(intent);
                                getActivity().finish();

                            } else {
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getActivity(), "获取公告详情", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                            }
                        }
                    }).start();
                }
            });
        }

        @Override
        public int getItemCount() {
            return announcementInfos.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView tvtitle, tvcontent, tvtime, tvname;
            private CardView cardView;

            public ContentHolder(View itemView) {
                super(itemView);
                tvtitle = (TextView) itemView.findViewById(R.id.title);
                tvcontent = (TextView) itemView.findViewById(R.id.content);
                tvtime = (TextView) itemView.findViewById(R.id.time);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
                tvname = (TextView) itemView.findViewById(R.id.name);
            }
        }

    }

    public void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                AnnouncementService announcementService = new AnnouncementService();
                AnnouncementJsonBean announcementJsonBean = announcementService.get_isubmitannoucements(s);
                if(announcementJsonBean.getStatus()==0){
                    List<AnnouncementInfo> announcementInfos = announcementJsonBean.getData();
                    Log.e(TAG,announcementInfos.toString());
                    //新建一个Message对象，存储需要发送的消息
                    Message message=new Message();
                    message.what=1;
                    message.obj=announcementInfos;
                    //然后将消息发送出去
                    handler.sendMessage(message);

                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "网络错误，获取公告信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();

    }


}