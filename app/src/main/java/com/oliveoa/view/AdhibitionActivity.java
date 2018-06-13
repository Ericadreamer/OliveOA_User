package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.myapplication.MyApplicationActivity;

import java.util.Timer;
import java.util.TimerTask;


public class AdhibitionActivity extends Fragment {

    private RadioButton application,schedule,approval,document,meeting,note;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_adhibition, container, false);
        this.mContext = getActivity();

        //设置图标大小
        //我的申请
        application = (RadioButton) rootview.findViewById(R.id.home_application);
        application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent it = new Intent(getActivity(), MyApplicationActivity.class);
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(it); //执行
                    }
                };
                timer.schedule(task, 1000 * 0); //0秒后
                //Toast.makeText(mContext, "您点击了我的申请", Toast.LENGTH_SHORT).show();
                applicationinfo();
            }
        });

        //工作日程
        schedule = (RadioButton) rootview.findViewById(R.id.home_schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您点击了工作日程", Toast.LENGTH_SHORT).show();
                scheduleinfo();
            }
        });

        //审批
        approval = (RadioButton) rootview.findViewById(R.id.home_appravol);
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您点击了审批", Toast.LENGTH_SHORT).show();
                approvalinfo();
            }
        });

        //公文管理
        document = (RadioButton) rootview.findViewById(R.id.home_document);
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您点击了公文管理", Toast.LENGTH_SHORT).show();
                documentinfo();
            }
        });

        //我的会议
        meeting = (RadioButton) rootview.findViewById(R.id.home_meeting);
        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您点击了会议管理", Toast.LENGTH_SHORT).show();
                meetinginfo();
            }
        });

        //便签
        note = (RadioButton) rootview.findViewById(R.id.home_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您点击了便签", Toast.LENGTH_SHORT).show();
                noteinfo();
            }
        });

        Drawable drawableApplication = getResources().getDrawable(R.drawable.ic_menu_application);
        drawableApplication.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        application.setCompoundDrawables(null, drawableApplication, null, null);//只放上面

        Drawable drawableSchedule = getResources().getDrawable(R.drawable.ic_menu_notify);
        drawableSchedule.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        schedule.setCompoundDrawables(null, drawableSchedule, null, null);//只放上面

        Drawable drawableApproval = getResources().getDrawable(R.drawable.ic_menu_examine);
        drawableApproval.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        approval.setCompoundDrawables(null, drawableApproval, null, null);//只放上面

        Drawable drawableDocument = getResources().getDrawable(R.drawable.ic_menu_official);
        drawableDocument.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        document.setCompoundDrawables(null, drawableDocument, null, null);//只放上面

        Drawable drawableMeeting = getResources().getDrawable(R.drawable.ic_menu_meeting);
        drawableMeeting.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        meeting.setCompoundDrawables(null, drawableMeeting, null, null);//只放上面

        Drawable drawableNote = getResources().getDrawable(R.drawable.ic_menu_note);
        drawableNote.setBounds(0, 0, 165, 165);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        note.setCompoundDrawables(null, drawableNote, null, null);//只放上面




        return rootview;

    }
    //我的申请
    private void applicationinfo() {

    }

    //工作日程
    private void scheduleinfo() {

    }

    //审批
    private void approvalinfo() {

    }

    //公文管理
    private void documentinfo() {

    }

    //会议管理
    private void meetinginfo() {

    }

    //便签
    private void noteinfo() {

    }





}
