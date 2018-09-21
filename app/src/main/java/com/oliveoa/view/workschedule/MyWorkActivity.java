package com.oliveoa.view.workschedule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Looper;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.Adapter.GridViewAdapter;
import com.oliveoa.Adapter.WorkGridViewAdapter;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.IssueWorkDao;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.MeetingApplicationAndStatus;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.meetingmanagement.MyMeetingActivity;
import com.oliveoa.view.mine.MineActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
import com.oliveoa.view.myapplication.MainApplicationActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;
import com.oliveoa.view.notice.MySubmissionActivity;
import com.oliveoa.view.notice.NoticeActivity;
import com.oliveoa.view.notice.NoticeListActivity;
import com.oliveoa.widget.LoadingDialog;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyWorkActivity extends AppCompatActivity {

    private ImageView back;
    private RadioButton protocolWork, leadershipApproval, workAllocation;  //工作拟定，领导审批，工作审批
    private LinearLayout addMyWorkItem;
    private WorkDetailDao workDetailDao;
    private WorkDetail workDetail;
    private IssueWork issueWork;
    private IssueWorkDao issueWorkDao;
    private WorkdetailAndStatusDao workdetailAndStatusDao;
    //两个tab
    private TabLayout mTabLayout;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.to_me, R.string.mine_};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new ToMeActivity(), new MyProtocolActivity()};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private MyWorkPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private int index = 0;


    private String TAG = this.getClass().getSimpleName();

    //图文按钮
    WorkGridViewAdapter adapter;
    GridView gridView;
    private  LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        /*index = getIntent().getIntExtra("index",index);
        Log.d("INDEX=", String.valueOf(index));*/

        adapter = new WorkGridViewAdapter(this);

        //默认添加一个Item
        addViewItem(null);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
//        protocolWork = (RadioButton) findViewById(R.id.protocol_work);
//        leadershipApproval = (RadioButton) findViewById(R.id.leadership_approval);
//        workAllocation = (RadioButton) findViewById(R.id.allocation);
        addMyWorkItem = (LinearLayout) findViewById(R.id.my_work_list);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES);

        mAdapter = new MyWorkPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.info_viewpager);
        initData();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        loadingDialog = new LoadingDialog(MyWorkActivity.this,"正在加载数据",true);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });

        //九宫格跳转
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0://点击图片加班跳转
                    { loadingDialog.show();
                        GetProtocolWorkSubmited();
                    }
                    break;
                    case 1://点击图片请假跳转
                    { loadingDialog.show();
                        GetLeadershipApprovalSubmited();
                    }
                    break;
                    case 2://点击图片出差跳转
                    { loadingDialog.show();
                        GetWorkAlLocationSubmited();
                    }
                    break;
                    default:
                        break;
                }
            }

        });
    }

    private void GetProtocolWorkSubmited() {
        workDetailDao = EntityManager.getInstance().getWorkDetailDao();
        workDetail  = new WorkDetail();
        workDetailDao.insert(workDetail);
        Intent intent = new Intent(MyWorkActivity.this, ProtocolWorkActivity.class);
        intent.putExtra("index",0);
        startActivity(intent);
        finish();
    }

    private void GetLeadershipApprovalSubmited() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                WorkDetailService service = new WorkDetailService();
                //Todo Service.Method
                StatusAndMsgAndDataHttpResponseObject<ArrayList<WorkDetail>> statusAndDataHttpResponseObject = service.getworkunapproved(s,0);
                //ToCheck JsonBean.getStatus()
                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    ArrayList<WorkDetail> workDetails = statusAndDataHttpResponseObject.getData();
                    WorkDetailDao workDetailDao = EntityManager.getInstance().getWorkDetailDao();
                    workDetailDao.deleteAll();
                    for (int i =0;i<workDetails.size();i++){
                        workDetailDao.insert(workDetails.get(i));
                    }
                    Intent intent = new Intent(MyWorkActivity.this, LeadershipApprovalActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),statusAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void GetWorkAlLocationSubmited() {
        issueWorkDao = EntityManager.getInstance().getIssueWorkDao();
        issueWork  = new IssueWork();
        issueWorkDao.insert(issueWork);
        Intent intent = new Intent(MyWorkActivity.this, WorkAllocationActivity.class);
        intent.putExtra("index",0);
        startActivity(intent);
        finish();
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
    private class MyWorkPagerAdapter extends FragmentPagerAdapter {
        public MyWorkPagerAdapter(FragmentManager fm) {
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

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.my_work_item
     * @param view
     */
    private void addViewItem(View view){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

    }

    private void initData(){
       workdetailAndStatusDao = EntityManager.getInstance().getWorkdetailAndStatusDao();

       List<WorkdetailAndStatus> ap = null;
        Bundle submitbymebundle = new Bundle(); //我拟定的
        submitbymebundle .putString("work","");
        Bundle submittomebundle = new Bundle(); //分配予我
        submittomebundle.putString("work","");

        //我拟定的
        QueryBuilder qb = workdetailAndStatusDao.queryBuilder();
        qb.where(WorkdetailAndStatusDao.Properties.Status.eq(0));
        ap =  qb.list();
        ArrayList ap1 =(ArrayList<WorkdetailAndStatus>) ap;
        Log.e(TAG,"ArrayList<WorkdetailAndStatus> ap = "+ap1.toString());
        if(ap!=null){
            submitbymebundle .putParcelableArrayList("work",ap1);

        }
        Log.e(TAG,"submitbymebundle  = "+submitbymebundle .toString());
        mAdapter.getItem(1).setArguments(submitbymebundle );

        //分配与我
        ap = workdetailAndStatusDao.queryBuilder().where(WorkdetailAndStatusDao.Properties.Status.eq(1)).list();
        ap1 =(ArrayList<WorkdetailAndStatus>) ap;
        Log.e(TAG,"ArrayList<WorkdetailAndStatus> ap = "+ap1.toString());
        if(ap!=null){
            submittomebundle.putParcelableArrayList("work",ap1);
        }
        mAdapter.getItem(0).setArguments(submittomebundle);


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
