package com.oliveoa.view.workschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.view.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ToMeActivity extends Fragment {
    private View rootview;
    private Context mContext;
    private RecyclerView mContentRv;
    private TextView tvcontent,tvtime,tvtip;
    private WorkdetailAndStatusDao workDetailDao;
    private List<WorkdetailAndStatus> list;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();
    private LinearLayout addlistView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_to_me, container, false);
        this.mContext = getActivity();

        Bundle bundle =getArguments();
        list = bundle.getParcelableArrayList("work");
        Log.e(TAG,"work"+list.toString());

        initView();

        return rootview;
    }
    public void initView() {
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        if(list!=null&list.size()!=0){
            mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
            mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mContentRv.setAdapter(new ToMeActivity.ContentAdapter(list));
        }else{
            tvtip.setVisibility(View.VISIBLE);
        }

    }
    private class ContentAdapter extends RecyclerView.Adapter<ToMeActivity.ContentAdapter.ContentHolder> {
        private List<WorkdetailAndStatus> workDetails;

        public ContentAdapter(List<WorkdetailAndStatus> workDetails) {
            this.workDetails = workDetails;
        }
        @Override
        public ToMeActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, String.valueOf(getItemCount()));
            return new ToMeActivity.ContentAdapter.ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.my_work_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ToMeActivity.ContentAdapter.ContentHolder holder, final int position) {
            DateFormat dateFormat = new DateFormat();
            holder.tvcontent.setText(workDetails.get(position).getTheme());
            holder.tvtime.setText(workDetails.get(position).getStarttime()+"-"+workDetails.get(position).getEndtime());
           /* holder.tvcontent.setText("啊啊啊不要再出现bug了");
            holder.tvtime.setText("2018-09-01 - 2018-09-20");*/
            Log.e(TAG,holder.tvcontent.getText().toString().trim()+"---"+holder.tvtime.getText().toString().trim());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,"你点击了"+holder.tvcontent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tvcontent.getText().toString().trim() + "----" + workDetails.get(position).toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                            String s = pref.getString("sessionid", "");

                            //Todo Service
                            WorkDetailService service = new WorkDetailService();
                            //Todo Service.Method
                            StatusAndMsgAndDataHttpResponseObject<ArrayList<IssueWork>> statusAndDataHttpResponseObject = service.getIssueworktome(s,0);
                            //ToCheck JsonBean.getStatus()
                            if (statusAndDataHttpResponseObject.getStatus() == 0) {
                                List<IssueWork> work = statusAndDataHttpResponseObject.getData();
                                for(int i =0;i<work.size();i++) {
                                    if(work.get(i).getIwid().equals(workDetails.get(position).getWaid())) {
                                        Intent intent = new Intent(getActivity(), ToMyWorkInfoActivity.class);
                                        intent.putExtra("work", work.get(i));
                                        startActivity(intent);
                                        getActivity().finish();
                                        break;
                                    }
                                }

                            } else {
                                Looper.prepare();//解决子线程弹toast问题
                                Toast.makeText(getActivity(), "获取分配与我工作详情失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                            }
                        }
                    }).start();
                }
            });
        }

        @Override
        public int getItemCount() {
            return workDetails.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView tvcontent, tvtime;
            private LinearLayout linearLayout;

            public ContentHolder(View itemView) {
                super(itemView);
                tvcontent = (TextView) itemView.findViewById(R.id.work_content);
                tvtime = (TextView) itemView.findViewById(R.id.work_time);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.my_work_item);

            }
        }

    }


}
