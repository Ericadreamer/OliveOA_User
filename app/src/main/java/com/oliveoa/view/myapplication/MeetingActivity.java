package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DateTimePicker;

public class MeetingActivity extends AppCompatActivity {
    private ImageView back,save;
    private EditText eTopic,eplace; //会议主题，会议地点
    private TextView tstartTime,tendTime,taddMember,taddPerson,tname;
    private LinearLayout addMemberList;

    private DateTimePicker picker;
   /* private View textAreaView;
    private EditText treson;*/
    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private MeetingApplicationDao applicationDao;
    private List<ApproveNumber> eps; //参会人员
    private ApproveNumber ep;//审批人
    private ApproveNumberDao approveNumberDao;
    private MeetingApplication application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        index = getIntent().getIntExtra("index",index);//审批选择1，列表0，参加选择2
        Log.e("IDDEX=", String.valueOf(index));

        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        eTopic = (EditText) findViewById(R.id.meeting_topic);
        eplace = (EditText) findViewById(R.id.meeting_place);
        taddPerson = (TextView) findViewById(R.id.person_add);
        //addPersonList = (LinearLayout) findViewById(R.id.approve_list);
        taddMember = (TextView) findViewById(R.id.members_add);
        addMemberList = (LinearLayout) findViewById(R.id.members_list);
        tstartTime = (TextView) findViewById(R.id.start);
        tendTime = (TextView) findViewById(R.id.end);


        addlistView = (LinearLayout) findViewById(R.id.approve_list);

        initData();

