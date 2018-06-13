package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveoa.view.MainActivity;
import com.oliveoa.view.R;

public class OvertimeInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView tname,tstatus,ttime,treason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_info);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OvertimeInfoActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
