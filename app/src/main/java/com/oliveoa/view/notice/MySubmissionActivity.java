package com.oliveoa.view.notice;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.view.R;

public class MySubmissionActivity extends Fragment {
    private View rootview;
    private Context mContext;
    private RecyclerView mContentRv;
    private TextView tvtitle, tvcontent, tvtime, tvname;
    private CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_my_submission, container, false);
        this.mContext = getActivity();

        initView();
        initData();

        return rootview;
    }

    public void initView() {
        tvtitle = (TextView) rootview.findViewById(R.id.title);
        tvcontent = (TextView) rootview.findViewById(R.id.content);
        tvtime = (TextView) rootview.findViewById(R.id.time);
        cardView = (CardView) rootview.findViewById(R.id.card_view);
        tvname = (TextView) rootview.findViewById(R.id.name);
        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mContentRv.setAdapter(new MyContentAdapter());
    }

    public void initData() {

    }

}