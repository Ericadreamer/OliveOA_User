package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;

public class MyWorkActivity extends AppCompatActivity {

    private ImageView back;
    private RadioButton protocolWork,leadershipApproval,workAllocation;

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

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWorkActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
