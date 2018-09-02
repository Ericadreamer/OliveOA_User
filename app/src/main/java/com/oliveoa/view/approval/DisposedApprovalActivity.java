package com.oliveoa.view.approval;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;

/**
 * 该页面显示已处理的审批列表，加载approval_item.xml列表
 * 未处理跟已处理页面加载同一个item，两者不同的是要显示item的状态是审批中（灰色）、同意（绿色）、不同意（红色）
 */

public class DisposedApprovalActivity extends Fragment {

    private Context mContext;
    private LinearLayout addlistView;
    private TextView tname,tcontent,ttype,tstatus;  //名字，内容，类型，状态
    private View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_disposed_approval, container, false);
        this.mContext = getActivity();

        //默认添加一个Item
        addViewItem(null);

        initView();
        initData();
        return rootview;
    }

    public void initView(){
        addlistView = (LinearLayout) rootview.findViewById(R.id.approval_list);
        tname = (TextView) rootview.findViewById(R.id.person_name);
        tcontent = (TextView) rootview.findViewById(R.id.approval_type);
        ttype = (TextView) rootview.findViewById(R.id.type);
        tstatus = (TextView) rootview.findViewById(R.id.status);

    }

    private void initData(){

    }

    //添加ViewItem,R.layout.approval_item
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
