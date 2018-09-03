package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ApprovedInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tname,tstatus,topinion;
    private String oaaolcid;

    private OvertimeApplicationApprovedOpinionList oaaol;
    private OvertimeApplicationApprovedOpinionListDao oaaoldao;
    private ContactInfoDao cidao;
    private List<ContactInfo> ci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_info);

        oaaolcid = getIntent().getStringExtra("oaaolcid");
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.tname);
        tstatus = (TextView) findViewById(R.id.status);
        topinion = (TextView)findViewById(R.id.opinion);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApprovedInfoActivity.this, WaitActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        initData();

    }
    private void initData(){

        oaaoldao = EntityManager.getInstance().getOvertimeApplicationApprovedOpinionListInfo();
        oaaol = oaaoldao.queryBuilder()
                .where(OvertimeApplicationApprovedOpinionListDao.Properties.Oaaocid.eq(oaaolcid) )
                .unique();

        cidao = EntityManager.getInstance().getContactInfo();
        ci = cidao.queryBuilder()
                .orderDesc(ContactInfoDao.Properties.Orderby)
                .list();

        if(oaaol.getOpinion()==null){
            topinion.setText("无");
        }else{
            topinion.setText(oaaol.getOpinion());
        }

        for (int i=0;i<ci.size();i++){
            if(oaaol.getEid().equals(ci.get(i).getEid())) {
                tname.setText(ci.get(i).getName());
                //Log.e("eid:",ci.get(i).getName());
                break;
            }
        }

        int flag = oaaol.getIsapproved();
        switch (flag) {
            case -2:
                tstatus.setText("待审核");
                tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tstatus.setText("不同意");
                tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                break;
            case 0:
                tstatus.setText("同意");
                tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                break;
            case 1:
                tstatus.setText("正在审核");
                tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;
        }

        Log.i("oaaol:",oaaol.toString());
        Log.i("ci:",ci.toString());

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
