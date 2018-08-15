package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

public class RecruitmentActivity extends AppCompatActivity {
    private ImageView back,save;
    private TextView tDepartment,tDuty,taddPerson; //申请部门，招聘岗位，添加审批人
    private EditText eQuantity,ePosition,eDemand,eSalary;  //招聘人数，岗位描述，招聘要求，薪资标准
    private LinearLayout addPersonList;  //审批人列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruitment);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tDepartment = (TextView) findViewById(R.id.department);
        tDuty = (TextView) findViewById(R.id.position);
        eQuantity = (EditText) findViewById(R.id.quantity);
        ePosition = (EditText) findViewById(R.id.position_content);
        eDemand = (EditText) findViewById(R.id.demand_content);
        eSalary = (EditText) findViewById(R.id.salary);
        taddPerson = (TextView) findViewById(R.id.person_add);
        addPersonList = (LinearLayout) findViewById(R.id.approve_list);


        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecruitmentActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecruitmentActivity.this, SelectPersonApprovingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 默认添加一个Item
        addViewItem(null);


    }

    private void initData() {

    }
    private void save() {

    }

    //添加审批人操作
    public void personadd(){

    }

    //加载R.layout.item_approve列表
    private void addViewItem(View view) {

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem(){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

    }

    //选择部门
    public void onLinkagePicker1(View view) {

    }

    //选择职务
    public void onLinkagePicker2(View view) {

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
