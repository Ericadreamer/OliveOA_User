package com.oliveoa.view.meetingmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oliveoa.view.R;

public class WillStartActivity extends Fragment {
    private View rootview;
    private TextView tvtip;
    private Context mContext;
    private LinearLayout addlistView;
    private TextView tvtype,tvcontent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_list, container, false);
        this.mContext = getActivity();

        initView();
        return rootview;
    }

    private void initView() {
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        addlistView = (LinearLayout) rootview.findViewById(R.id.my_work_list);
        addViewItem(null);
    }

    /**
     * Item排序
     */
    private void sortViewItem() {

    }

    //添加ViewItem
    private void addViewItem(View view) {

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {

    }
}
