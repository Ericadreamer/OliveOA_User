package com.oliveoa.view.approval;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.Timer;
import java.util.TimerTask;

public class BusinessUndisposedActivity extends AppCompatActivity {

    private ImageView back;
    //申请人，出差时间，出差地点，出差原因
    private TextView tApplicant,tvtime,tvplace,tvreason;
    private Button bagree,bdisagree;
    private BusinessTripApplication bta;
    private ContactInfoDao contactInfoDao;
    private ContactInfo ci;
    private String TAG = this.getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_undisposed);
        bta = getIntent().getParcelableExtra("bta");
        index = getIntent().getIntExtra("index",index);//0为列表，1为审批意见返回
        Log.i(TAG,"bta="+bta);
        Log.i(TAG,"index="+index);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tvtime = (TextView) findViewById(R.id.business_time);
        tvreason = (TextView) findViewById(R.id.reason);
        tvplace = (TextView) findViewById(R.id.business_place);
        tApplicant = (TextView) findViewById(R.id.name);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);
        loadingDialog = new LoadingDialog(BusinessUndisposedActivity.this,"正在加载数据",true);
        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusinessUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",31);//1同意
                intent.putExtra("aid",bta.getBtaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusinessUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",30);//0不同意
                intent.putExtra("aid",bta.getBtaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void back() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    //Todo Service
                    BusinessTripApplicationService service = new BusinessTripApplicationService();
                    //Todo Service.Method
                     ApprovalDao apDao = EntityManager.getInstance().getApprovalDao();
                        Approval ap = new Approval();
                        int i, j = 0;
                        BusinessTripApplicationJsonBean infoJsonBean = service.getbtapplicationunapproved(s); //获取待我审核的申请
                        Log.e(TAG, infoJsonBean.toString());
                        if (infoJsonBean.getStatus() == 0) {
                            apDao.deleteAll();
                            for (i = 0; i < infoJsonBean.getData().size(); i++) {
                                ap.setAid( infoJsonBean.getData().get(i).getBtaid());
                                ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                ap.setContent(infoJsonBean.getData().get(i).getTask());
                                ap.setStatus(0);
                                ap.setType(3);
                                ap.setIsapprove(-2);
                                apDao.insert(ap);
                            }
                            //startActivity(new Intent(MainApprovalActivity.this, MyApprovalActivity.class).putExtra("index",2));
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        infoJsonBean = service.getbtapplicationapproved(s); //获取我已经审核的申请
                        Log.e(TAG, infoJsonBean.toString());
                        if (infoJsonBean.getStatus() == 0) {
                            for (i = 0; i < infoJsonBean.getData().size(); i++) {
                                BusinessTripApplicationHttpResponseObject httpResponseObject = service.getbtapplicationinfo(s,infoJsonBean.getData().get(i).getBtaid());
                                if(httpResponseObject.getStatus()==0) {
                                    ap.setAid(infoJsonBean.getData().get(i).getBtaid());
                                    ap.setSeid(infoJsonBean.getData().get(i).getEid());
                                    ap.setContent(infoJsonBean.getData().get(i).getTask());
                                    ap.setStatus(1);
                                    ap.setType(3);
                                    for (j = 0; j < httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                        switch (httpResponseObject.getData().getBusinessTripApplicationApprovedOpinionLists().get(j).getIsapproved()) {
                                            case -2:
                                                ap.setIsapprove(-2);
                                                break;
                                            case -1:
                                                ap.setIsapprove(-1);
                                                break;
                                            case 0:
                                                ap.setIsapprove(0);
                                                break;
                                            case 1:
                                                ap.setIsapprove(1);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                    apDao.insert(ap);
                                }
                            }
                            Looper.prepare();//解决子线程弹toast问题
                            //Toast.makeText(getApplicationContext(),"保存成功，正在返回我的审批列表", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BusinessUndisposedActivity.this, MyApprovalActivity.class).putExtra("index",3));
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),infoJsonBean .getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                }
            }).start();

    }

    private void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        tvtime.setText(dateFormat.LongtoDatemm(bta.getBegintime())+"--"+dateFormat.LongtoDatemm(bta.getEndtime()));
        tvreason.setText(bta.getTask());
        tvplace.setText(bta.getPlace());
        ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(bta.getEid())).unique();

        if(ci!=null) {
            tApplicant.setText(ci.getName());
        }else{
            tApplicant.setText("");
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
