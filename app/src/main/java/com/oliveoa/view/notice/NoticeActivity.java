package com.oliveoa.view.notice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
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
import com.oliveoa.view.note.EditNoteActivity;
import com.oliveoa.view.note.MyNoteActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoticeActivity extends Fragment {
    private RecyclerView mContentRv;
    private Context mContext;
    private View rootview;
    private FloatingActionButton btn_fab;

    private AnnouncementInfoDao announcementInfoDao;
    private List<AnnouncementInfo> announcementInfos;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();
    private boolean run = false;
    private final Handler handler = new Handler();

    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_notice, container, false);
        this.mContext = getActivity();

        initView();

        //一秒刷新一次
        run = true;
        handler.postDelayed(task, 3000);
        return rootview;
    }

    public void initView() {
        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_fab = (FloatingActionButton)rootview.findViewById(R.id.fab_add);
        initData();
        mContentRv.setAdapter(new ContentAdapter());


        //点击事件
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run =false;
                task.run();
                announcementInfoDao.deleteAll();
                AnnouncementInfo announcementInfo = new AnnouncementInfo();
                announcementInfoDao.insert(announcementInfo);
                Intent intent = new Intent(mContext, AddNoticeActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                getActivity().finish();


            }
        });

    }



    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(getItemCount()==0){
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.notice_null, parent, false));
            }else {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_notice, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(final ContentAdapter.ContentHolder holder, final int position) {
            DateFormat dateFormat = new DateFormat();
            holder.tvcontent.setText(announcementInfos.get(position).getContent());
            holder.tvtime.setText(dateFormat.LongtoDatemm(announcementInfos.get(position).getPublishtime()));
            holder.tvtitle.setText(announcementInfos.get(position).getTitle());
            holder.tvname.setText(announcementInfos.get(position).getSignature());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,holder.tvtitle.getText().toString().trim()+"----"+announcementInfos.get(position).toString());
                       new Thread(new Runnable() {
                                   @Override
                                   public void run() {
                                       SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                                       String s = pref.getString("sessionid","");

                                       //Todo Service
                                       AnnouncementService service = new AnnouncementService();
                                       //Todo Service.Method
                                       StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> announcementInfoJsonBeanStatusAndDataHttpResponseObject = service.get_annoucementinfo(s,announcementInfos.get(position).getAid());
                                       //ToCheck JsonBean.getStatus()
                                       if (announcementInfoJsonBeanStatusAndDataHttpResponseObject.getStatus() == 0) {
                                           run = false;
                                           task.run();
                                           ArrayList<AnnouncementApprovedOpinionList> announcementApprovedOpinionLists = announcementInfoJsonBeanStatusAndDataHttpResponseObject.getData().getAnnouncementApprovedOpinionList();
                                           Intent intent = new Intent(getActivity(), NoticeInfoActivity.class);
                                           intent.putExtra("notice",announcementInfos.get(position));
                                           intent.putExtra("list",announcementApprovedOpinionLists);
                                           startActivity(intent);
                                           getActivity().finish();

                                       } else {
                                           Looper.prepare();//解决子线程弹toast问题
                                           Toast.makeText(getActivity(),"获取公告详情", Toast.LENGTH_SHORT).show();
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

        class ContentHolder extends RecyclerView.ViewHolder{

            private TextView tvtitle,tvcontent,tvtime,tvname;
            private CardView cardView;

            public ContentHolder(View itemView) {
                super(itemView);
                tvtitle = (TextView) itemView.findViewById(R.id.title);
                tvcontent = (TextView) itemView.findViewById(R.id.content);
                tvtime = (TextView) itemView.findViewById(R.id.time);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
                tvname = (TextView)itemView.findViewById(R.id.name);
            }
        }

    }

    public void initData() {
        announcementInfoDao = EntityManager.getInstance().getAnnouncementInfoDao();
        announcementInfos = announcementInfoDao.queryBuilder().orderDesc(AnnouncementInfoDao.Properties.Orderby).list();
        contactInfoDao = EntityManager.getInstance().getContactInfo();

        Log.e(TAG,announcementInfos.toString());
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                //doSomething();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                        String s = pref.getString("sessionid","");
                                  /* DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                                   ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
                                   departmentInfoDao.deleteAll();
                                   contactInfoDao.deleteAll();*/
                        AnnouncementInfoDao announcementInfoDao = EntityManager.getInstance().getAnnouncementInfoDao();
                        announcementInfoDao.deleteAll();

                                   /*UserInfoService userInfoService = new UserInfoService();
                                   ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);
                                   if(contactHttpResponseObject.getStatus()==0){
                                       ArrayList<ContactJsonBean> contactJsonBean = contactHttpResponseObject.getData();
                                       for(int i =0;i<contactJsonBean.size();i++){
                                           departmentInfoDao.insert(contactJsonBean.get(i).getDepartment());
                                           for(int j=0;j<contactJsonBean.get(i).getEmpContactList().size();j++){

                                               contactInfoDao.insert(contactJsonBean.get(i).getEmpContactList().get(j).getEmployee());
                                           }
                                       }
                                   }else{
                                       Looper.prepare();//解决子线程弹toast问题
                                       Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                                       Looper.loop();// 进入loop中的循环，查看消息队列
                                   }*/
                        AnnouncementService announcementService = new AnnouncementService();
                        AnnouncementJsonBean announcementJsonBean = announcementService.get_published_annoucements(s);
                        if(announcementJsonBean.getStatus()==0){
                            List<AnnouncementInfo> announcementInfos = announcementJsonBean.getData();
                            Log.e("notice",announcementInfos.toString());
                            for (int i=0;i<announcementInfos.size();i++){
                                announcementInfoDao.insert(announcementInfos.get(i));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, "网络错误，获取公告信息失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                }).start();
                handler.postDelayed(this, 3000);
            }
        }
    };

}
