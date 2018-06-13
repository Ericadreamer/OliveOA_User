package com.oliveoa.view.myapplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oliveoa.view.R;

public class RefuseActivity extends Fragment {

    private Context mContext;
    private LinearLayout addPasslistView;
    private TextView tvname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_refuse, container, false);
        this.mContext = getActivity();
        tvname = (TextView) rootview.findViewById(R.id.item_name);
        addPasslistView = (LinearLayout) rootview.findViewById(R.id.refuse_list);

        //默认添加一个Item
        addViewItem(null);

        return rootview;
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
