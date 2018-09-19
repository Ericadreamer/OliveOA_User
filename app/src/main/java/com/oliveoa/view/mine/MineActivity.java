package com.oliveoa.view.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.controller.LoginService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.UserInfoDao;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.UserInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.LoginActivity;
import com.oliveoa.view.MainActivity;
import com.oliveoa.view.R;
import com.oliveoa.view.note.EditNoteActivity;
import com.oliveoa.view.note.MyNoteActivity;

import static android.content.Context.MODE_PRIVATE;

public class MineActivity extends Fragment {
    private LinearLayout personal,password,advise,about,update,exit;
    private TextView tvname,tvid;
    private Context mContext;
    private View rootview;
    private UserInfo userInfo;
    private UserInfoDao userInfoDao;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;

    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_mine, container, false);
        this.mContext = getActivity();

        initView();
        return rootview;
    }

    public void initView() {
        personal = (LinearLayout) rootview.findViewById(R.id.personal);
        password = (LinearLayout) rootview.findViewById(R.id.password);
        advise = (LinearLayout) rootview.findViewById(R.id.advise);
        about = (LinearLayout) rootview.findViewById(R.id.about_us);
        update = (LinearLayout) rootview.findViewById(R.id.update);
        exit = (LinearLayout) rootview.findViewById(R.id.edit);

        tvname = (TextView)rootview.findViewById(R.id.application_person_name);
        tvid = (TextView)rootview.findViewById(R.id.approval_type);

        initData();
        //点击事件
        personal.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Log.e(String.valueOf(mContext),contactInfo.toString());
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
                intent.putExtra("ci",contactInfo);
                intent.putExtra("index",1);
                startActivity(intent);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PasswordEditActivity.class);
                startActivity(intent);
            }
        });

        advise.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdviseActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutsUsActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "当前版本为最新版本", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                LoginService loginService = new LoginService();
                LogoutJsonBean logoutJsonBean = loginService.logout(s);
                //System.out.println("logoutisSuccess = "+ logoutJsonBean.getStatus());

                if (logoutJsonBean.getStatus()==0){
                    pref.edit().remove("sessionid").commit();//移除指定数值
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getActivity(), "网络错误，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }
            }
        }).start();
    }

    private void initData() {
        userInfoDao = EntityManager.getInstance().getUserInfoDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        userInfo = userInfoDao.queryBuilder().unique();
        if(userInfo!=null) {
            tvname.setText(userInfo.getName());
            tvid.setText("编号：" + userInfo.getId());
            contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(userInfo.getEid())).unique();

        }
    }


}
