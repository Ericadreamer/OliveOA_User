package com.oliveoa.view.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.greendao.AnnouncementInfoDao;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

public class NoticeActivity extends Fragment {
    private RecyclerView mContentRv;
    private Context mContext;
    private View rootview;
    private FloatingActionButton btn_fab;

    private boolean run = false;
    private final Handler handler = new Handler();

    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.notice_list, R.string.my_sumission};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new NoticeListActivity(), new MySubmissionActivity()};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private NoticePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private int index = 0;

    private AnnouncementInfoDao announcementInfoDao;

    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_notice, container, false);
        this.mContext = getActivity();

        initView();
        initData();
        return rootview;
    }

    public void initView() {
        btn_fab = (FloatingActionButton) rootview.findViewById(R.id.fab_add);

        mTabLayout = (TabLayout) rootview.findViewById(R.id.tab_layout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES);

        mAdapter = new NoticePagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) rootview.findViewById(R.id.info_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        //点击事件
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                announcementInfoDao =EntityManager.getInstance().getAnnouncementInfoDao();
                announcementInfoDao.deleteAll();
                AnnouncementInfo announcementInfo = new AnnouncementInfo();
                announcementInfoDao.insert(announcementInfo);
                Intent intent = new Intent(mContext, AddNoticeActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
                getActivity().finish();


            }
        });

    }

    private void initData() {
        Log.e(String.valueOf(mContext),"initData()");
        //已发布
        //我提交

    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees) {
        for (int i = 0; i < tabTitlees.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom_top, null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab_top);
            tvTitle.setText(tabTitlees[i]);
            tabLayout.addTab(tab);

        }
    }

    /**
     * @description: ViewPager 适配器
     */
    private class NoticePagerAdapter extends FragmentPagerAdapter {
        public NoticePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }
}
