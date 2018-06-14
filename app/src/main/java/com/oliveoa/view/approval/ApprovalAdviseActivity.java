package com.oliveoa.view.approval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.MyApplicationActivity;
import com.oliveoa.view.myapplication.OvertimeActivity;

public class ApprovalAdviseActivity extends AppCompatActivity {

    private ImageView back,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_advise);

        initView();
        initData();
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);

        LinesEditView linesEditView = new LinesEditView(ApprovalAdviseActivity.this);
        String test = linesEditView.getContentText();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApprovalAdviseActivity.this, BusinessUndisposedActivity.class);
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

}
