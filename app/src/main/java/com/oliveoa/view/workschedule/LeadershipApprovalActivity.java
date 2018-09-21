package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.approval.LeaveUndisposedActivity;
import com.oliveoa.view.myapplication.LeaveActivity;
import com.oliveoa.view.myapplication.SelectPersonApprovingActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LeadershipApprovalActivity extends AppCompatActivity {

    private ImageView back;
    private LinearLayout addmyWorkitem;
    private List<WorkDetail> workDetails;
    private TextView tvtip;
    private TextView tvcontent,tvtime;
    private ContactInfoDao contactInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership_approval);
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        addmyWorkitem = (LinearLayout) findViewById(R.id.my_work_list);
        tvtip = (TextView)findViewById(R.id.tvtip);

        initData();
        //默认添加一个Item
        addViewItem(null);
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog  = new LoadingDialog(LeadershipApprovalActivity.this,"正在加载数据",true);
                loadingDialog.show();
                back();
            }
        });

        addmyWorkitem.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                approve();
            }
        });

    }

    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref =getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Log.d(TAG,"cookie=="+s);
                WorkDetailService service = new WorkDetailService();
                StatusAndMsgAndDataHttpResponseObject<ArrayList<WorkDetail>> statusAndMsgAndDataHttpResponseObject = service.getsubmitedwork(s, 0);
                WorkdetailAndStatus workdetailAndStatus = new WorkdetailAndStatus();
                WorkdetailAndStatusDao workdetailAndStatusDao = EntityManager.getInstance().getWorkdetailAndStatusDao();
                workdetailAndStatusDao.deleteAll();
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    List<WorkDetail> workDetails = statusAndMsgAndDataHttpResponseObject.getData();
                    //Log.e(TAG, workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for (int i = 0; i < workDetails.size(); i++) {
                        workdetailAndStatus.setWaid(workDetails.get(i).getSwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(0);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    // startActivity(new Intent(getActivity(), MyWorkActivity.class));

                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取我的拟定工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                StatusAndMsgAndDataHttpResponseObject<ArrayList<IssueWork>> isswork = service.getIssueworktome(s,0);
                if (isswork.getStatus()==0){
                    List<IssueWork> workDetails = isswork.getData();
                    //Log.e(TAG,workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for(int i =0;i<workDetails.size();i++){
                        workdetailAndStatus.setWaid(workDetails.get(i).getIwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(1);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    startActivity(new Intent(LeadershipApprovalActivity.this, MyWorkActivity.class));
                    finish();
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取分配与我工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }


    private void initData() {
        WorkDetailDao workDetailDao = EntityManager.getInstance().getWorkDetailDao();
        workDetails = workDetailDao.queryBuilder().list();
    }

    private void approve() {
        LoadingDialog loadingDialog  = new LoadingDialog(LeadershipApprovalActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                contactInfoDao = EntityManager.getInstance().getContactInfo();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if(contactInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }
                        }
                        Intent intent = new Intent(LeadershipApprovalActivity.this, SelectPersonApprovingActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i <addmyWorkitem.getChildCount(); i++) {
            final View childAt =addmyWorkitem.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.my_work_item);
            item.setTag("info");

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvcontent = (TextView) childAt.findViewById(R.id.work_content);
                    //Toast.makeText(LeadershipApprovalActivity.this, "你点击了"+tvcontent.getText().toString()+"----------"+workDetails.get(finalI).getContent(), Toast.LENGTH_SHORT).show();

                    //跳转审批页面
                    if(workDetails!=null){
                        Intent intent = new Intent(LeadershipApprovalActivity.this, ApprovalWorkActivity.class);
                        intent.putExtra("work",workDetails.get(finalI));
                        startActivity(intent);
                        finish();
                    }

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (workDetails.size()==0) {//如果申请列表为0，加载空布局
            //Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
            tvtip.setVisibility(View.VISIBLE);
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <workDetails.size(); i ++){
                View applicationview = View.inflate(LeadershipApprovalActivity.this, R.layout.leadership_approval_item, null);
                addmyWorkitem.addView(applicationview);
                InitDataViewItem();
            }
            sortViewItem();

        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addmyWorkitem.getChildCount(); i++) {

            View childAt = addmyWorkitem.getChildAt(i);
            tvtime = (TextView) childAt.findViewById(R.id.work_time);
            tvcontent = (TextView) childAt.findViewById(R.id.work_content);

            tvcontent.setText(workDetails.get(i).getContent());
            DateFormat dateFormat = new DateFormat();
            if(workDetails.get(i).getUpdatetime()!=0) {
                tvtime.setText(dateFormat.LongtoDatemm(workDetails.get(i).getUpdatetime()));
            }
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
