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
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
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
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

public class LeaveActivity extends AppCompatActivity {

    private ImageView back,save;
    private TextView tstartTime,tendTime,treason,taddPerson,tleaveType;
    private TextView tnum,tname; //approve_item信息，tnum为第几审批人，tname为审批人名称

    private DateTimePicker picker;
    private View textAreaView;
    private EditText treson;
    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private LeaveApplicationDao applicationDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private LeaveApplication application;
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        index = getIntent().getIntExtra("index",index);//选择1，列表0
        Log.e("IDDEX=", String.valueOf(index));

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);

        tleaveType = (TextView) findViewById(R.id.leave_type);
        tstartTime = (TextView) findViewById(R.id.start);
        tendTime = (TextView) findViewById(R.id.end);
        taddPerson = (TextView) findViewById(R.id.person_add);
        addlistView = (LinearLayout) findViewById(R.id.approve_list);

        textAreaView = (LinesEditView)findViewById(R.id.reason);
        treson = textAreaView.findViewById(R.id.id_et_input);

        initData();

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
                if(index==1){
                    if(eps!=null){
                        approveNumberDao.deleteAll();
                    }
                }
                Intent intent = new Intent(LeaveActivity.this, MainApplicationActivity.class);
                startActivity(intent);
                finish();
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
        if(index==1){
            addViewItem(null);
        }

    }

    private void initData() {
        applicationDao = EntityManager.getInstance().getLeaveApplicationInfo();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        application = new LeaveApplication();
        DateFormat dateFormat= new DateFormat();
        if(index==1) {
            eps = approveNumberDao.queryBuilder().list();
            Log.e(TAG,"EPS="+eps);
            application = applicationDao.queryBuilder().unique();
            if(application!=null){
                treson.setText(application.getReason());
                if(application.getType()!=0){
                    switch (application.getType()){
                        case 1:tleaveType.setText("事假");break;
                        case 2:tleaveType.setText("病假");break;
                        case 3:tleaveType.setText("婚假");break;
                        case 4:tleaveType.setText("丧假");break;
                        case 5:tleaveType.setText("公假");break;
                        case 6:tleaveType.setText("年假");break;
                        case 7:tleaveType.setText("产假");break;
                        case 8:tleaveType.setText("护理假");break;
                        case 9:tleaveType.setText("工伤假");break;
                        default:break;
                    }
                }
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
            }
        }
    }
    //添加审批人操作
    private void addPerson() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        LeaveApplication ap = new LeaveApplication();
        Log.e(TAG,tstartTime.getText().toString().trim()+"==========="+tendTime.getText().toString().trim());
        if(tstartTime.getText().toString().trim().equals("")||tstartTime.getText().toString().trim().equals("选择开始时间")){

        }else{
            ap.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim(),"yyyy-MM-dd hh:mm"));
        }
        if(tendTime.getText().toString().trim().equals("")||tendTime.getText().toString().trim().equals("选择结束时间")){

        }else{
            ap.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim(),"yyyy-MM-dd hh:mm"));
        }
        if(!tleaveType.getText().toString().trim().equals("")){
            switch (tleaveType.getText().toString().trim()){
                case "事假":ap.setType(1);break;
                case "病假":ap.setType(2);break;
                case "婚假":ap.setType(3);break;
                case "丧假":ap.setType(4);break;
                case "公假":ap.setType(5);break;
                case "年假":ap.setType(6);break;
                case "产假":ap.setType(7);break;
                case "护理假":ap.setType(8);break;
                case "工伤假":ap.setType(9);break;
                default:break;
            }
        }
        ap.setReason(treson.getText().toString().trim());
        Log.e(TAG,ap.toString());
        applicationDao.deleteAll();
        applicationDao.insert(ap);

        LoadingDialog loadingDialog  = new LoadingDialog(LeaveActivity.this,"正在加载数据",true);
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
                        Intent intent = new Intent(LeaveActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",2);
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
                ||TextUtils.isEmpty(treson.getText().toString().trim())||TextUtils.isEmpty(tleaveType.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
                switch (tleaveType.getText().toString().trim()){
                    case "事假":application.setType(1);break;
                    case "病假":application.setType(2);break;
                    case "婚假":application.setType(3);break;
                    case "丧假":application.setType(4);break;
                    case "公假":application.setType(5);break;
                    case "年假":application.setType(6);break;
                    case "产假":application.setType(7);break;
                    case "护理假":application.setType(8);break;
                    case "工伤假":application.setType(9);break;
                    default:break;
                }
            application.setReason(treson.getText().toString().trim());
            application.setBegintime(dateFormat.stringToLong(tstartTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
            application.setEndtime(dateFormat.stringToLong(tendTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
            if (eps==null) {
                Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
            } else if(eps.size()==0){
                Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        LeaveApplicationService service = new LeaveApplicationService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.sentlapplication(s,application,eps.toString());
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "添加成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
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
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            final ImageView delete = (ImageView)childAt.findViewById(R.id.delete);

            final int finalI = i;
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
                View EvaluateView = View.inflate(LeaveActivity.this, R.layout.item_member, null);
                addlistView.addView(EvaluateView);
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
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
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
        picker = new DateTimePicker(LeaveActivity.this, DateTimePicker.HOUR_24);
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
        Log.e("starttime==",tstartTime.getText().toString());
        Log.e("starttime==",tstartTime.getText().toString().trim().substring(0,4));
        Log.e("starttime==",tstartTime.getText().toString().trim().substring(5,7));
        Log.e("starttime==",tstartTime.getText().toString().trim().substring(8,10));
        Log.e("starttime==", String.valueOf(Integer.parseInt(tstartTime.getText().toString().trim().substring(11,13))+1));
        //Log.e("starttime==",String.valueOf(Integer.parseInt(tstartTime.getText().toString().trim().substring(14,16))));
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(Integer.parseInt(tstartTime.getText().toString().trim().substring(0,4)), Integer.parseInt(tstartTime.getText().toString().trim().substring(5,7)), Integer.parseInt(tstartTime.getText().toString().trim().substring(8,10)));
        picker.setDateRangeEnd(2025, 12, 31);
        picker.setTimeRangeStart(0,0);
        picker.setTimeRangeEnd(23, 59);
        int hour = Integer.parseInt(tstartTime.getText().toString().trim().substring(11,13));
        if(hour<23){
            hour++;
        }
        picker.setSelectedItem(
                Integer.parseInt(tstartTime.getText().toString().trim().substring(0,4)),
                Integer.parseInt(tstartTime.getText().toString().trim().substring(5,7)),
                Integer.parseInt(tstartTime.getText().toString().trim().substring(8,10)),
                hour,
                Integer.parseInt(tstartTime.getText().toString().trim().substring(14,16))
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
                if (tstartTime.getText().toString().trim().substring(0,4).equals(year)
                        &&tstartTime.getText().toString().trim().substring(5,7).equals(month)
                        &&tstartTime.getText().toString().trim().substring(8,10).equals(day)){
                    if(Integer.parseInt(hour)<=Integer.parseInt(tstartTime.getText().toString().trim().substring(11,13))){
                        showToast("结束时间不得早于开始时间，请重新选择！");
                    }else{
                        tendTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                    }
                }else{
                    tendTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                }
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    //单项选择器，请假类型选择
    public void onOptionPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "事假","病假","婚假","丧假","公假","年假","产假","护理假","工伤假"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("请假类型选择");
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(18);
        picker.setTitleTextSize(16);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tleaveType.setText(item);
                application.setType(index+1);
                Log.e(TAG,item+"****"+index+1);
                //showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
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
