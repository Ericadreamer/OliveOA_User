package com.oliveoa.view.workschedule;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.view.R;

public class MyProtocolActivity extends Fragment {
    private View rootview;
    private Context mContext;
    private RecyclerView mContentRv;
    private TextView tcontext,ttime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_my_protocol, container, false);
        this.mContext = getActivity();

        initView();

        return rootview;
    }

    public void initView() {
        tcontext = (TextView) rootview.findViewById(R.id.work_content);
        ttime = (TextView) rootview.findViewById(R.id.work_time);

        initData();
    }

    public void initData() {

    }
}
