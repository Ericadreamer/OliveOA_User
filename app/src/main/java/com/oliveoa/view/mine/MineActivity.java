package com.oliveoa.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oliveoa.view.LoginActivity;
import com.oliveoa.view.R;
import com.oliveoa.view.note.EditNoteActivity;
import com.oliveoa.view.note.MyNoteActivity;

public class MineActivity extends Fragment {
    private LinearLayout personal,password,advise,about,update,edit;
    private Context mContext;
    private View rootview;

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
        edit = (LinearLayout) rootview.findViewById(R.id.edit);

        //点击事件
        personal.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalDetailsActivity.class);
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

        edit.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
