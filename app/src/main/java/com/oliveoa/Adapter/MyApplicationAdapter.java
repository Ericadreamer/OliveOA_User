package com.oliveoa.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.pojo.Application;
import com.oliveoa.view.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyApplicationAdapter extends RecyclerView.Adapter<MyApplicationAdapter.ViewHolder> {
    private Context mContext;
    private List<Application> mapplicationList;
    /*private List<LeaveApplication> leaveApplications;
    private List<OvertimeApplication> overtimeApplications;
    private List<BusinessTripApplication> businessTripApplications;*/

    public MyApplicationAdapter(Context mContext, List<Application> mapplicationList) {
        this.mContext = mContext;
        this.mapplicationList = mapplicationList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listView;
        TextView tvtype,tvcontent;  //申请类型和申请内容
        ImageView ivpicture;  //申请类型对应的图片


        public ViewHolder(View view) {
            super(view);
            listView = (LinearLayout) view.findViewById(R.id.wait_list);
            ivpicture = (ImageView) view.findViewById(R.id.application_pic);
            tvtype = (TextView) view.findViewById(R.id.item_type);
            tvcontent = (TextView) view.findViewById(R.id.item_content);
        }
    }

    @Override
    public MyApplicationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.e(" MyApplicationAdapter", "viewType = " + viewType);
        View view = null;
        if (viewType == 2) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_null, viewGroup, false);
            ViewHolder nullview =new ViewHolder(view);
            return nullview;
        } else if(viewType == 0||viewType ==-2){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wait,viewGroup,false);
            ViewHolder v_wait=new ViewHolder(view);
            return v_wait;
        }else if(viewType == -1){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_refuse,viewGroup,false);
            ViewHolder v_refuse=new ViewHolder(view);
            return v_refuse;
        }else if(viewType == 1){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pass,viewGroup,false);
            ViewHolder v_pass=new ViewHolder(view);
            return v_pass;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
       /* if (holder instanceof CommentFirstHolder) {
            //TODO
        } else if (holder instanceof CommentSecondHolder) {
            //TODO
        }*/
        final int j=i;
        holder.tvcontent.setText(mapplicationList.get(i).getDescribe());
        if(mapplicationList.get(i).getType()==1){
            holder.tvtype.setText("加班申请");
            holder.ivpicture.setImageResource(R.drawable.overtime_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("请假申请");
            holder.ivpicture.setImageResource(R.drawable.leave_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("出差申请");
            holder.ivpicture.setImageResource(R.drawable.business_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("会议申请");
            holder.ivpicture.setImageResource(R.drawable.meeting_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("离职申请");
            holder.ivpicture.setImageResource(R.drawable.dimission_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("转正申请");
            holder.ivpicture.setImageResource(R.drawable.regular_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("调岗申请");
            holder.ivpicture.setImageResource(R.drawable.adjust_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("招聘申请");
            holder.ivpicture.setImageResource(R.drawable.recruitment_pic);
        }else if(mapplicationList.get(i).getType()==2){
            holder.tvtype.setText("物品申请");
            holder.ivpicture.setImageResource(R.drawable.goods_pic);
        }

        //为btn_edit btn_delete  cardView设置点击事件
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HandlerThread handlerThread = new HandlerThread("HandlerThread");
                handlerThread.start();

                Handler mHandler = new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                        String s = pref.getString("sessionid","");

                        Toast.makeText(mContext,"你点击了"+holder.tvcontent.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                mHandler.sendEmptyMessage(1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mapplicationList.size();
    }

    /**
     * 决定元素的布局使用哪种类型
     *
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数 */
    @Override
    public int getItemViewType(int position) {
        return mapplicationList.get(position).getStatus();
    }

    /*  //第一个ViewHolder
    public class CommentFirstHolder extends ViewHolder {
        public CommentFirstHolder(View itemView) {
            super(itemView);
        }
    }

    //第二个ViewHolder
    public class CommentSecondHolder extends ViewHolder {
        public CommentSecondHolder(View itemView) {
            super(itemView);
        }
    }
   */

}
