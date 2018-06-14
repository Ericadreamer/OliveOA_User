package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.LeaveApplicationHttpResponseObject;
import com.oliveoa.common.OvertimeApplicationHttpResponseObject;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DaoMaster;
import com.oliveoa.greendao.DaoSession;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.IssueWorkDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.MessageDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.EmpContactListJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationInfoJsonBean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.Message;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DataCleanManager;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingViewActivity extends AppCompatActivity {


    private String TAG = this.getClass().getSimpleName();
    private ArrayList<ContactJsonBean> contactInfos;
    private ArrayList<Message> messages;
    private ContactInfo user;
    private ArrayList<EmpContactListJsonBean> empContactList;
    private ArrayList<OvertimeApplication> oa;
    private OvertimeApplicationJsonBean oaaol;
    private ArrayList<LeaveApplication> la;
    private LeaveApplicationInfoJsonBean laaol;
    private ArrayList<BusinessTripApplication> bta;
    private BusinessTripApplicationInfoJsonBean btaaol;


    private ContactInfoDao contactInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private BusinessTripApplicationDao btaDao;
    private BusinessTripApplicationApprovedOpinionListDao btaaolDao;
    private IssueWorkDao issueWorkDao;
    private LeaveApplicationDao laDao;
    private LeaveApplicationApprovedOpinionListDao laaolDao;
    private MessageDao messageDao;
    private OvertimeApplicationDao oaDao;
    private OvertimeApplicationApprovedOpinionListDao oaaolDao;



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
        //String string = getString(R.string.app_name);

         DataCleanManager dataCleanManager = new DataCleanManager();
        dataCleanManager.cleanDatabases();

        contactInfoDao = EntityManager.getInstance().getContactInfo();
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
        btaDao =EntityManager.getInstance().getBusinessTripApplicationInfo();
        btaaolDao =EntityManager.getInstance().getBusinessTripApplicationApprovedOpinionListInfo();
        issueWorkDao = EntityManager.getInstance().getIssueWorkInfo();
        laDao = EntityManager.getInstance().getLeaveApplicationInfo();
        laaolDao = EntityManager.getInstance().getLeaveApplicationApprovedOpinionListInfo();
        messageDao = EntityManager.getInstance().getMessageInfo();
        oaDao = EntityManager.getInstance().getOvertimeApplicationInfo();
        oaaolDao = EntityManager.getInstance().getOvertimeApplicationApprovedOpinionListInfo();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取SharePreferences的cookies
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");


                //获取用户数据
                UserInfoService userInfoService = new UserInfoService();
                UserLoginJsonBean userlogin = userInfoService.userinfo(s);
                if (userlogin.getStatus()==0) {
                    user = userlogin.getData();
                    Log.d("userinfo", user.toString());
                    contactInfoDao.insert(user);
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取个人信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                //获取通讯录信息
                ContactHttpResponseObject contactHttpResponseObject =userInfoService.contact(s);
                if (contactHttpResponseObject.getStatus()==0) {
                    contactInfos = contactHttpResponseObject.getData();
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
                    Toast.makeText(getApplicationContext(), "网络错误，获取通讯录信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                //消息详情
//                MessageService messageService = new MessageService();
//                MessageJsonbean messageJsonbean = messageService.getMessagebyme(s,0);
//                if (messageJsonbean.getStatus()==0) {
//                    messages = messageJsonbean.getData();
//                    Log.d("message", messages.toString());
//                    for (int i=0;i<messages.size();i++){
//                        messageDao.insert(messages.get(i));
//                    }
//                }else{
//                    Looper.prepare();//解决子线程弹toast问题
//                    Toast.makeText(getApplicationContext(), "网络错误，获取个人信息失败", Toast.LENGTH_SHORT).show();
//                    Looper.loop();// 进入loop中的循环，查看消息队列
//                }


                //加班申请详情
                OvertimeApplictionService overtimeApplictionService = new OvertimeApplictionService();
                OvertimeApplicationInfoJsonBean overtimeApplications = overtimeApplictionService.submitotapplication(s);
                if (overtimeApplications.getStatus()==0) {
                     oa = overtimeApplications .getData();
                    Log.d("overtimeApplication",oa.toString());
                    Log.d(TAG,"overtimeApplication.size:"+oa.size());
                    for (int i=0;i<oa.size();i++){
                        Log.d(TAG,"overtimeApplication.oaid["+i+"]:"+oa.get(i).getOaid());
                        OvertimeApplicationHttpResponseObject oahrom = overtimeApplictionService.overtimeapplication(s,oa.get(i).getOaid());
                        Log.e(TAG,"oahro"+oahrom.toString());
                        if(oahrom.getStatus() == 0){
                            oaaol =oahrom.getOvertimeApplicationJsonBean();
                            Log.d(TAG,"oaaol"+oaaol.toString());
                            if(oaaol.getOvertimeApplicationApprovedOpinionLists()!=null) {
                                Log.d(TAG,"oaaol.getOvertimeApplicationApprovedOpinionLists():"+oaaol.getOvertimeApplicationApprovedOpinionLists().toString());
                                for (int j = 0; j < oaaol.getOvertimeApplicationApprovedOpinionLists().size(); j++) {
                                    oaaolDao.insert(oaaol.getOvertimeApplicationApprovedOpinionLists().get(j));
                                }
                            }
                        }
                        oaDao.insert(oa.get(i));
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取加班申请信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                //加班审核详情
//               OvertimeApplicationApprovedOpinionListInfoJsonBean overtimeApplicationApprovedOpinionListInfoJsonBean = overtimeApplictionService.unapprovedotapplication(s);
//                if (overtimeApplicationApprovedOpinionListInfoJsonBean.getStatus()==0) {
//                    oaaol = overtimeApplicationApprovedOpinionListInfoJsonBean .getData();
//                    Log.d("oaaol",oaaol.toString());
//                    for (int i=0;i<oa.size();i++){
//                        oaaolDao.insert(oaaol.get(i));
//                    }
//                }else{
//                    Looper.prepare();//解决子线程弹toast问题
//                    Toast.makeText(getApplicationContext(), "网络错误，获取加班申请信息失败", Toast.LENGTH_SHORT).show();
//                    Looper.loop();// 进入loop中的循环，查看消息队列
//                }

                //出差申请详情
                BusinessTripApplicationService businessTripApplicationService = new BusinessTripApplicationService();
                BusinessTripApplicationJsonBean businessTripApplicationJsonBean = businessTripApplicationService.getbtapplicationsubmited(s);
                if (businessTripApplicationJsonBean.getStatus()==0) {
                    bta = businessTripApplicationJsonBean .getData();
                    Log.d("bta:",bta.toString());
                    for (int i=0;i<bta.size();i++){
                        BusinessTripApplicationHttpResponseObject businessTripApplicationHttpResponseObject = businessTripApplicationService.getbtapplicationinfo(s,bta.get(i).getBtaid());
                        if(businessTripApplicationHttpResponseObject.getStatus() == 0){
                            btaaol = businessTripApplicationHttpResponseObject.getData();
                            Log.i(TAG,"btaaol:"+btaaol);
                            if(btaaol.getBusinessTripApplicationApprovedOpinionLists()!=null) {
                                for (int j = 0; j < btaaol.getBusinessTripApplicationApprovedOpinionLists().size(); j++) {
                                    btaaolDao.insert(btaaol.getBusinessTripApplicationApprovedOpinionLists().get(j));
                                }
                            }
                        }
                        btaDao.insert(bta.get(i));
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取加班申请信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                //出差审核详情



                //请假申请详情
                LeaveApplicationService leaveApplicationService = new LeaveApplicationService();
                LeaveApplicationJsonBean leaveApplicationJsonBean = leaveApplicationService.getlapplicationsubmited(s);
                if (businessTripApplicationJsonBean.getStatus()==0) {
                    la = leaveApplicationJsonBean .getData();
                    Log.d("la:",la.toString());
                    for (int i=0;i<la.size();i++){
                        LeaveApplicationHttpResponseObject leaveApplicationHttpResponseObject = leaveApplicationService.getlapplicationinfo(s,la.get(i).getLaid());
                        if(leaveApplicationHttpResponseObject.getStatus() == 0){
                            laaol = leaveApplicationHttpResponseObject.getData();
                            Log.i(TAG,"laaol:"+laaol);
                            if(laaol.getLeaveApplicationApprovedOpinionLists()!=null) {
                                for (int j = 0; j < laaol.getLeaveApplicationApprovedOpinionLists().size(); j++) {
                                    laaolDao.insert(laaol.getLeaveApplicationApprovedOpinionLists().get(j));
                                }
                            }
                        }
                        laDao.insert(la.get(i));
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取加班申请信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

                //请假审核详情



                //提交工作详情
                WorkDetailService workDetailService = new WorkDetailService();


                //发布工作详情


            }
        }).start();


        final Intent intent = new Intent(this,TabLayoutBottomActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3000);//此处的Delay可以是3*1000，代表三秒
    }
}