        taddMember.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addMember();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addPerson();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // 默认添加一个Item
        if(index==1||index==2){
            addViewItem(null);//参会
        }
    }

    private void back() {
        if(index==1){
            if(eps!=null){
                approveNumberDao.deleteAll();
            }
            applicationDao.deleteAll();
        }
        Intent intent = new Intent(MeetingActivity.this, MainApplicationActivity.class);
        startActivity(intent);
        finish();
    }

    private void initData() {
        applicationDao = EntityManager.getInstance().getMeetingApplicationDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        application = new MeetingApplication();
        DateFormat dateFormat= new DateFormat();
        application = applicationDao.queryBuilder().unique();
        if(application!=null){
            eTopic.setText(application.getTheme());
            eplace.setText(application.getPlace());
            if(application.getBegintime()==0){
                //startTime.setText("");
            }else{
                tstartTime.setText(dateFormat.LongtoDatemm(application.getBegintime()));
            }
            if(application.getEndtime()==0){
                //endTime.setText("");
            }else{
                tendTime.setText(dateFormat.LongtoDatemm(application.getEndtime()));
            }
            if(application.getAeid()!=null&&!application.getAeid().equals("")){
                View EvaluateView = View.inflate(MeetingActivity.this, R.layout.item_member, null);
                addlistView.addView(EvaluateView);
                final View childAt = addlistView.getChildAt(0);
                final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
                ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(application.getAeid())).unique();
                if(ci!=null){
                   tname = (TextView)childAt.findViewById(R.id.leave_type);
                   tname.setText(ci.getName());
                }else{
                    tname.setText("");
                }
                ImageView delete = (ImageView)childAt.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tname = (TextView)childAt.findViewById(R.id.leave_type);
                        Log.e(TAG,"确定删除"+tname.getText().toString()+"??");
                        addlistView.removeView(item);
                        approveNumberDao.deleteAll();
                        ApproveNumber employee = new ApproveNumber();
                        int h=0;
                        for(int j=0;j<eps.size();j++){
                            if(!eps.get(j).getId().equals(eps.get(0).getId())){
                                employee.setId(eps.get(j).getId());
                                approveNumberDao.insert(employee);
                            }
                        }
                    }
                });
            }
        }
        if(index==0){
            approveNumberDao.deleteAll();
        }
        if(index==1||index==2) {
            eps = approveNumberDao.queryBuilder().list();
            Log.e(TAG,"EPS="+eps);
        }

    }


    private void addMember() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        //MeetingApplication ap = new MeetingApplication();
        if(application!=null) {
            Log.e(TAG, tstartTime.getText().toString().trim() + "===========" + tendTime.getText().toString().trim());
            if (tstartTime.getText().toString().trim().equals("") || tstartTime.getText().toString().trim().equals("选择开始时间")) {

            } else {
                application.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim(), "yyyy-MM-dd hh:mm"));
            }
            if (tendTime.getText().toString().trim().equals("") || tendTime.getText().toString().trim().equals("选择结束时间")) {

            } else {
                application.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim(), "yyyy-MM-dd hh:mm"));
            }
            application.setTheme(eTopic.getText().toString().trim());
            application.setPlace(eplace.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);
        }

        LoadingDialog loadingDialog  = new LoadingDialog(MeetingActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if(contactInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                            DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                            departmentInfoDao.insert(departmentInfo);
                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().get("+j+").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }
                        }
                        Intent intent = new Intent(MeetingActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",4);
                        startActivity(intent);
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();

    }

    //添加审批人操作
    private void addPerson() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        MeetingApplication ap = new MeetingApplication();
        Log.e(TAG,tstartTime.getText().toString().trim()+"==========="+tendTime.getText().toString().trim());
        if(application!=null) {
            Log.e(TAG, tstartTime.getText().toString().trim() + "===========" + tendTime.getText().toString().trim());
            if (tstartTime.getText().toString().trim().equals("") || tstartTime.getText().toString().trim().equals("选择开始时间")) {

            } else {
                application.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim(), "yyyy-MM-dd hh:mm"));
            }
            if (tendTime.getText().toString().trim().equals("") || tendTime.getText().toString().trim().equals("选择结束时间")) {

            } else {
                application.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim(), "yyyy-MM-dd hh:mm"));
            }
            application.setTheme(eTopic.getText().toString().trim());
            application.setPlace(eplace.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);
        }

        LoadingDialog loadingDialog  = new LoadingDialog(MeetingActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if(contactInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                            DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                            departmentInfoDao.insert(departmentInfo);
                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().get("+j+").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }
                        }
                        Intent intent = new Intent(MeetingActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",0);
                        startActivity(intent);
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }
    private void save() throws ParseException {
        //Toast.makeText(getApplicationContext(), "你点击了保存", Toast.LENGTH_SHORT).show();

        DateFormat dateFormat = new DateFormat();
        if(TextUtils.isEmpty(tstartTime.getText().toString().trim())||TextUtils.isEmpty(tendTime.getText().toString().trim())
                ||TextUtils.isEmpty(eTopic.getText().toString().trim())||TextUtils.isEmpty(eplace.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            application.setTheme(eTopic.getText().toString().trim());
            application.setPlace(eplace.getText().toString().trim());
            application.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
            application.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
            if (eps==null) {
                Toast.makeText(getApplicationContext(), "请选择参会人员", Toast.LENGTH_SHORT).show();
            } else if(eps.size()==0){
                Toast.makeText(getApplicationContext(), "请选择参会人员", Toast.LENGTH_SHORT).show();
            } else if(application.getAeid()!=null&&application.getAeid().equals("")){
                Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
            } else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        MeetingApplicationService service = new MeetingApplicationService();
                        Log.e(TAG,application.getAeid());
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.submitApplication(s,application,eps.toString());
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            back();
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "添加成功!", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addMemberList.getChildCount(); i++) {
            final View childAt = addMemberList.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            final ImageView delete = (ImageView)childAt.findViewById(R.id.delete);

            final int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.leave_type);
                    Log.e(TAG,"确定删除"+tname.getText().toString()+"??");
                    addMemberList.removeView(item);
                    approveNumberDao.deleteAll();
                    ApproveNumber employee = new ApproveNumber();
                    int h=0;
                    for(int j=0;j<eps.size();j++){
                        if(!eps.get(j).getId().equals(eps.get(finalI).getId())){
                            employee.setId(eps.get(j).getId());
                            approveNumberDao.insert(employee);
                        }
                    }
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (eps==null) {
                /*Toast.makeText(OvertimeActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();*/
            } else {
                for(int i = 0;i <eps.size(); i ++){
                    View EvaluateView = View.inflate(MeetingActivity.this, R.layout.item_member, null);
                    addMemberList.addView(EvaluateView);
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
        for (i = 0; i < addMemberList.getChildCount(); i++) {
            View childAt = addMemberList.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.leave_type);

            ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(eps.get(i).getId())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }

        }
    }

    //年月日时分选择器
    public void onYearMonthDayTimePicker1(View view) {
        tendTime.setText("");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy", Locale.CHINA);// 输出北京时间
        SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.CHINA);//
        SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.CHINA);// 输出北京时间
        SimpleDateFormat HH = new SimpleDateFormat("HH", Locale.CHINA);// 输出北京时间
        SimpleDateFormat aa = new SimpleDateFormat("aa", Locale.CHINA);// 输出北京时间
        picker = new DateTimePicker(MeetingActivity.this, DateTimePicker.HOUR_24);
        picker.setDividerColor(Color.rgb(0, 178, 238));//设置分割线的颜色
        picker.setLabelTextColor(Color.GRAY);
        picker.setTopLineColor(Color.GRAY);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("年月日时分选择");
        int hour = Integer.parseInt(HH.format(date));
        Log.e("AM/PM=",aa.format(date));
        if(aa.format(date).equals("上午")||aa.format(date).equals("PM")){
            if(hour<12){
                switch (hour){
                    case 1:hour=13;break;
                    case 2:hour=14;break;
                    case 3:hour=15;break;
                    case 4:hour=16;break;
                    case 5:hour=17;break;
                    case 6:hour=18;break;
                    case 7:hour=19;break;
                    case 8:hour=20;break;
                    case 9:hour=21;break;
                    case 10:hour=22;break;
                    case 11:hour=23;break;
                }
            }
        }
        picker.setDateRangeStart(Integer.parseInt(yyyy.format(date)),Integer.parseInt(mm.format(date)),Integer.parseInt(dd.format(date)));
        picker.setTimeRangeStart(0,0);
        picker.setSelectedItem(
                Integer.parseInt(yyyy.format(date)),Integer.parseInt(mm.format(date)),Integer.parseInt(dd.format(date)),
                hour,date.getMinutes());
        picker.setDateRangeEnd(2025, 12, 31);
        picker.setTimeRangeEnd(23, 59);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tstartTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }


    //年月日时分选择器
    public void onYearMonthDayTimePicker2(View view) {
        if(!tstartTime.getText().toString().equals("")) {
            Log.e("starttime==", tstartTime.getText().toString());
            Log.e("starttime==", tstartTime.getText().toString().trim().substring(0, 4));
            Log.e("starttime==", tstartTime.getText().toString().trim().substring(5, 7));
            Log.e("starttime==", tstartTime.getText().toString().trim().substring(8, 10));
            Log.e("starttime==", String.valueOf(Integer.parseInt(tstartTime.getText().toString().trim().substring(11, 13)) + 1));
            //Log.e("starttime==",String.valueOf(Integer.parseInt(tstartTime.getText().toString().trim().substring(14,16))));
            DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
            picker.setDateRangeStart(Integer.parseInt(tstartTime.getText().toString().trim().substring(0, 4)), Integer.parseInt(tstartTime.getText().toString().trim().substring(5, 7)), Integer.parseInt(tstartTime.getText().toString().trim().substring(8, 10)));
            picker.setDateRangeEnd(2025, 12, 31);
            picker.setTimeRangeStart(0, 0);
            picker.setTimeRangeEnd(23, 59);
            int hour = Integer.parseInt(tstartTime.getText().toString().trim().substring(11, 13));
            if (hour < 23) {
                hour++;
            }
            picker.setSelectedItem(
                    Integer.parseInt(tstartTime.getText().toString().trim().substring(0, 4)),
                    Integer.parseInt(tstartTime.getText().toString().trim().substring(5, 7)),
                    Integer.parseInt(tstartTime.getText().toString().trim().substring(8, 10)),
                    hour,
                    Integer.parseInt(tstartTime.getText().toString().trim().substring(14, 16))
            );
            // picker.setTopLineColor(0x99FF0000);
            picker.setDividerColor(Color.rgb(0, 178, 238));//设置分割线的颜色
            picker.setLabelTextColor(Color.GRAY);  //年月日时分单位字体颜色
            picker.setTopLineColor(Color.GRAY);  //顶部横线颜色
            picker.setSubmitTextSize(16); //确定文字大小
            picker.setCancelTextSize(16); //取消文字大小
            picker.setTitleTextColor(Color.BLACK); //标题文字颜色
            picker.setTitleText("年月日时分选择");  //标题文字
            picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                @Override
                public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                    if (tstartTime.getText().toString().trim().substring(0, 4).equals(year)
                            && tstartTime.getText().toString().trim().substring(5, 7).equals(month)
                            && tstartTime.getText().toString().trim().substring(8, 10).equals(day)) {
                        if (Integer.parseInt(hour) <= Integer.parseInt(tstartTime.getText().toString().trim().substring(11, 13))) {
                            showToast("结束时间不得早于开始时间，请重新选择！");
                        } else {
                            tendTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        }
                    } else {
                        tendTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                    //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                }
            });
            picker.show();
        }else{
            showToast("请选择开始时间！");
        }
    }



    //重写showToast
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
