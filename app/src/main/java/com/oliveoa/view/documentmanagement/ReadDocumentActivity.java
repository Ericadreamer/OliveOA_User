package com.oliveoa.view.documentmanagement;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.notice.MySubmissionActivity;
import com.oliveoa.view.notice.NoticeActivity;
import com.oliveoa.view.notice.NoticeListActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ReadDocumentActivity extends AppCompatActivity {
    private ImageView back;

    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.read_wait, R.string.read_done};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new ReadWaitActivity(), new ReadDoneActivity()};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private ReadPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_document);

        index = getIntent().getIntExtra("index",index);
        Log.d("INDEX=", String.valueOf(index));

        initView();
        initData();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES);

        mAdapter = new ReadPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.info_viewpager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                ApproveNumberDao approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
                approveNumberDao.deleteAll();
                Intent intent = new Intent(ReadDocumentActivity.this, DocumentManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initData() {

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
    private class ReadPagerAdapter extends FragmentPagerAdapter {
        public ReadPagerAdapter(FragmentManager fm) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit;
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
    }
}
