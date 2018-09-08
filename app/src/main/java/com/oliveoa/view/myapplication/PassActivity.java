package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.FulltimeApplicationService;
import com.oliveoa.controller.JobTransferApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.LeaveOfficeApplicationService;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.RecruitmentApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.Application;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.FulltimeApplication;
import com.oliveoa.pojo.FulltimeApplicationApprovedOpinion;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.JobTransferApplicationApprovedOpinion;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.pojo.LeaveOfficeApplication;
import com.oliveoa.pojo.LeaveOfficeApplicationApprovedOpinion;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingMember;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.pojo.RecruitmentApplication;
import com.oliveoa.pojo.RecruitmentApplicationApprovedOpinion;
import com.oliveoa.pojo.RecruitmentApplicationItem;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class PassActivity extends Fragment {

    private Context mContext;
    private LinearLayout addPasslistView;
    private TextView tvtype,tvcontent;
    private ImageView ivpicture;
    private View rootview;
    private TextView tvtip;
    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Application> applicationList;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private ContactInfoDao contactInfoDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_wait, container, false);
        this.mContext = getActivity();

        Bundle bundle =getArguments();
        applicationList = bundle.getParcelableArrayList("application");
        Log.e(TAG,"applicatinList"+applicationList.toString());

        initView();
        return rootview;
    }


    private void initView() {
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);
        addPasslistView = (LinearLayout) rootview.findViewById(R.id.wait_list);
        addViewItem(null);


    }

    private void initData(){

    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addPasslistView.getChildCount(); i++) {
            final View childAt = addPasslistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            item.setTag("info");
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   tvcontent = (TextView)childAt.findViewById(R.id.item_content);
                   // Toast.makeText(mContext, "你点击了"+tvcontent.getText().toString(), Toast.LENGTH_SHORT).show();
                  if(applicationList.get(finalI).getType()==1){
                        getovertimeapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==2){
                        getleaveapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==3){
                        getbusinessapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==4){
                        getmeetingapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==5){
                        getdimissionapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==6){
                        getregularapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType()==7){
                        getadjustapplicationinfo(applicationList.get(finalI).getAid());
                    }
                    if(applicationList.get(finalI).getType() == 8){
                        getrecruitmentapplicationinfo(applicationList.get(finalI).getAid());
                    }

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (applicationList.size()==0) {//如果申请列表为0，加载空布局
            //Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
            tvtip.setVisibility(View.VISIBLE);
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <applicationList.size(); i ++){
                View applicationview = View.inflate(mContext, R.layout.item_pass, null);
                addPasslistView.addView(applicationview);
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
        for (i = 0; i <  addPasslistView.getChildCount(); i++) {

            View childAt =  addPasslistView.getChildAt(i);
            ivpicture = (ImageView) childAt.findViewById(R.id.application_pic);
            tvtype = (TextView) childAt.findViewById(R.id.item_type);
            tvcontent = (TextView) childAt.findViewById(R.id.item_content);

            tvcontent.setText(applicationList.get(i).getDescribe());
            if(applicationList.get(i).getType()==1){
                tvtype.setText("加班申请");
                ivpicture.setImageResource(R.drawable.overtime_pic);
            }else if(applicationList.get(i).getType()==2){
                tvtype.setText("请假申请");
                ivpicture.setImageResource(R.drawable.leave_pic);
            }else if(applicationList.get(i).getType()==3){
                tvtype.setText("出差申请");
                ivpicture.setImageResource(R.drawable.business_pic);
            }else if(applicationList.get(i).getType()==4){
                tvtype.setText("会议申请");
                ivpicture.setImageResource(R.drawable.meeting_pic);
            }else if(applicationList.get(i).getType()==5){
                tvtype.setText("离职申请");
                ivpicture.setImageResource(R.drawable.dimission_pic);
            }else if(applicationList.get(i).getType()==6){
                tvtype.setText("转正申请");
                ivpicture.setImageResource(R.drawable.regular_pic);
            }else if(applicationList.get(i).getType()==7){
                tvtype.setText("调岗申请");
                ivpicture.setImageResource(R.drawable.adjust_pic);
            }else{
                tvtype.setText("招聘申请");
                ivpicture.setImageResource(R.drawable.recruitment_pic);
            }/*else if(applicationList.get(i).getType()==9){
                tvtype.setText("物品申请");
                ivpicture.setImageResource(R.drawable.goods_pic);
            }*/
        }

    }
    private void getovertimeapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                OvertimeApplicationHttpResponseObject overtimeApplicationHttpResponseObject = overtimeApplictionService.overtimeapplication(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (overtimeApplicationHttpResponseObject.getStatus() == 0) {
                    OvertimeApplicationJsonBean overtimeApplicationJsonBean = overtimeApplicationHttpResponseObject.getOvertimeApplicationJsonBean();
                    OvertimeApplication overtimeApplication = overtimeApplicationJsonBean.getOvertimeApplications();
                    ArrayList<OvertimeApplicationApprovedOpinionList> overtimeApplicationApprovedOpinionLists  = overtimeApplicationJsonBean.getOvertimeApplicationApprovedOpinionLists();
                    startActivity( new Intent(mContext, OvertimeInfoActivity.class)
                            .putExtra("oa",overtimeApplication)
                            .putParcelableArrayListExtra("oaaol",overtimeApplicationApprovedOpinionLists));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,overtimeApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getleaveapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                LeaveApplicationService leaveApplicationService = new LeaveApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                LeaveApplicationHttpResponseObject leaveApplicationHttpResponseObject = leaveApplicationService.getlapplicationinfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (leaveApplicationHttpResponseObject.getStatus() == 0) {
                    LeaveApplicationInfoJsonBean leaveApplicationInfoJsonBean = leaveApplicationHttpResponseObject.getData();
                    LeaveApplication leaveApplication =leaveApplicationInfoJsonBean.getLeaveApplication();
                    ArrayList<LeaveApplicationApprovedOpinionList> leaveApplicationApprovedOpinionLists  = leaveApplicationInfoJsonBean.getLeaveApplicationApprovedOpinionLists();
                    startActivity( new Intent(mContext, LeaveInfoActivity.class)
                            .putExtra("la",leaveApplication)
                            .putParcelableArrayListExtra("laaol",leaveApplicationApprovedOpinionLists));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,leaveApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getbusinessapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (businessTripApplicationHttpResponseObject.getStatus() == 0) {
                    BusinessTripApplicationInfoJsonBean businessTripApplicationInfoJsonBean  = businessTripApplicationHttpResponseObject.getData();
                    BusinessTripApplication businessTripApplication = businessTripApplicationInfoJsonBean.getBusinessTripApplication();
                    ArrayList<BusinessTripApplicationApprovedOpinionList> businessTripApplicationApprovedOpinionLists = businessTripApplicationInfoJsonBean.getBusinessTripApplicationApprovedOpinionLists();
                    startActivity( new Intent(mContext, BusinessInfoActivity.class)
                            .putExtra("bta",businessTripApplication)
                            .putParcelableArrayListExtra("btaaol",businessTripApplicationApprovedOpinionLists));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,businessTripApplicationHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getmeetingapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                MeetingApplicationService service = new MeetingApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    MeetingApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    MeetingApplication meetingApplication = aaol.getMeetingApplication();
                    ArrayList<MeetingMember> list = aaol.getMeetingMembers();
                    startActivity( new Intent(mContext, MeetingInfoActivity.class)
                            .putExtra("ap",meetingApplication)
                            .putParcelableArrayListExtra("aaol",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getdimissionapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                LeaveOfficeApplicationService service = new LeaveOfficeApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<LeaveOfficeApplicationJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    LeaveOfficeApplicationJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    LeaveOfficeApplication ap = aaol.getLeaveOfficeApplication();
                    ArrayList<LeaveOfficeApplicationApprovedOpinion> list = aaol.getLeaveOfficeApplicationApprovedOpinions();
                    startActivity( new Intent(mContext, DimissionInfoActivity.class)
                            .putExtra("ap",ap)
                            .putParcelableArrayListExtra("aaol",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getregularapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                FulltimeApplicationService service = new FulltimeApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<FulltimeApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    FulltimeApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    FulltimeApplication ap = aaol.getFulltimeApplication();
                    ArrayList<FulltimeApplicationApprovedOpinion> list = aaol.getFulltimeApplicationApprovedOpinions();
                    startActivity( new Intent(mContext, RegularWorkerInfoActivity.class)
                            .putExtra("ap",ap)
                            .putParcelableArrayListExtra("aaol",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getadjustapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                JobTransferApplicationService service = new JobTransferApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<JobTransferApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    JobTransferApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    JobTransferApplication ap = aaol.getJobTransferApplication();
                    ArrayList<JobTransferApplicationApprovedOpinion> list = aaol.getJobTransferApplicationApprovedOpinions();
                    startActivity( new Intent(mContext, AdjustPostInfoActivity.class)
                            .putExtra("ap",ap)
                            .putParcelableArrayListExtra("aaol",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getrecruitmentapplicationinfo(final String aid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                RecruitmentApplicationService service = new RecruitmentApplicationService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<RecruitmentApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s,aid);
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                dutyInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    for (int i=0;i<contactInfos.size();i++){
                        Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                        DepartmentInfo departmentInfo =contactInfos.get(i).getDepartment();
                        departmentInfoDao.insert(departmentInfo);
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();i++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                dutyInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getPosition());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    RecruitmentApplicationInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    RecruitmentApplication ap = aaol.getRecruitmentApplication();
                    RecruitmentApplicationItem apitem = aaol.getRecruitmentApplicationItem();
                    ArrayList<RecruitmentApplicationApprovedOpinion> list = aaol.getRecruitmentApplicationApprovedOpinions();
                    startActivity( new Intent(mContext, RecruitmentInfoActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("apitem",apitem)
                            .putParcelableArrayListExtra("aaol",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }


}