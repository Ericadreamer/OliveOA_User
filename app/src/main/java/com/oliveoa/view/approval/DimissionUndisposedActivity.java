package com.oliveoa.view.approval;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.greendao.ApprovalDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.DimissionInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DimissionUndisposedActivity extends AppCompatActivity {
    private ImageView back;
    //离职员工编号，离职日期，所属部门职务，离职原因，交代事项，申请人
    private TextView tnumber,tdate,tContentDpcid,treason,tmatter,tApplicant;
    private Button bagree,bdisagree;
    private LeaveOfficeApplication ap;
    private ContactInfoDao contactInfoDao;
    private ContactInfo ci;
    private String TAG = this.getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission_undisposed);
        ap = getIntent().getParcelableExtra("ap");
        index = getIntent().getIntExtra("index",index);//0为列表，1为审批意见返回
        Log.i(TAG,"ap="+ap);
        Log.i(TAG,"index="+index);
        initView();
    }

    public void initView(){
        tnumber = (TextView) findViewById(R.id.employee_num);
        tdate = (TextView) findViewById(R.id.dimission_date);
        tContentDpcid = (TextView) findViewById(R.id.content_dpcid);
        treason = (TextView) findViewById(R.id.reason);
        tmatter = (TextView) findViewById(R.id.matter);
        back = (ImageView) findViewById(R.id.iback);
        tApplicant = (TextView) findViewById(R.id.name);
        bagree = (Button) findViewById(R.id.agree);
        bdisagree = (Button) findViewById(R.id.disagree);

        loadingDialog = new LoadingDialog(DimissionUndisposedActivity.this,"正在加载数据",true);
        initData();

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                back();
            }
        });

        bagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DimissionUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",51);//1同意
                intent.putExtra("aid",ap.getLoaid());
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        bdisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DimissionUndisposedActivity.this, ApprovalAdviseActivity.class);
                intent.putExtra("index",50);
                intent.putExtra("aid",ap.getLoaid());

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
                    startActivity(new Intent(DimissionUndisposedActivity.this, MyApprovalActivity.class).putExtra("index",5));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),"获取已审批离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    public void initData(){
        ContactInfoDao cidao = EntityManager.getInstance().getContactInfo();
        DateFormat dateFormat = new DateFormat();
        DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        DutyInfoDao dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();

        tdate.setText(dateFormat.LongtoDatedd(ap.getLeavetime()));
        treason.setText(ap.getReason());
        tmatter.setText(ap.getHandoverMatters());
        ci = cidao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(ap.getEid())).unique();
        if(ci!=null){
            tApplicant.setText(ci.getName());
            tnumber.setText(ci.getId());
            DepartmentInfo dp = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(ci.getDcid())).unique();
            if(dp!=null){
                DutyInfo dt = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(ci.getPcid())).unique();
                if(dt!=null){
                    tContentDpcid.setText(dp.getName()+dt.getName());
                }else{
                    tContentDpcid.setText("");
                }
            }else{
                tContentDpcid.setText("");
            }
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
