package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;

public class MyWorkActivity extends AppCompatActivity {

    private ImageView back;
    private RadioButton protocolWork,leadershipApproval,workAllocation;
    private LinearLayout myWork,leadershipAllocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        protocolWork = (RadioButton) findViewById(R.id.protocol_work);
        leadershipApproval = (RadioButton) findViewById(R.id.leadership_approval);
        workAllocation = (RadioButton) findViewById(R.id.allocation);
        myWork = (LinearLayout) findViewById(R.id.my_work);
        leadershipAllocation = (LinearLayout) findViewById(R.id.leadership_allocation);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        protocolWork.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, ProtocolWorkActivity.class);
                startActivity(intent);
                finish();
            }
        });
        leadershipApproval.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, LeadershipApprovalActivity.class);
                startActivity(intent);
                finish();
            }
        });
        workAllocation.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, WorkAllocationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Drawable drawableApplication = getResources().getDrawable(R.drawable.protocol_work_pic);
        drawableApplication.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        protocolWork.setCompoundDrawables(null, drawableApplication, null, null);//只放上面

        Drawable drawableSchedule = getResources().getDrawable(R.drawable.leadership_approval_pic);
        drawableSchedule.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        leadershipApproval.setCompoundDrawables(null, drawableSchedule, null, null);//只放上面

        Drawable drawableApproval = getResources().getDrawable(R.drawable.allocation_pic);
        drawableApproval.setBounds(0, 0, 150, 150);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        workAllocation.setCompoundDrawables(null, drawableApproval, null, null);//只放上面
    }
}
