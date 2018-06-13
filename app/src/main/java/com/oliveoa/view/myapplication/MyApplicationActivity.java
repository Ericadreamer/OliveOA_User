package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.fragment.TabListFragment;
import com.oliveoa.view.AdhibitionActivity;
import com.oliveoa.view.MainActivity;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.addressbook.AddressBookActivity;
import com.oliveoa.view.mine.MineActivity;
import com.oliveoa.view.notice.NoticeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyApplicationActivity extends AppCompatActivity {

    private TabLayout mIndicatorTl;
    private ViewPager mContentVp;
    private ImageView back,add;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.wait,R.string.pass,R.string.refuse};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[] {new WaitActivity(),new PassActivity(),new RefuseActivity()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application);

        mIndicatorTl = (TabLayout) findViewById(R.id.tl_indicator);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initTab();
        initView();
        initContent(mIndicatorTl,this.getLayoutInflater(),TAB_TITLES);
    }

    private void initView() {
        back = (ImageView)findViewById(R.id.iback);
        add = (ImageView)findViewById(R.id.iadd);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplicationActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApplicationActivity.this, AddApplicationActivity.class);
                /*intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfos);
                intent.putExtra("index",departmentInfos.size());
                setAddDepartmentinfo(departmentInfos.size());*/
                startActivity(intent);
                finish();
            }
        });

    }

    //tab的字体和线条颜色
    private void initTab(){
        mIndicatorTl.setTabMode(TabLayout.MODE_FIXED);
        mIndicatorTl.setTabTextColors(ContextCompat.getColor(this, R.color.tv_gray_deep), ContextCompat.getColor(this, R.color.tab_tv_selected));
        mIndicatorTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tab_tv_selected));
        ViewCompat.setElevation(mIndicatorTl, 10);
        mIndicatorTl.setupWithViewPager(mContentVp);
    }

    /**
     * @description: 设置添加Tab
     */
    private void initContent(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees){
        for (int i = 0; i < tabTitlees.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom_top,null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView)view.findViewById(R.id.tv_tab_top);
            tvTitle.setText(tabTitlees[i]);

            tabLayout.addTab(tab);

        }
    }


    /**
     * @description: ViewPager 适配器
     */

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
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

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }
    *//**
     * 双击退出函数
     *//*
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }*/
}