package com.oliveoa.view.addressbook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.mine.PersonalDetailsActivity;

import java.util.List;


/**
 * @Author：Guo 时间:2018/9/11 0011
 * 项目名:OliveOA_User
 * 包名:com.oliveoa.view.addressbook
 * 类名:
 * 简述:<功能简述>
 */
public class SortAdapter extends BaseAdapter{

    private List<User> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<User> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder;
        final User user = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.catalog = (TextView) view.findViewById(R.id.catalog);
            viewHolder.linearLayout = (LinearLayout)view.findViewById(R.id.contact_content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取首字母作为目录catalog
        String catalog = list.get(position).getFirstLetter();

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(catalog)){
            viewHolder.catalog.setVisibility(View.VISIBLE);
            viewHolder.catalog.setText(user.getFirstLetter().toUpperCase());
        }else{
            viewHolder.catalog.setVisibility(View.GONE);
        }

        viewHolder.name.setText(this.list.get(position).getName());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("name=",list.get(position).getName()+"-----"+list.get(position).getEid());
                ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
                ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(list.get(position).getEid())).unique();
                if(ci!=null){
                    Intent intent = new Intent(mContext,PersonalDetailsActivity.class);
                    intent.putExtra("ci",ci);
                    mContext.startActivity(intent);
                }
            }
        });
        return view;
    }

    final static class ViewHolder {
        TextView catalog;
        TextView name;
        LinearLayout linearLayout;
    }

    /**
     * 获取catalog首次出现位置
     */
    public int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }

}
