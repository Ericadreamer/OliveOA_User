package com.oliveoa.view.approval;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.controller.AnnouncementService;
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
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.FulltimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.JobTransferApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveOfficeApplicationJsonBean;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.RecruitmentApplicationInfoJsonBean;
import com.oliveoa.pojo.AnnouncementApprovedOpinionList;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.Approval;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
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
import com.oliveoa.view.myapplication.AdjustPostInfoActivity;
import com.oliveoa.view.myapplication.BusinessInfoActivity;
import com.oliveoa.view.myapplication.DimissionInfoActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
import com.oliveoa.view.myapplication.MeetingInfoActivity;
import com.oliveoa.view.myapplication.OvertimeInfoActivity;
import com.oliveoa.view.myapplication.RecruitmentInfoActivity;
import com.oliveoa.view.myapplication.RegularWorkerInfoActivity;
import com.oliveoa.view.notice.NoticeInfoActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * 该页面显示已处理的审批列表，加载approval_item.xml列表
 * 未处理跟已处理页面加载同一个item，两者不同的是要显示item的状态是审批中（灰色）、同意（绿色）、不同意（红色）
 */

public class DisposedApprovalActivity extends Fragment {

    private Context mContext;
    private LinearLayout addlistView;
    private TextView tname,tcontent,ttype,tstatus,tvtip;  //名字，内容，类型，状态
    private View rootview;

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<Approval> approvalList;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private ContactInfoDao contactInfoDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_disposed_approval, container, false);
        this.mContext = getActivity();

        Bundle bundle =getArguments();
        approvalList = bundle.getParcelableArrayList("approval");
        Log.e(TAG,"approvalList"+approvalList.toString());


        initView();
        return rootview;
    }

    public void initView(){
        addlistView = (LinearLayout) rootview.findViewById(R.id.approval_list);
      /*  tname = (TextView) rootview.findViewById(R.id.person_name);
        tcontent = (TextView) rootview.findViewById(R.id.approval_type);
        ttype = (TextView) rootview.findViewById(R.id.type);
        tstatus = (TextView) rootview.findViewById(R.id.status);*/
        tvtip = (TextView)rootview.findViewById(R.id.tvtip);

        //默认添加一个Item
        addViewItem(null);
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i <  addlistView.getChildCount(); i++) {
            final View childAt =  addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approval_list);
            item.setTag("info");

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tcontent = (TextView)childAt.findViewById(R.id.item_content);
                    //Toast.makeText(mContext, "你点击了"+tcontent.getText().toString()+"----------"+applicationList.get(finalI).getDescribe(), Toast.LENGTH_SHORT).show();
                    if(approvalList.get(finalI).getType()==1){
                        getovertimeapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==2){
                        getleaveapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==3){
                        getbusinessapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==4){
                        getmeetingapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==5){
                        getdimissionapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==6){
                        getregularapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType()==7){
                        getadjustapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType() == 8){
                        getrecruitmentapplicationinfo(approvalList.get(finalI).getAid());
                    }
                    if(approvalList.get(finalI).getType() == 9){
                        getnoticceinfo(approvalList.get(finalI).getAid());
                    }
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (approvalList.size()==0) {//如果申请列表为0，加载空布局
            //Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
            tvtip.setVisibility(View.VISIBLE);
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <approvalList.size(); i ++){
                View applicationview = View.inflate(mContext, R.layout.approval_item, null);
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
        for (i = 0; i <   addlistView.getChildCount(); i++) {

            View childAt =   addlistView.getChildAt(i);
            tstatus = (TextView)childAt.findViewById(R.id.status);
            switch (approvalList.get(i).getIsapprove()){
                case -1:
                    tstatus.setText("不同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                    break;
                case 1:
                    tstatus.setText("同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                    break;
                case 0:
                    tstatus.setText("审核中");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray));
                    break;
                case -2:
                    tstatus.setText("审核中");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray));
                    break;
                default:
                    tstatus.setText("");
                    //tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                    break;

            }
            //tstatus.setVisibility(View.GONE);
            tname = (TextView) childAt.findViewById(R.id.person_name);
            contactInfoDao = EntityManager.getInstance().getContactInfo();
            ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(approvalList.get(i).getSeid())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
            }
            tcontent = (TextView) childAt.findViewById(R.id.approval_content);
            tcontent.setText(approvalList.get(i).getContent());
           /* ttype = (TextView) childAt.findViewById(R.id.type);
            if(approvalList.get(i).getType()==1){
                ttype.setText("加班申请");
            }else if(approvalList.get(i).getType()==2){
                ttype.setText("请假申请");;
            }else if(approvalList.get(i).getType()==3){
                ttype.setText("出差申请");
            }else if(approvalList.get(i).getType()==4){
                ttype.setText("会议申请");
            }else if(approvalList.get(i).getType()==5){
                ttype.setText("离职申请");
            }else if(approvalList.get(i).getType()==6){
                ttype.setText("转正申请");
            }else if(approvalList.get(i).getType()==7){
                ttype.setText("调岗申请");
            }else if(approvalList.get(i).getType()==8){
                ttype.setText("招聘申请");
            }else{
                ttype.setText("公告审批");
            }*/
        }

    }

    private void getovertimeapplicationinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                            .putExtra("index",1)
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
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                            .putExtra("index",1)
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
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                            .putExtra("index",1)
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
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取会议申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getdimissionapplicationinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                    ArrayList<LeaveOfficeApplicationApprovedOpinion> list = aaol.getLeaveOfficeApplicationApprovedOpinionList();
                    startActivity( new Intent(mContext, DimissionInfoActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取离职申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getregularapplicationinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                    ArrayList<FulltimeApplicationApprovedOpinion> list = aaol.getFulltimeApplicationApprovedOpinionList();
                    startActivity( new Intent(mContext, RegularWorkerInfoActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取转正申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getadjustapplicationinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                    ArrayList<JobTransferApplicationApprovedOpinion> list = aaol.getJobTransferApplicationApprovedOpinionList();
                    startActivity( new Intent(mContext, AdjustPostInfoActivity.class)
                            .putExtra("ap",ap)
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取调岗申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private void getrecruitmentapplicationinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
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
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
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
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取招聘申请数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
    private void getnoticceinfo(final String aid) {
        LoadingDialog loadingDialog  = new LoadingDialog(getActivity(),"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                AnnouncementService service = new AnnouncementService();
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                StatusAndDataHttpResponseObject<AnnouncementInfoJsonBean> statusAndDataHttpResponseObject = service.get_annoucementinfo(s,aid);
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
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(departmentInfo.getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            for(int k=0;k<dutyInfos.size();k++){
                                dutyInfoDao.insert(dutyInfos.get(k));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(mContext, dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        Log.d(TAG,"contactInfos.get(i).getEmpContactList().size():"+contactInfos.get(i).getEmpContactList().size());
                        for(int j=0;j<contactInfos.get(i).getEmpContactList().size();j++){
                            if(contactInfos.get(i).getEmpContactList().get(j).getEmployee()!=null) {
                                Log.d(TAG,"contactInfos.get(i).getEmpContactList().get(j).getEmployee()"+contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                            }
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                if (statusAndDataHttpResponseObject.getStatus() == 0) {
                    AnnouncementInfoJsonBean aaol  = statusAndDataHttpResponseObject.getData();
                    AnnouncementInfo ap = aaol.getAnnouncement();
                    ArrayList<AnnouncementApprovedOpinionList> list = aaol.getAnnouncementApprovedOpinionList();
                    startActivity( new Intent(mContext, NoticeInfoActivity.class)
                            .putExtra("notice",ap)
                            .putExtra("index",1)
                            .putParcelableArrayListExtra("list",list));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext,"获取公告数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }
}
