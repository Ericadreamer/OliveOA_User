package com.oliveoa.view.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oliveoa.view.R;


public class WaitActivity extends Fragment {

    private Context mContext;
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addWaitlistView;
    private TextView tvname;
    private View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_wait, container, false);
        this.mContext = getActivity();

        //默认添加一个Item
        addViewItem(null);

        return rootview;
    }

    private void initView() {
        tvname = (TextView) rootview.findViewById(R.id.item_name);
        addWaitlistView = (LinearLayout) rootview.findViewById(R.id.wait_list);
    }

    //添加ViewItem
    private void addViewItem(View view) {

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {

    }

}
