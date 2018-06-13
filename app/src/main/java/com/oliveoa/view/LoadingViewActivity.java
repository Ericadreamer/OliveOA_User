package com.oliveoa.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oliveoa.controller.LoginService;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;

public class LoadingViewActivity extends AppCompatActivity {

    private ArrayList<ContactInfo> contactInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<DutyInfo> dutyInfos;
    //private ArrayList<>;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        initView();
    }
    public void initView(){
        initData();
    }
    public void initData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取SharePreferences的cookies
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");


            }
        }).start();
    }
}
