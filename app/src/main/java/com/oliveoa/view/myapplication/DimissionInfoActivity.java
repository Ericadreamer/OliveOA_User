package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.approval.MyApprovalActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DimissionInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tnumber,tdate,tContentDpcid,treason,tmatter;  //离职员工编号，离职日期，所属部门职务，离职原因，交代事项
    private TextView tname,tstatus;  //审批进度item，审批人和审批状态
    private LinearLayout addlistView;  //添加审批进度列表

    private ContactInfoDao cidao;
    private ContactInfo ci;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo dp;
    private DutyInfoDao dutyInfoDao;
    private DutyInfo dt;
    private LeaveOfficeApplication ap;
    private ArrayList<LeaveOfficeApplicationApprovedOpinion> list;
    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission_info);

        ap = getIntent().getParcelableExtra("ap");
        list = getIntent().getParcelableArrayListExtra("list");
        index = getIntent().getIntExtra("index",index);
        Log.e(TAG,"index="+index);
        Log.i(TAG,"ap="+ap+"---list="+list);

        initView();
    }

    public void initView(){
        tnumber = (TextView) findViewById(R.id.employee_num);
        tdate = (TextView) findViewById(R.id.dimission_date);
        tContentDpcid = (TextView) findViewById(R.id.content_dpcid);
        treason = (TextView) findViewById(R.id.reason);
        tmatter = (TextView) findViewById(R.id.matter);
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);
        addlistView = (LinearLayout)findViewById(R.id.approve_list);
        loadingDialog = new LoadingDialog(DimissionInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                if(index==0){
                    back();
                }else{
                    backAppoval();
                }
            }
        });
        initData();
        addViewItem(null);
    }

    private void backAppoval() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                //审批
                ApprovalDao approvalDao = EntityManager.getInstance().getApprovalDao();
                Approval approval = new Approval();
                int i,j= 0;
                StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> infoJsonBean = service.getApplicationIunapproved(s); //获取待我审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    approvalDao.deleteAll();
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        approval.setAid( infoJsonBean.getData().get(i).getLoaid());
                        approval.setSeid(infoJsonBean.getData().get(i).getEid());
                        approval.setContent(infoJsonBean.getData().get(i).getReason());
                        approval.setStatus(0);
                        approval.setType(5);
                        approval.setIsapprove(-2);
                        approvalDao.insert(approval);
                    }
                    //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取待审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                infoJsonBean = service.getApplicationIapproved(s); //获取我已经审核的申请
                Log.e(TAG, infoJsonBean.toString());
                if (infoJsonBean.getStatus() == 0) {
                    for (i = 0; i < infoJsonBean.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> httpResponseObject = service.getApplicationInfo(s,infoJsonBean.getData().get(i).getLoaid());
                        if(httpResponseObject.getStatus()==0){
                            approval.setAid( infoJsonBean.getData().get(i).getLoaid());
                            approval.setSeid(infoJsonBean.getData().get(i).getEid());
                            approval.setContent(infoJsonBean.getData().get(i).getReason());
                            approval.setStatus(1);
                            approval.setType(5);

                            for (j = 0; j < httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().size(); j++) {
                                switch (httpResponseObject.getData().getLeaveOfficeApplicationApprovedOpinionList().get(j).getIsapproved()) {
                                    case -2:
                                        approval.setIsapprove(-2);
                                        break;
                                    case -1:
                                        approval.setIsapprove(-1);
                                        break;
                                    case 0:
                                        approval.setIsapprove(0);
                                        break;
                                    case 1:
                                        approval.setIsapprove(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            approvalDao.insert(approval);
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                    startActivity(new Intent(DimissionInfoActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();

                applicationDao = EntityManager.getInstance().getApplicationDao();

                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<LeaveOfficeApplication>> statusAndDataHttpResponseObject = service.getApplicationIsubmited(s);
                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for (i = 0;i<statusAndDataHttpResponseObject.getData().size();i++){
                        StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> infojsonbean
                                = service.getApplicationInfo(s,statusAndDataHttpResponseObject.getData().get(i).getLoaid());
                        application.setAid(statusAndDataHttpResponseObject.getData().get(i).getLoaid());
                        application.setDescribe(statusAndDataHttpResponseObject.getData().get(i).getReason());
                        application.setType(5);
                        if(infojsonbean.getData().getLeaveOfficeApplicationApprovedOpinionList()!=null) {
                            for(j=0;j<infojsonbean.getData().getLeaveOfficeApplicationApprovedOpinionList().size();j++)
                            switch (infojsonbean.getData().getLeaveOfficeApplicationApprovedOpinionList().get(j).getIsapproved()) {
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
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(DimissionInfoActivity.this, MyApplicationActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "获取申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
    private void initData(){
        cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();

        tdate.setText(dateFormat.LongtoDatedd(ap.getLeavetime()));
        treason.setText(ap.getReason());
        tmatter.setText(ap.getHandoverMatters());
        ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(ap.getEid())).unique();
        if(ci!=null){
            Log.i(TAG,ci.toString());
            tnumber.setText(ci.getId());
            dp = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(ci.getDcid())).unique();
            if(dp!=null){
                dt = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(ci.getPcid())).unique();
                if(dt!=null){
                    tContentDpcid.setText(dp.getName()+dt.getName());
                }
            }
        }else{
            tContentDpcid.setText("");
        }
        LinearLayout seid = (LinearLayout)findViewById(R.id.seid);
        if(index==0){
            seid.setVisibility(View.GONE);
        }else{
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(ap.getEid())).unique();
            TextView seidname = (TextView)findViewById(R.id.seidname);
            if(ci!=null){
                seidname.setText(ci.getName());
            }
        }

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.person_approving);
                    Log.e(TAG,"tname="+tname.getText().toString());
                    String epname =tname.getText().toString().trim();
                    Application application = new Application();
                    application.setDescribe(list.get(finalI).getOpinion());
                    if(index==1){
                        application.setType(15);
                    }else{
                        application.setType(5);
                    }
                    application.setAid(ap.getLoaid());
                    application.setStatus(list.get(finalI).getIsapproved());
                    Intent intent = new Intent(DimissionInfoActivity.this, ApprovedInfoActivity.class);
                    intent.putExtra("epname",epname);
                    intent.putExtra("ap",application);
                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {

        if (list==null) {
            Toast.makeText(DimissionInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <list.size(); i ++) {
                View EvaluateView = View.inflate(DimissionInfoActivity.this, R.layout.approveapplication_listitem, null);
                addlistView.addView(EvaluateView);
                InitLADataViewItem();
            }
            sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitLADataViewItem() {
        int i;
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView) childAt.findViewById(R.id.person_approving);
            tstatus = (TextView) childAt.findViewById(R.id.status);
            if (list != null) {
                ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(list.get(i).getEid())).unique();
                if (ci != null) {
                    tname.setText(ci.getName());
                    Log.e("eid:", ci.getName());
                } else {
                    tname.setText("");
                }
                int flag = list.get(i).getIsapproved();
                switch (flag) {
                    case -2:
                        tstatus.setText("待审核");
                        tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                        break;
                    case -1:
                        tstatus.setText("不同意");
                        tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                        break;
                    case 1:
                        tstatus.setText("同意");
                        tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                        break;
                    case 0:
                        tstatus.setText("正在审核");
                        tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                        break;
                    default:
                        break;
                }
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
