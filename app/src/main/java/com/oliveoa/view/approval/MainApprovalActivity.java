package com.oliveoa.view.approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.oliveoa.Adapter.ApprovalGridViewAdapter;
import com.oliveoa.Adapter.GridViewAdapter;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.MainApplicationActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.Timer;
import java.util.TimerTask;

public class MainApprovalActivity extends AppCompatActivity {
    ApprovalGridViewAdapter adapter;
    GridView gridView;
    private ImageView back;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_approval);

        adapter = new ApprovalGridViewAdapter(this);

        initView();

    }

    public void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        back = (ImageView) findViewById(R.id.iback);
        loadingDialog = new LoadingDialog(MainApprovalActivity.this,"正在加载数据",true);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainApprovalActivity.this, TabLayoutBottomActivity.class);
                intent.getIntExtra("index", 0);
                startActivity(intent);
                finish();
            }
        });

        //九宫格跳转
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0://点击图片加班跳转
                    {
                        loadingDialog.show();
                        GetMyOvertimeApplicationSubmited();
                    }
                    break;
                    case 1://点击图片请假跳转
                    {
                        loadingDialog.show();
                        GetMyLeaveApplicationSubmited();
                    }
                    break;
                    case 2://点击图片出差跳转
                    {
                        loadingDialog.show();
                        GetMyBussinessApplicationSubmited();
                    }
                    break;
                    case 3://点击图片会议跳转
                    {
                        loadingDialog.show();
                        GetMyMeetingApplicationSubmited();
                    }
                    break;
                    case 4://点击图片离职跳转
                    {
                        loadingDialog.show();
                        GetMyDismissionApplicationSubmited();
                    }
                    break;
                    case 5://点击图片转正跳转
                    {
                        loadingDialog.show();
                        GetMyRegularApplicationSubmited();
                    }
                    break;
                    case 6://点击图片调岗跳转
                    {
                        loadingDialog.show();
                        GetMyAdjustApplicationSubmited();
                    }
                    break;
                    case 7://点击图片招聘跳转
                    {
                        loadingDialog.show();
                        GetMyRecruitmentApplicationSubmited();
                    }
                    case 8://点击图片招聘跳转
                    {
                        loadingDialog.show();
                        GetMyNoticeSubmited();
                    }
                    break;
                    default:
                        break;
                }
            }

        });
    }

    private void GetMyOvertimeApplicationSubmited() {

    }

    private void GetMyLeaveApplicationSubmited() {

    }

    private void GetMyBussinessApplicationSubmited() {

    }

    private void GetMyMeetingApplicationSubmited() {

    }

    private void GetMyDismissionApplicationSubmited() {

    }

    private void GetMyRegularApplicationSubmited() {

    }

    private void GetMyAdjustApplicationSubmited() {

    }

    private void GetMyRecruitmentApplicationSubmited() {

    }

    private void GetMyNoticeSubmited() {

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
