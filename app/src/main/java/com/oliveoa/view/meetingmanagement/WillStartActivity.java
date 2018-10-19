package com.oliveoa.view.meetingmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingApplicationAndStatus;
import com.oliveoa.pojo.MeetingMember;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class WillStartActivity extends Fragment {
    private View rootview;
    private TextView tvtip;
    private Context mContext;
    private LinearLayout addlistView;
    private TextView tvtype,tvcontent;
    private String TAG = this.getClass().getSimpleName();
    private ArrayList<MeetingApplicationAndStatus> applicationList;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private ContactInfoDao contactInfoDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.tab_list, container, false);
        this.mContext = getActivity();

        Bundle bundle =getArguments();
        applicationList = bundle.getParcelableArrayList("application");
        Log.e(TAG,"applicatinList"+applicationList.toString());

        initView();
        return rootview;
    }

    private void initView() {
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        addlistView = (LinearLayout) rootview.findViewById(R.id.my_meeting_list);
        addViewItem(null);
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approval_list);
            item.setTag("info");

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvcontent = (TextView)childAt.findViewById(R.id.meeting_topic);
                    //Toast.makeText(mContext, "你点击了"+tvcontent.getText().toString()+"----------"+applicationList.get(finalI).getTheme(), Toast.LENGTH_SHORT).show();
                    getmeetingapplicationinfo(applicationList.get(finalI).getMaid());
                }
            });
        }
    }
    private void getmeetingapplicationinfo(final String aid) {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity(), "正在加载数据", true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s, aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus() == 0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i = 0; i < contactInfos.size(); i++) {
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                        for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                            if (contactInfos.get(i).getEmpContactList().get(j).getEmployee() != null) {
                                Log.d(TAG, "contactInfos.get(i).getEmpContactList().get(j).getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                    MeetingApplication meetingApplication = aaol.getMeetingApplication();
                    ArrayList<MeetingMember> list = aaol.getMeetingMembers();
                    startActivity(new Intent(mContext, MyMeetingInfoActivity.class)
                            .putExtra("ap", meetingApplication)
                            .putParcelableArrayListExtra("list", list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (applicationList.size()==0) {//如果申请列表为0，加载空布局
            //Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
            tvtip.setVisibility(View.VISIBLE);
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <applicationList.size(); i ++){
                View applicationview = View.inflate(mContext, R.layout.my_meeting_list, null);
                addlistView.addView(applicationview);
                InitDataViewItem();
            }
            sortViewItem();

        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i <  addlistView.getChildCount(); i++) {

            View childAt =  addlistView.getChildAt(i);
            tvtype = (TextView) childAt.findViewById(R.id.meeting_topic);
            tvcontent = (TextView) childAt.findViewById(R.id.time);
            tvtype.setText(applicationList.get(i).getTheme());
            tvcontent.setText(applicationList.get(i).getStarttime()+"-"+applicationList.get(i).getEndtime());

        }

    }
}
