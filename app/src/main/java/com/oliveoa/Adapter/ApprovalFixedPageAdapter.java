package com.oliveoa.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.oliveoa.view.R;

import java.util.List;
/**
 * 当前类注释:Fragment，Viewpager的自定义适配器
 */

public class ApprovalFixedPageAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private LayoutInflater mInflater;
    public void setTitles(String[] titles) {
        this.titles = titles;
    }
    private List<Fragment> fragments;
    public ApprovalFixedPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment=null;
        try {
            fragment=(Fragment)super.instantiateItem(container,position);
        }catch (Exception e){

        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
    //此方法用来显示tab上的名字
    /*@Override
    public CharSequence getPageTitle(int position) {

        return titles[position % titles.length];
    }*/
    public List<Fragment> getFragments() {
        return fragments;
    }
    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     * @param position
     * @return
     */
    public View getTabView(int position){
        //mInflater=LayoutInflater.from(FDApplication.getInstance());
        View view=mInflater.inflate(R.layout.approval_item,null);
        LinearLayout approvalList= (LinearLayout) view.findViewById(R.id.approval_list);
        approvalList.setBackgroundResource(Integer.parseInt(titles[position]));
        return view;
    }
}
