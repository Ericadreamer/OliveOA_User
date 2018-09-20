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
 * @Author：Guo 时间:2018/9/20 0020
 * 项目名:OliveOA_User
 * 包名:com.oliveoa.Adapter
 * 类名:
 * 简述:<功能简述>
 */
public class WorkGridViewAdapter extends BaseAdapter {
    private Context context;

    public WorkGridViewAdapter(Context context){
        this.context=context;
    }

    //九宫格图片设置
    private Integer[] images={
            R.drawable.protocol_work_pic,
            R.drawable.leadership_approval_pic,
            R.drawable.allocation_pic,
    };

    //九宫格图片下方文字设置
    private String[] texts={
            "工作拟定",
            "领导批阅",
            "工作分配",
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
        WorkImgTextWrapper wrapper;
        if(view==null){
            wrapper = new WorkImgTextWrapper();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.gridview_item,null);
            view.setTag(wrapper);
            view.setPadding(10,22,10,22);  //每格的间距
        }
        else{
            wrapper = (WorkImgTextWrapper)view.getTag();
        }

        wrapper.imageView = (ImageView)view.findViewById(R.id.image);
        wrapper.imageView.setBackgroundResource(images[i]);
        wrapper.textView = (TextView)view.findViewById(R.id.text);
        wrapper.textView.setText(texts[i]);

        return view;

    }
}

class WorkImgTextWrapper {
    ImageView imageView;
    TextView textView;
}
