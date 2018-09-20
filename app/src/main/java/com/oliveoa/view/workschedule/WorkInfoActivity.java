package com.oliveoa.view.workschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveoa.view.R;

public class WorkInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView ttime,tcontext,tadvise,tapproval,tstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_info);

        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.work_time);
        tcontext = (TextView) findViewById(R.id.work_content);
        tadvise = (TextView) findViewById(R.id.advise);
        tapproval = (TextView) findViewById(R.id.person_approving);
        tstatus = (TextView) findViewById(R.id.status);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, MyWorkActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
