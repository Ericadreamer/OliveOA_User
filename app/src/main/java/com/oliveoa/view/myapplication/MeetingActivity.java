package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.graphics.Color;
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

import cn.qqtheme.framework.picker.DateTimePicker;

public class MeetingActivity extends AppCompatActivity {
    private ImageView back,save;
    private EditText eTopic,eplace; //会议主题，会议地点
    private TextView tstartTime,tendTime,taddMember,taddPerson;
    private LinearLayout addPersonList,addMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        eTopic = (EditText) findViewById(R.id.meeting_topic);
        eplace = (EditText) findViewById(R.id.meeting_place);
        taddPerson = (TextView) findViewById(R.id.person_add);
        addPersonList = (LinearLayout) findViewById(R.id.approve_list);
        taddMember = (TextView) findViewById(R.id.members_add);
        addMemberList = (LinearLayout) findViewById(R.id.members_list);
        tstartTime = (TextView) findViewById(R.id.start);
        tendTime = (TextView) findViewById(R.id.end);

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingActivity.this, SelectPersonApprovingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // 默认添加一个Item
        addViewItem(null);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingActivity.this, MyApplicationActivity.class);
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

    }

    private void initData() {

    }
    private void save() {

    }

    //添加审批人操作
    public void personadd(){

    }

    //加载R.layout.item_approve,R.layout.item_member列表
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

    //年月日时分选择器，开始时间选择
    public void onYearMonthDayTimePicker1(View view) {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 8, 1);
        picker.setDateRangeEnd(2025, 12, 31);
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
        // picker.setTopLineColor(0x99FF0000);
        picker.setDividerColor(Color.rgb(0, 178, 238));//设置分割线的颜色
        picker.setLabelTextColor(Color.GRAY);
        picker.setTopLineColor(Color.GRAY);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("年月日时分选择");
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tstartTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    //年月日时分选择器，结束时间选择
    public void onYearMonthDayTimePicker2(View view) {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(2018, 8, 1);
        picker.setDateRangeEnd(2025, 12, 31);
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
        // picker.setTopLineColor(0x99FF0000);
        picker.setDividerColor(Color.rgb(0, 178, 238));//设置分割线的颜色
        picker.setLabelTextColor(Color.GRAY);
        picker.setTopLineColor(Color.GRAY);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("年月日时分选择");
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tendTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    //重写showToast
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
