package com.oliveoa.view.documentmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.view.R;

public class NuclearWaitActivity extends Fragment {
    private Context mContext;
    private View rootview;
    private RecyclerView mContentRv;
    private TextView ttitle,tcontext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_nuclear_wait, container, false);
        this.mContext = getActivity();

        initViews();
        initData();

        return rootview;
    }

    public void initViews() {
        ttitle = (TextView) rootview.findViewById(R.id.title);
        tcontext = (TextView) rootview.findViewById(R.id.content);

    }

    public void initData() {

    }
}
