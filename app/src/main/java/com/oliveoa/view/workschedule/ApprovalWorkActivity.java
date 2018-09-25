package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

public class ApprovalWorkActivity extends AppCompatActivity {

    private TextView tname,ttime,tworkContent,tvisapproval;
    private ImageView back,save,inext;
    private WorkDetail workDetail;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private View textAreaView;
    private EditText treson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_work);
        workDetail = getIntent().getParcelableExtra("work");
        Log.e(getPackageName(),workDetail.toString());
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tname = (TextView) findViewById(R.id.name);
        ttime = (TextView) findViewById(R.id.work_time);
        tvisapproval =(TextView) findViewById(R.id.isapproval);
        inext = (ImageView)findViewById(R.id.inext);
        tworkContent = (TextView) findViewById(R.id.work_content);

        textAreaView = (LinesEditView)findViewById(R.id.reason);
        treson = textAreaView.findViewById(R.id.id_et_input);
        initData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog  = new LoadingDialog(ApprovalWorkActivity.this,"正在加载数据",true);
                loadingDialog.show();
                back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
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
                               Intent intent = new Intent(ApprovalWorkActivity.this, LeadershipApprovalActivity.class);
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

    private void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(workDetail.getSeid())).unique();
        if(contactInfo!=null){
            tname.setText(contactInfo.getName());
        }
        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatedd(workDetail.getBegintime())+"--"+dateFormat.LongtoDatedd(workDetail.getEndtime()));
        switch (workDetail.getIsapproved()){
            case -1:tvisapproval.setText("不同意");break;
            case 1:tvisapproval.setText("同意");break;
            case 0:tvisapproval.setText("正在审核");break;
            case -2:tvisapproval.setText("未审核");break;
            default:break;
        }
        tworkContent.setText(workDetail.getContent());
    }
    private void save() {
        if(TextUtils.isEmpty(treson.getText().toString().trim())||TextUtils.isEmpty(tvisapproval.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            if(tvisapproval.getText().toString().trim().equals("正在审核")||tvisapproval.getText().toString().trim().equals("未审核")){
                Toast.makeText(getApplicationContext(), "请选择是否同意此工作！", Toast.LENGTH_SHORT).show();
            }else{
                 switch (tvisapproval.getText().toString().trim()){
                     case "同意":workDetail.setIsapproved(1);break;
                     case "不同意":workDetail.setIsapproved(-1);break;
                     default:break;
                 }
                 workDetail.setOpinion(treson.getText().toString().trim());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        WorkDetailService service = new WorkDetailService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.approvedwork(s,workDetail);
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "审批成功！点击返回键返回待审批工作列表", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();

            }
        }

    }

    //单项选择器，请假类型选择
    public void onOptionPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "同意","不同意"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("审批意见选择");
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(18);
        picker.setTitleTextSize(16);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tvisapproval.setText(item);
                //showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
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
