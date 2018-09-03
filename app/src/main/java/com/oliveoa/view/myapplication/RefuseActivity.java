package com.oliveoa.view.myapplication;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;

public class RefuseActivity extends Fragment {

    private Context mContext;
    private LinearLayout addRefuselistView;
    private TextView tvtype,tvcontent;
    private ImageView ivpicture;
    private View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_refuse, container, false);
        this.mContext = getActivity();

        //默认添加一个Item
        addViewItem(null);

        initView();

        return rootview;
    }


    private void initView() {
        ivpicture = (ImageView) rootview.findViewById(R.id.application_pic);
        tvtype = (TextView) rootview.findViewById(R.id.item_type);
        tvcontent = (TextView) rootview.findViewById(R.id.item_content);
        addRefuselistView = (LinearLayout) rootview.findViewById(R.id.refuse_list);

        initData();
    }

    private void initData(){

    }

    //添加ViewItem
    private void addViewItem(View view) {
//        if (la == null&&bta==null&&oa==null) {//如果申请列表为0，加载空布局
//            Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
//        } else {//如果有申请则按数组大小加载布局
//            for(int i = 0;i <7; i ++){
//                View hotelEvaluateView = View.inflate(mContext, R.layout.item_refuse, null);
//                addRefuselistView.addView(hotelEvaluateView);
//                InitDataViewItem();
//
//            }
//            sortHotelViewItem();
//
//        }

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addRefuselistView.getChildCount(); i++) {
            final View childAt = addRefuselistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            //final View child = LayoutInflater.from(mContext).inflate(R.layout.item_list_1, null);
            //final LinearLayout item =childAt.findViewWithTag(child);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "你点击了www", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {

    }
}
