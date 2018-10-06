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
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cn.qqtheme.framework.picker.DateTimePicker;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class OvertimeActivity extends AppCompatActivity {

    private ImageView back,save;
    private TextView startTime,endTime,addPerson;
    private TextView tnum,tname; //approve_item信息，tnum为第几审批人，tname为审批人名称
    private DateTimePicker picker;

    private EditText treson;
    private View textAreaView;
    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private OvertimeApplicationDao overtimeApplicationDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private OvertimeApplication overtimeApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime);
        index = getIntent().getIntExtra("index",index);//选择1，列表0
        Log.e("IDDEX=", String.valueOf(index));
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        textAreaView = (LinesEditView)findViewById(R.id.reason);
        treson = textAreaView.findViewById(R.id.id_et_input);
        startTime = (TextView) findViewById(R.id.start);
        endTime = (TextView) findViewById(R.id.end);
        addPerson = (TextView) findViewById(R.id.person_add);
        addlistView = (LinearLayout) findViewById(R.id.approve_list);

        initData();

        LinesEditView linesEditView = new LinesEditView(OvertimeActivity.this);
        String test = linesEditView.getContentText();

        //+添加点击事件
        addPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
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
        if(index==1){
            addViewItem(null);
        }


    }

    private void back() {
        if(index==1){
            if(eps!=null){
                approveNumberDao.deleteAll();
            }
        }
        Intent intent = new Intent(OvertimeActivity.this, MainApplicationActivity.class);
        startActivity(intent);
        finish();
    }

    private void initData() {
          overtimeApplicationDao = EntityManager.getInstance().getOvertimeApplicationInfo();
          contactInfoDao = EntityManager.getInstance().getContactInfo();
          approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
          overtimeApplication = new OvertimeApplication();
          DateFormat dateFormat= new DateFormat();
          if(index==1) {
              eps = approveNumberDao.queryBuilder().list();
              Log.e(TAG,"EPS="+eps);
              overtimeApplication = overtimeApplicationDao.queryBuilder().unique();
              if(overtimeApplication!=null){
                  treson.setText(overtimeApplication.getReason());
                  if(overtimeApplication.getBegintime()==0){
                      //startTime.setText("");
                  }else{
                      startTime.setText(dateFormat.LongtoDatemm(overtimeApplication.getBegintime()));
                  }
                  if(overtimeApplication.getEndtime()==0){
                      //endTime.setText("");
                  }else{
                      endTime.setText(dateFormat.LongtoDatemm(overtimeApplication.getEndtime()));
                  }
              }
          }
    }
    //添加审批人操作
    private void addPerson() throws ParseException {
        DateFormat dateFormat = new DateFormat();
        OvertimeApplication oa = new OvertimeApplication();
        Log.e(TAG,startTime.getText().toString().trim()+"==========="+endTime.getText().toString().trim());
        if(startTime.getText().toString().trim().equals("")|| startTime.getText().toString().trim().equals("选择开始时间")){

        }else{
          oa.setBegintime(dateFormat.stringToLong(startTime.getText().toString().trim(),"yyyy-MM-dd hh:mm"));
        }
        if(endTime.getText().toString().trim().equals("")||endTime.getText().toString().trim().equals("选择结束时间")){

        }else{
            oa.setEndtime(dateFormat.stringToLong(endTime.getText().toString().trim(),"yyyy-MM-dd hh:mm"));
        }
        oa.setReason(treson.getText().toString().trim());
        Log.e(TAG,oa.toString());
        overtimeApplicationDao.deleteAll();
        overtimeApplicationDao.insert(oa);

        LoadingDialog loadingDialog  = new LoadingDialog(OvertimeActivity.this,"正在加载数据",true);
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
                                   Intent intent = new Intent(OvertimeActivity.this, SelectPersonApprovingActivity.class);
                                   intent.putExtra("index",1);
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
        if(TextUtils.isEmpty(startTime.getText().toString().trim())||TextUtils.isEmpty(endTime.getText().toString().trim())
                ||TextUtils.isEmpty(treson.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            overtimeApplication.setReason(treson.getText().toString().trim());
            overtimeApplication.setBegintime(dateFormat.stringToLong(startTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
            overtimeApplication.setEndtime(dateFormat.stringToLong(endTime.getText().toString().trim()+":00","yyyy-MM-dd HH:mm:ss"));
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

                        OvertimeApplictionService service = new OvertimeApplictionService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.sentotapplication(s,overtimeApplication,eps.toString());
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            back();
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_LONG).show();
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
                View EvaluateView = View.inflate(OvertimeActivity.this, R.layout.item_member, null);
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
        endTime.setText("");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy", Locale.CHINA);// 输出北京时间
        SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.CHINA);//
        SimpleDateFormat dd = new SimpleDateFormat("dd", Locale.CHINA);// 输出北京时间
        SimpleDateFormat HH = new SimpleDateFormat("HH", Locale.CHINA);// 输出北京时间
        SimpleDateFormat aa = new SimpleDateFormat("aa", Locale.CHINA);// 输出北京时间
        picker = new DateTimePicker(OvertimeActivity.this, DateTimePicker.HOUR_24);
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
                startTime.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }



    //年月日时分选择器
    public void onYearMonthDayTimePicker2(View view) {
        if(!startTime.getText().toString().equals("")) {
            Log.e("starttime==", startTime.getText().toString());
            Log.e("starttime==", startTime.getText().toString().trim().substring(0, 4));
            Log.e("starttime==", startTime.getText().toString().trim().substring(5, 7));
            Log.e("starttime==", startTime.getText().toString().trim().substring(8, 10));
            Log.e("starttime==", String.valueOf(Integer.parseInt(startTime.getText().toString().trim().substring(11, 13)) + 1));
            //Log.e("starttime==",String.valueOf(Integer.parseInt(startTime.getText().toString().trim().substring(14,16))));
            DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
            picker.setDateRangeStart(Integer.parseInt(startTime.getText().toString().trim().substring(0, 4)), Integer.parseInt(startTime.getText().toString().trim().substring(5, 7)), Integer.parseInt(startTime.getText().toString().trim().substring(8, 10)));
            picker.setDateRangeEnd(2025, 12, 31);
            picker.setTimeRangeStart(0, 0);
            picker.setTimeRangeEnd(23, 59);
            int hour = Integer.parseInt(startTime.getText().toString().trim().substring(11, 13));
            if (hour < 23) {
                hour++;
            }
            picker.setSelectedItem(
                    Integer.parseInt(startTime.getText().toString().trim().substring(0, 4)),
                    Integer.parseInt(startTime.getText().toString().trim().substring(5, 7)),
                    Integer.parseInt(startTime.getText().toString().trim().substring(8, 10)),
                    hour,
                    Integer.parseInt(startTime.getText().toString().trim().substring(14, 16))
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
                    if (startTime.getText().toString().trim().substring(0, 4).equals(year)
                            && startTime.getText().toString().trim().substring(5, 7).equals(month)
                            && startTime.getText().toString().trim().substring(8, 10).equals(day)) {
                        if (Integer.parseInt(hour) <= Integer.parseInt(startTime.getText().toString().trim().substring(11, 13))) {
                            showToast("结束时间不得早于开始时间，请重新选择！");
                        } else {
                            endTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        }
                    } else {
                        endTime.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
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
