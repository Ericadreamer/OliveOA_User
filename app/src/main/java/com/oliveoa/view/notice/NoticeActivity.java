package com.oliveoa.view.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.view.R;

public class NoticeActivity extends Fragment {
    private RecyclerView mContentRv;
    private Context mContext;
    private View rootview;
    private FloatingActionButton btn_fab;
    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_notice, container, false);
        this.mContext = getActivity();

        initView();
        initData();

        return rootview;

    }

    public void initView() {
        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_fab = (FloatingActionButton)rootview.findViewById(R.id.fab_add);
        mContentRv.setAdapter(new ContentAdapter());


        //点击事件
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddNoticeActivity.class);
                startActivity(intent);
            }
        });

    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_notice, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
            //holder.itemTv.setText("Item "+new DecimalFormat("00").format(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            private TextView itemTv;

            public ContentHolder(View itemView) {
                super(itemView);
                itemTv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }

    }

    public void initData() {

    }
}
