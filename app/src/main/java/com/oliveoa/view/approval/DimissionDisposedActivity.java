package com.oliveoa.view.approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.DimissionInfoActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;

import java.util.Timer;
import java.util.TimerTask;

public class DimissionDisposedActivity extends AppCompatActivity {
    private ImageView back;
    //离职员工编号，离职日期，所属部门职务，离职原因，交代事项，申请人
    private TextView tnumber,tdate,tContentDpcid,treason,tmatter,tApplicant,tadvise;
    private TextView tname,tstatus;  //审批进度item，审批人和审批状态
    private LinearLayout addListView;  //添加审批进度列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimission_disposed);

        initView();
        initData();
    }

    public void initView(){
        tnumber = (TextView) findViewById(R.id.employee_num);
        tdate = (TextView) findViewById(R.id.dimission_date);
        tContentDpcid = (TextView) findViewById(R.id.content_dpcid);
        treason = (TextView) findViewById(R.id.reason);
        tmatter = (TextView) findViewById(R.id.matter);
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.approver);
        tadvise =(TextView) findViewById(R.id.advise);
        tstatus = (TextView) findViewById(R.id.status);
        tApplicant = (TextView) findViewById(R.id.name);
        addListView = (LinearLayout) findViewById(R.id.approve_list);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DimissionDisposedActivity.this, MyApprovalActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);
                finish();
            }
        });
    }

    public void initData(){

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
    }

    /**
     * 添加ViewItem，R.layout.approval_status_item
     * @param view
     */
    private void addViewItem(View view){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

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
