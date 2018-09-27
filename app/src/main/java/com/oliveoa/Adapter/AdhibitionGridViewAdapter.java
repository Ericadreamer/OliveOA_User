package com.oliveoa.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveoa.view.R;

/**
 * @Author：Guo 时间:2018/9/26 0026
 * 项目名:OliveOA_User
 * 包名:com.oliveoa.Adapter
 * 类名:
 * 简述:<功能简述>
 */
public class AdhibitionGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private String[] Title;//显示标题数组
    private int[] Imgs;//显示图标数组

    public AdhibitionGridViewAdapter(Context mContext,int[] imgs ,String[] titles) {
        super();
        this.mContext = mContext;
        Title = titles;
        Imgs = imgs;
    }

    @Override
    public int getCount() {
        return Title.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = loadHomeHyOrYs(position, convertView, parent);
        return convertView;
    }

    /**
     * 加载列表
     */
    public View loadHomeHyOrYs(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.gridview_item, parent, false);
        }
        TextView tv = get(convertView, R.id.text);
        ImageView iv = get(convertView, R.id.image);
        iv.setBackgroundResource(Imgs[position]);
        tv.setText(Title[position]);
        return convertView;
    }

    public <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
