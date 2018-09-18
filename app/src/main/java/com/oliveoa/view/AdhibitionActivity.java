package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.NoteInfoDao;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingApplicationAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.approval.MyApprovalActivity;
import com.oliveoa.view.meetingmanagement.MyMeetingActivity;
import com.oliveoa.view.myapplication.AddApplicationActivity;
import com.oliveoa.view.myapplication.MainApplicationActivity;
import com.oliveoa.view.myapplication.MeetingActivity;
import com.oliveoa.view.myapplication.MyApplicationActivity;
import com.oliveoa.view.myapplication.WaitActivity;
import com.oliveoa.view.myapplication.WaitActivity1;
import com.oliveoa.view.note.MyNoteActivity;
import com.oliveoa.view.workschedule.MyWorkActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class AdhibitionActivity extends Fragment {

    private RadioButton application,schedule,approval,document,meeting,note;
    private Context mContext;
    private ImageView more;
    private LinearLayout addMlistView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_adhibition, container, false);
        this.mContext = getActivity();

        addMlistView = (LinearLayout) rootview.findViewById(R.id.message_list);
        //默认添加一个Item
        addViewItem(null);

        //设置图标大小
        //我的申请
        application = (RadioButton) rootview.findViewById(R.id.home_application);
        application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "您点击了我的申请", Toast.LENGTH_SHORT).show();
                applicationinfo();
            }
        });

        //工作日程
        schedule = (RadioButton) rootview.findViewById(R.id.home_schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "您点击了工作日程", Toast.LENGTH_SHORT).show();
                scheduleinfo();
            }
        });

        //审批
        approval = (RadioButton) rootview.findViewById(R.id.home_appravol);
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "您点击了审批", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(mContext, "您点击了会议管理", Toast.LENGTH_SHORT).show();
                LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
                loadingDialog.show();
                meetinginfo();
            }
        });

        //便签
        note = (RadioButton) rootview.findViewById(R.id.home_note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "您点击了便签", Toast.LENGTH_SHORT).show();
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

        more = (ImageView)rootview.findViewById(R.id.more);

        //监听事件
        more.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                final Intent it = new Intent(getActivity(), MessageListActivity.class);
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(it); //执行
                    }
                };
                timer.schedule(task, 1000 * 0); //0秒后
            }
        });

        return rootview;

    }
    //添加ViewItem
    private void addViewItem(View view){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

    }

    //我的申请
    private void applicationinfo() {
        final Intent it = new Intent(getActivity(), MainApplicationActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后
    }

    //工作日程
    private void scheduleinfo() {
        final Intent it = new Intent(getActivity(), MyWorkActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后

    }

    //审批
    private void approvalinfo() {
        final Intent it = new Intent(getActivity(), MainApprovalActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后


    }

    //公文管理
    private void documentinfo() {

    }

    //会议管理
    private void meetinginfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                MeetingApplicationService service = new MeetingApplicationService();
                MeetingApplicationAndStatusDao meetingApplicationAndStatusDao = EntityManager.getInstance().getMeetingApplicationAndStatusDao();
                MeetingApplicationAndStatus application = new MeetingApplicationAndStatus();
                DateFormat dateFormat = new DateFormat();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIdoing(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                           application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                           application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                           application.setMaid(aaol.getMeetingApplication().getMaid());
                           application.setTheme(aaol.getMeetingApplication().getTheme());
                           application.setStatus(1);
                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    //startActivity(new Intent(getActivity(), MyApplicationActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIwilldo(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                            application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                            application.setMaid(aaol.getMeetingApplication().getMaid());
                            application.setTheme(aaol.getMeetingApplication().getTheme());
                            application.setStatus(2);

                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIdone(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for ( i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject  = service.getApplicationInfo(s,arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                            application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                            application.setMaid(aaol.getMeetingApplication().getMaid());
                            application.setTheme(aaol.getMeetingApplication().getTheme());
                            application.setStatus(3);
                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(getActivity(),MyMeetingActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    //便签
    private void noteinfo() {
        final Intent it = new Intent(getActivity(), MyNoteActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后

    }





}
