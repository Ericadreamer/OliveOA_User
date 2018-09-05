package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.fragment.TabListFragment;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.AdhibitionActivity;
import com.oliveoa.view.MainActivity;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.addressbook.AddressBookActivity;
import com.oliveoa.view.mine.MineActivity;
import com.oliveoa.view.notice.NoticeActivity;

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
    private BusinessTripApplicationDao btaDao;
    private BusinessTripApplicationApprovedOpinionListDao btaaolDao;
    private LeaveApplicationDao laDao;
    private LeaveApplicationApprovedOpinionListDao laaolDao;
    private OvertimeApplicationDao oaDao;
    private OvertimeApplicationApprovedOpinionListDao oaaolDao;
    private ApplicationDao applicationDao;
    private Application application ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application);

        index = getIntent().getIntExtra("index",index);//加班1、请假2、出差3、会议4、离职5、转正6、调岗7、招聘8、物品9

        mIndicatorTl = (TabLayout) findViewById(R.id.tl_indicator);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
        mContentVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mIndicatorTl));
        mIndicatorTl.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mContentVp));//点击显示子页面

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
                startActivity(intent);
                finish();
            }
        });

        initData();
    }

    private void initData() {
           new Thread(new Runnable() {
                       @Override
                       public void run() {
                           GetMyApplicationSubmited(1);
                       }
                   }).start();
    }

    private void GetMyApplicationSubmited(int index) {
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        String s = pref.getString("sessionid","");

        //Todo Service
        LeaveApplicationService leaveApplicationService = new LeaveApplicationService();
        OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
        BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
        if(index==1){//加班
            Log.e(TAG,"SESSIONID="+s+","+index);
            applicationDao = EntityManager.getInstance().getApplicationDao();
            List<Application> ap = new ArrayList<>() ;
            application = new Application();
            int i,j,k=0;
            OvertimeApplicationInfoJsonBean overtimeApplicationInfoJsonBean  = overtimeApplictionService.submitotapplication(s); //获取我提交的申请
            if(overtimeApplicationInfoJsonBean.getStatus()==0){
                    for (i = 0; i < overtimeApplicationInfoJsonBean.getData().size(); i++) {
                        OvertimeApplicationHttpResponseObject oahro = overtimeApplictionService.overtimeapplication(s, overtimeApplicationInfoJsonBean.getData().get(i).getOaid());
                        Log.e(TAG, "oahro" + oahro.toString());
                        String oaid = overtimeApplicationInfoJsonBean.getData().get(i).getOaid();
                        if (oahro.getStatus() == 0) {
                            OvertimeApplicationJsonBean oaaol = oahro.getOvertimeApplicationJsonBean(); //获取oaid的审核列表结果及oaid申请详情
                            Log.d(TAG, "oaaol" + oaaol.toString());
                            if (oaaol.getOvertimeApplicationApprovedOpinionLists() != null) {
                                Log.d(TAG, "oaaol.getOvertimeApplicationApprovedOpinionLists():" + oaaol.getOvertimeApplicationApprovedOpinionLists().toString());
                                Log.d(TAG, "oaid="+oaid);
                                application.setAid(oaid);
                                application.setDescribe(overtimeApplicationInfoJsonBean.getData().get(i).getReason());
                                application.setType(1);
                                for (j = 0; j < oaaol.getOvertimeApplicationApprovedOpinionLists().size(); j++) { //循环审核列表
                                    int flag = oaaol.getOvertimeApplicationApprovedOpinionLists().get(j).getIsapproved();
                                    switch (flag) {
                                        case -2:
                                            application.setStatus(-2);
                                            break;
                                        case -1:
                                            application.setStatus(-1);
                                            break;
                                        case 0:
                                            application.setStatus(0);
                                            break;
                                        case 1:
                                            application.setStatus(1);
                                            break;
                                        default:
                                            break;
                                    }
                                }


                            }
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), oahro.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                     applicationDao.insert(application);
                    }
                     Bundle bundle = new Bundle();
                     bundle.putString("application","");

                     //审核中
                     QueryBuilder qb = applicationDao.queryBuilder();
                     qb.whereOr(ApplicationDao.Properties.Status.eq(-2),
                             ApplicationDao.Properties.Status.eq(0));
                     ap =  qb.list();
                             ArrayList ap1 =(ArrayList<Application>) ap;
                     Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                     if(ap!=null){
                         bundle.putParcelableArrayList("application",ap1);
                     }
                     contentAdapter.getItem(0).setArguments(bundle);

                     //同意
                     ap = applicationDao.queryBuilder().where(ApplicationDao.Properties.Status.eq(1)).list();
                    ap1 =(ArrayList<Application>) ap;
                    Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                    if(ap!=null){
                        bundle.putParcelableArrayList("application",ap1);
                    }
                     contentAdapter.getItem(1).setArguments(bundle);

                    //不同意
                    ap = applicationDao.queryBuilder().where(ApplicationDao.Properties.Status.eq(-1)).list();
                    ap1 =(ArrayList<Application>) ap;
                    Log.e(TAG,"ArrayList<Application> ap = "+ap1.toString());
                    if(ap!=null){
                        bundle.putParcelableArrayList("application",ap1);
                    }
                    contentAdapter.getItem(2).setArguments(bundle);


            }else{
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(),overtimeApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
        if(index==2){//请假
            LeaveApplicationJsonBean leaveApplicationInfoJsonBean = leaveApplicationService.getlapplicationsubmited(s);
            if (leaveApplicationInfoJsonBean.getStatus() == 0) {
                for (int i = 0;i<leaveApplicationInfoJsonBean.getData().size();i++){
                    LeaveApplicationHttpResponseObject leaveApplicationHttpResponseObject = leaveApplicationService.getlapplicationinfo(s,leaveApplicationInfoJsonBean.getData().get(i).getLaid());
                    if(leaveApplicationHttpResponseObject.getStatus()==0){
                        LeaveApplicationInfoJsonBean laaol = leaveApplicationHttpResponseObject.getData();
                        Log.i(TAG,"laaol:"+laaol);
                        if(laaol.getLeaveApplicationApprovedOpinionLists()!=null) {
                            for (int j = 0; j < laaol.getLeaveApplicationApprovedOpinionLists().size(); j++) {
                                laaolDao.insert(laaol.getLeaveApplicationApprovedOpinionLists().get(j));
                            }
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),leaveApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                    laDao.insert(leaveApplicationInfoJsonBean.getData().get(i));
                }
            }else{
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(),leaveApplicationInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
        if(index==3){//出差
            BusinessTripApplicationJsonBean businessTripApplicationJsonBean = businessTripApplicationService.getbtapplicationsubmited(s);
            if( businessTripApplicationJsonBean.getStatus()==0) {
                for(int i=0;i<businessTripApplicationJsonBean.getData().size();i++){
                        BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s,businessTripApplicationJsonBean.getData().get(i).getBtaid());
                        if(businessTripApplicationHttpResponseObject.getStatus() == 0){
                            BusinessTripApplicationInfoJsonBean btaaol = businessTripApplicationHttpResponseObject.getData();
                            Log.i(TAG,"btaaol:"+btaaol);
                            if(btaaol.getBusinessTripApplicationApprovedOpinionLists()!=null) {
                                for (int j = 0; j < btaaol.getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                    btaaolDao.insert(btaaol.getBusinessTripApplicationApprovedOpinionLists().get(j));
                                }
                            }
                        }
                        btaDao.insert(businessTripApplicationJsonBean.getData().get(i));
                }
            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(),businessTripApplicationJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
        if(index==4){//会议

        }
        if(index==5){//离职

        }
        if(index==6){//转正

        }
        if(index==7){//调岗

        }
        if(index==8){//招聘

        }
        if(index==9){//物品

        }
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
