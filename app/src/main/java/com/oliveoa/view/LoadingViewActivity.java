package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.MessageService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.AnnouncementApprovedOpinionListDao;
import com.oliveoa.greendao.AnnouncementInfoDao;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.MessageDao;
import com.oliveoa.greendao.NoteInfoDao;
import com.oliveoa.greendao.UserInfoDao;
import com.oliveoa.jsonbean.BusinessTripApplicationInfoJsonBean;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.EmpContactListJsonBean;
import com.oliveoa.jsonbean.LeaveApplicationInfoJsonBean;
import com.oliveoa.jsonbean.MessageJsonbean;
import com.oliveoa.jsonbean.OvertimeApplicationJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.Message;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.UserInfo;
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
    private AnnouncementApprovedOpinionListDao announcementApprovedOpinionListDao;
    private AnnouncementInfoDao announcementInfoDao;
    private ApplicationDao applicationDao;
    private MeetingApplicationDao meetingApplicationDao;
    private ApproveNumberDao approveNumberDao;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private MeetingApplicationAndStatusDao meetingApplicationAndStatusDao;
    private NoteInfoDao noteInfoDao;
    private UserInfoDao userInfoDao;
    private MessageDao messageDao;


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

       /* DataCleanManager dataCleanManager = new DataCleanManager();
        dataCleanManager.cleanDatabases();*/

        DaoManager.getInstance().getSession().clear();

        contactInfoDao = EntityManager.getInstance().getContactInfo();
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
        announcementApprovedOpinionListDao =EntityManager.getInstance().getAnnouncementApprovedOpinionListDao();
        announcementInfoDao = EntityManager.getInstance().getAnnouncementInfoDao();//公告
        applicationDao = EntityManager.getInstance().getApplicationDao();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        meetingApplicationDao = EntityManager.getInstance().getMeetingApplicationDao();
        departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
        meetingApplicationAndStatusDao = EntityManager.getInstance().getMeetingApplicationAndStatusDao();
       // noteInfoDao = EntityManager.getInstance().getNoteInfoDao();
        userInfoDao = EntityManager.getInstance().getUserInfoDao();
        messageDao = EntityManager.getInstance().getMessageInfo();

        announcementInfoDao.deleteAll();//公告
        contactInfoDao.deleteAll();
        departmentInfoDao.deleteAll();
        dutyInfoDao.deleteAll();
        applicationDao.deleteAll();
        approveNumberDao.deleteAll();
        meetingApplicationDao.deleteAll();
        departmentAndDutyDao.deleteAll();
        meetingApplicationAndStatusDao.deleteAll();
        userInfoDao.deleteAll();
        messageDao.deleteAll();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取SharePreferences的cookies
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");


                //获取用户数据
                UserInfoService userInfoService = new UserInfoService();
                  /*UserLoginJsonBean userlogin = userInfoService.userinfo(s);
                if (userlogin.getStatus()==0) {
                    user = userlogin.getData();
                    Log.d("userinfo", user.toString());
                    contactInfoDao.insert(user);
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取个人信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }*/
                  UserLoginJsonBean userLoginJsonBean = userInfoService.userinfo(s);
                  if(userLoginJsonBean.getStatus()==0){
                      UserInfo userInfo = new UserInfo();
                      userInfo.setEid(userLoginJsonBean.getData().getEid());
                      userInfo.setId(userLoginJsonBean.getData().getId());
                      userInfo.setDcid(userLoginJsonBean.getData().getDcid());
                      userInfo.setPcid(userLoginJsonBean.getData().getPcid());
                      userInfo.setOrderby(userLoginJsonBean.getData().getOrderby());
                      userInfo.setEmail(userLoginJsonBean.getData().getEmail());
                      userInfo.setName(userLoginJsonBean.getData().getName());
                      userInfo.setSex(userLoginJsonBean.getData().getSex());
                      userInfo.setBirth(userLoginJsonBean.getData().getBirth());
                      userInfo.setAddress(userLoginJsonBean.getData().getAddress());
                      userInfo.setTel(userLoginJsonBean.getData().getTel());
                      userInfoDao.insert(userInfo);
                  }else{
                      Looper.prepare();//解决子线程弹toast问题
                      Toast.makeText(getApplicationContext(),userLoginJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                      Looper.loop();// 进入loop中的循环，查看消息队列
                  }

                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);
                if(contactHttpResponseObject.getStatus()==0){
                    ArrayList<ContactJsonBean> contactJsonBean = contactHttpResponseObject.getData();
                    for(int i =0;i<contactJsonBean.size();i++){
                        departmentInfoDao.insert(contactJsonBean.get(i).getDepartment());
                        DutyInfoJsonBean dutyInfoJsonBean = userInfoService.getPosition(contactJsonBean.get(i).getDepartment().getDcid());
                        if(dutyInfoJsonBean.getStatus()==0){
                            for(int j=0;j<dutyInfoJsonBean.getData().size();j++){
                                dutyInfoDao.insert(dutyInfoJsonBean.getData().get(j));
                            }
                        }else{
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                        for(int j=0;j<contactJsonBean.get(i).getEmpContactList().size();j++){
                            contactInfoDao.insert(contactJsonBean.get(i).getEmpContactList().get(j).getEmployee());
                        }
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                //消息详情
                MessageService messageService = new MessageService();
                MessageJsonbean messageJsonbean = messageService.getMessagetome(s,0);
                if (messageJsonbean.getStatus()==0) {
                    messages = messageJsonbean.getData();
                    Log.d("message", messages.toString());
                    for (int i=0;i<messages.size();i++){
                        messageDao.insert(messages.get(i));
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取个人信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

               /* AnnouncementService announcementService = new AnnouncementService();
                AnnouncementJsonBean announcementJsonBean = announcementService.get_published_annoucements(s);
                if(announcementJsonBean.getStatus()==0){
                    List<AnnouncementInfo> announcementInfos = announcementJsonBean.getData();
                    Log.e(TAG,announcementInfos.toString());
                    for (int i=0;i<announcementInfos.size();i++){
                        announcementInfoDao.insert(announcementInfos.get(i));
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取公告信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }*/



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
