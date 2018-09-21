package com.oliveoa.view.approval;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 当前类注释:使用google支持库实现Tab标签
 */

public class MyApprovalActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.undisposed,R.string.disposed};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[] {new UndisposedApprovalActivity(),new DisposedApprovalActivity()};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private ApprovalPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private int index;
    private ImageView back;
    private ApprovalDao approvalDao;
    private String TAG = this.getClass().getSimpleName();
    private TextView tvtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_approval);
        index = getIntent().getIntExtra("index",index);//加班1、请假2、出差3、会议4、离职5、转正6、调岗7、招聘8、公告9
        Log.d("INDEX=", String.valueOf(index));

        initViews();
    }
    private void initViews() {
        back = (ImageView) findViewById(R.id.iback);
        tvtitle = (TextView)findViewById(R.id.tvtitle);
        if(index==1){
            tvtitle.setText("加班申请审批");
        }
        if(index==2){
            tvtitle.setText("请假申请审批");
        }
        if(index==3){
            tvtitle.setText("出差申请审批");
        }
        if(index==4){
            tvtitle.setText("会议申请审批");
        }
        if(index==5){
            tvtitle.setText("离职申请审批");
        }
        if(index==6){
            tvtitle.setText("转正申请审批");
        }
        if(index==7){
            tvtitle.setText("调岗申请审批");
        }
        if(index == 8){
            tvtitle.setText("招聘申请审批");
        }
        if(index == 9){
            tvtitle.setText("公告发布审批");
        }

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        setTabs(mTabLayout,this.getLayoutInflater(),TAB_TITLES);

        mAdapter = new ApprovalPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.info_viewpager);
        initData();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //点击事件

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approvalDao = EntityManager.getInstance().getApprovalDao();
                approvalDao.deleteAll();
                Intent intent = new Intent(MyApprovalActivity.this, MainApprovalActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }

   /* //tab的阴影
    private void initTab(){
        ViewCompat.setElevation(mTabLayout, 10);
//        mIndicatorTl.setupWithViewPager(mContentVp);
    }
*/
   private void initData() {
       approvalDao = EntityManager.getInstance().getApprovalDao();

       List<Approval> ap = null;
       Bundle unaprrovalbundle = new Bundle();
       unaprrovalbundle.putString("approval","");
       Bundle approvalbundle = new Bundle();
       approvalbundle.putString("approval","");

       //未审批
       QueryBuilder qb = approvalDao.queryBuilder();
       ap = approvalDao.queryBuilder().where(ApprovalDao.Properties.Status.eq(0)).list();
       ArrayList ap1 =(ArrayList<Approval>) ap;
       Log.e(TAG,"ArrayList<Approval> ap = "+ap1.toString());
       if(ap!=null){
           unaprrovalbundle.putParcelableArrayList("approval",ap1);
       }
       Log.e(TAG,"unaprrovalbundle = "+unaprrovalbundle.toString());
       mAdapter.getItem(0).setArguments(unaprrovalbundle);

       //已审批
       ap = approvalDao.queryBuilder().where(ApprovalDao.Properties.Status.eq(1)).list();
       ap1 =(ArrayList<Approval>) ap;
       Log.e(TAG,"ArrayList<Approval> ap = "+ap1.toString());
       if(ap!=null){
           approvalbundle.putParcelableArrayList("approval",ap1);
       }
       mAdapter.getItem(1).setArguments(approvalbundle);
   }


    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees){
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
    private class ApprovalPagerAdapter extends FragmentPagerAdapter {
        public ApprovalPagerAdapter(FragmentManager fm) {
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
    }
}
