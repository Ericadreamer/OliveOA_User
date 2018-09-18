package com.oliveoa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveoa.view.R;

/**
 * @Author：Guo 时间:2018/9/18 0018
 * 项目名:OliveOA_User
 * 包名:com.oliveoa.Adapter
 * 类名:
 * 简述:<功能简述> 我的审批九宫格
 */
public class ApprovalGridViewAdapter extends BaseAdapter {
    private Context context;

    public ApprovalGridViewAdapter(Context context){
        this.context=context;
    }

    //九宫格图片设置
    private Integer[] images={
            R.drawable.overtime_pic,
            R.drawable.leave_pic,
            R.drawable.business_pic,
            R.drawable.meeting_pic,
            R.drawable.dimission_pic,
            R.drawable.regular_pic,
            R.drawable.adjust_pic,
            R.drawable.recruitment_pic,
            R.drawable.notice_pic,
            /*R.drawable.goods_pic,*/
    };

    //九宫格图片下方文字设置
    private String[] texts={
            "加班申请",
            "请假申请",
            "出差申请",
            "会议申请",
            "离职申请",
            "转正申请",
            "调岗申请",
            "招聘申请",
            "公告"
            /* "物品申请",*/
    };

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageTextWrapper wrapper;
        if(view==null){
            wrapper = new ImageTextWrapper();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.gridview_item,null);
            view.setTag(wrapper);
            view.setPadding(10,22,10,22);  //每格的间距
        }
        else{
            wrapper = (ImageTextWrapper)view.getTag();
        }

        wrapper.imageView = (ImageView)view.findViewById(R.id.image);
        wrapper.imageView.setBackgroundResource(images[i]);
        wrapper.textView = (TextView)view.findViewById(R.id.text);
        wrapper.textView.setText(texts[i]);

        return view;

    }
}

class ImageTextWrapper {
    ImageView imageView;
    TextView textView;
}
