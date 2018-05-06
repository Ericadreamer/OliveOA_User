package com.example.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erica.oliveoa_user.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText id_login;
    private EditText password_login;
    private ImageView logo_login;
    private CheckBox auto_login;
    private Button button_login;
    private SharedPreferences sp;
    private String idvalue;
    private String passwordlavue;
    private static final int PASSWORD_MIWEN = 0x81;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=this.getSharedPreferences("userInfo",Context.MODE_WORLD_READABLE);
        //找到相应的布局及文件
        setContentView(R.layout.activity_login);
        id_login=(EditText) findViewById(R.id.login_id);
        password_login=(EditText) findViewById(R.id.login_password);
        logo_login=(ImageView) findViewById(R.id.login_logo);
        auto_login=(CheckBox) findViewById(R.id.login_autologin);
        button_login=(Button) findViewById(R.id.login_button);

        if(sp.getBoolean("ischeck",false)){
            id_login.setText(sp.getString("PHONEEDIT"));
            password_login.setText(sp.getString("PASSWORD",""));
            //密文密码
            password_login.setInputType(PASSWORD_MIWEN);
            if(sp.getBoolean("auto_ischeck",false)){
                auto_login.setChecked(true);
                Intent intent = new Intent(LoginActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        }
    }
}
