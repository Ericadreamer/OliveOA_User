package com.oliveoa.view.myapplication;

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

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BusinessInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tname,tstatus,tplace,ttime,treason;   //审批人名称，审批状态，出差地点，出差时间，出差原因
    private LinearLayout addBtalistView;  //添加审批进度列表

    private BusinessTripApplication bta;
    private ArrayList<BusinessTripApplicationApprovedOpinionList> btaaol;
    private ContactInfoDao cidao;
    private ContactInfo ci;

    private String TAG = this.getClass().getSimpleName();
    private ApplicationDao applicationDao;
    private Application application;
private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

        bta = getIntent().getParcelableExtra("bta");
        btaaol = getIntent().getParcelableArrayListExtra("btaaol");
        Log.i(TAG,"bta="+bta+"---btaaol="+btaaol);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tplace = (TextView) findViewById(R.id.business_place);
        tstatus = (TextView) findViewById(R.id.status);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        addBtalistView = (LinearLayout)findViewById(R.id.approve_list);
        loadingDialog = new LoadingDialog(BusinessInfoActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
              loadingDialog.show();back();
            }
        });

        initData();
        addViewItem(null);
    }
    private void back() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                //出差
                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
                applicationDao = EntityManager.getInstance().getApplicationDao();
                //applicationDao.deleteAll();
                List<Application> ap = new ArrayList<>();
                application = new Application();
                int i, j = 0;
                BusinessTripApplicationJsonBean businessTripApplicationJsonBean = businessTripApplicationService.getbtapplicationsubmited(s);
                if (businessTripApplicationJsonBean.getStatus() == 0) {
                    applicationDao.deleteAll();
                    for ( i = 0; i < businessTripApplicationJsonBean.getData().size(); i++) {
                        BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s, businessTripApplicationJsonBean.getData().get(i).getBtaid());
                        if (businessTripApplicationHttpResponseObject.getStatus() == 0) {
                            BusinessTripApplicationInfoJsonBean btaaol = businessTripApplicationHttpResponseObject.getData();
                            Log.i(TAG, "btaaol:" + btaaol);
                            if (btaaol.getBusinessTripApplicationApprovedOpinionLists() != null) {
                                application.setAid(businessTripApplicationJsonBean.getData().get(i).getBtaid());
                                application.setDescribe(businessTripApplicationJsonBean.getData().get(i).getTask());
                                application.setType(3);
                                for (j = 0; j < btaaol.getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                    int flag = btaaol.getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved();
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
                        }
                        applicationDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(BusinessInfoActivity.this, MyApplicationActivity.class).putExtra("index",3));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), businessTripApplicationJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }
    private void initData(){
        cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(bta.getBegintime())+"--"+dateFormat.LongtoDatemm(bta.getEndtime()));
        treason.setText(bta.getTask());
        tplace.setText(bta.getPlace());
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addBtalistView.getChildCount(); i++) {
            final View childAt =  addBtalistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.person_approving);
                    Log.e(TAG,"tname="+tname.getText().toString());
                    String epname =tname.getText().toString().trim();
                    Application application = new Application();
                    application.setDescribe(btaaol.get(finalI).getOpinion());
                    application.setType(3);
                    application.setAid(btaaol.get(finalI).getBtaid());
                    application.setStatus(btaaol.get(finalI).getIsapproved());
                    Intent intent = new Intent(BusinessInfoActivity.this, ApprovedInfoActivity.class);
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

        if (btaaol==null) {
            Toast.makeText(BusinessInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <btaaol.size(); i ++){
                View EvaluateView = View.inflate(BusinessInfoActivity.this, R.layout.approveapplication_listitem, null);
                 addBtalistView.addView(EvaluateView);
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
        for (i = 0; i <  addBtalistView.getChildCount(); i++) {
            View childAt =  addBtalistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);
            ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(btaaol.get(i).getEid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }
            int flag = btaaol.get(i).getIsapproved();
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
