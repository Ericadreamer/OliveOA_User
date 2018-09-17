package com.oliveoa.view.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

public class PasswordEditActivity extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);

        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordEditActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",3);
                startActivity(intent);
                finish();
            }
        });
    }
}
