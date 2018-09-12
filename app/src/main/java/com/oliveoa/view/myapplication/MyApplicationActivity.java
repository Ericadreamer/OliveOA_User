package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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

import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.pojo.Application;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.ProgressDialog.show;

public class MyApplicationActivity extends AppCompatActivity {

    private TabLayout mIndicatorTl;
    private ViewPager mContentVp;
    private ImageView back,add;
    private TextView tvtitle;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.wait,R.string.pass,R.string.refuse};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[] {new WaitActivity(),new PassActivity(),new RefuseActivity()};

    private int index;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application ;
    private BusinessTripApplicationDao btaDao;
    private BusinessTripApplicationApprovedOpinionListDao btaaolDao;
    private LeaveApplicationDao laDao;
    private LeaveApplicationApprovedOpinionListDao laaolDao;
    private OvertimeApplicationDao oaDao;
    private OvertimeApplicationApprovedOpinionListDao oaaolDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application);

        index = getIntent().getIntExtra("index",index);//加班1、请假2、出差3、会议4、离职5、转正6、调岗7、招聘8、物品9
        Log.e(TAG,"INDEX="+index);
        initView();

        mIndicatorTl = (TabLayout) findViewById(R.id.tl_indicator);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        initData();
        mContentVp.setAdapter(contentAdapter);
        mContentVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mIndicatorTl));
        mIndicatorTl.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mContentVp));//点击显示子页面

        initTab();

        initContent(mIndicatorTl,this.getLayoutInflater(),TAB_TITLES);
    }

    private void initView() {
        back = (ImageView)findViewById(R.id.iback);
        tvtitle = (TextView)findViewById(R.id.tvtitle);
        if(index==1){
            tvtitle.setText("加班申请");
        }
        if(index==2){
            tvtitle.setText("请假申请");
        }
        if(index==3){
            tvtitle.setText("出差申请");
        }
        if(index==4){
            tvtitle.setText("会议申请");
        }
        if(index==5){
            tvtitle.setText("离职申请");
        }
        if(index==6){
            tvtitle.setText("转正申请");
        }
        if(index==7){
            tvtitle.setText("调岗申请");
        }
        if(index == 8){
            tvtitle.setText("招聘申请");
        }
        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                applicationDao = EntityManager.getInstance().getApplicationDao();
                applicationDao.deleteAll();
                Intent intent = new Intent(MyApplicationActivity.this, MainApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData() {
        applicationDao = EntityManager.getInstance().getApplicationDao();

        List<Application> ap = null;
        Bundle waitbundle = new Bundle();
        waitbundle.putString("application","");
        Bundle passbundle = new Bundle();
        passbundle.putString("application","");
        Bundle refusebundle = new Bundle();
        refusebundle.putString("application","");
                    //审核中
                    QueryBuilder qb = applicationDao.queryBuilder();
                    qb.whereOr(ApplicationDao.Properties.Status.eq(-2),
                            ApplicationDao.Properties.Status.eq(0));
                    ap =  qb.list();
                    ArrayList ap1 =(ArrayList<Application>) ap;
                    Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                    if(ap!=null){
                        waitbundle.putParcelableArrayList("application",ap1);
                    }
                    Log.e(TAG,"waitbundle = "+waitbundle.toString());
                    contentAdapter.getItem(0).setArguments(waitbundle);

                    //同意
                    ap = applicationDao.queryBuilder().where(ApplicationDao.Properties.Status.eq(1)).list();
                    ap1 =(ArrayList<Application>) ap;
                    Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                    if(ap!=null){
                        passbundle.putParcelableArrayList("application",ap1);
                    }
                    contentAdapter.getItem(1).setArguments(passbundle);

                    //不同意
                    ap = applicationDao.queryBuilder().where(ApplicationDao.Properties.Status.eq(-1)).list();
                    ap1 =(ArrayList<Application>) ap;
                    Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                    if(ap!=null){
                        refusebundle.putParcelableArrayList("application",ap1);
                    }
                    contentAdapter.getItem(2).setArguments(refusebundle);

    }


    //tab的字体和线条颜色
    private void initTab(){
        mIndicatorTl.setTabMode(TabLayout.MODE_FIXED);   //设置不可横向水平滑动，MODE_SCROLLABLE支持可横向滑动
        mIndicatorTl.setTabTextColors(ContextCompat.getColor(this, R.color.tv_gray_deep), ContextCompat.getColor(this, R.color.tab_tv_selected)); //字体颜色
        mIndicatorTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tab_tv_selected));   //指示器颜色
        //ViewCompat.setElevation(mIndicatorTl, 10);  //设置阴影
//        mIndicatorTl.setupWithViewPager(mContentVp);
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
