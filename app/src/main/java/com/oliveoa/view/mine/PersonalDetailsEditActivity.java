package com.oliveoa.view.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.UserInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.UserLoginJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentAndDuty;
import com.oliveoa.pojo.UserInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

import static com.oliveoa.util.Validator.isEmail;
import static com.oliveoa.util.Validator.isMobile;

public class PersonalDetailsEditActivity extends AppCompatActivity {
    private EditText ename, etel, email, eaddress;
    private TextView tbirth, tdepart;
    private ImageView back, ivdepart,isave;
    private TextView tsex;
    private ContactInfo contactInfo;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private DepartmentAndDuty departmentAndDuty;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_edit);
        contactInfo = getIntent().getParcelableExtra("ci");
        index = getIntent().getIntExtra("index",index);//详情0，职务选择1
        Log.e(getLocalClassName(),contactInfo.toString());
        initViews();
    }

    public void initViews() {
        back = (ImageView) findViewById(R.id.iback);
        ename = (EditText) findViewById(R.id.name);
        tsex = (TextView) findViewById(R.id.sex);
        etel = (EditText) findViewById(R.id.tel);
        email = (EditText) findViewById(R.id.mail);
        eaddress = (EditText) findViewById(R.id.address);
        tbirth = (TextView) findViewById(R.id.birth);
        //tdepart = (TextView) findViewById(R.id.dpcid);
        //ivdepart = (ImageView) findViewById(R.id.dpdtselect);
        isave = (ImageView)findViewById(R.id.isave);

        initData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog  = new LoadingDialog(PersonalDetailsEditActivity.this,"正在加载数据",true);
                loadingDialog.show();
                back();
            }
        });

        isave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        //选择部门职务
      /*  ivdepart.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    selectdpdt();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });*/


    }

    private void save() {
        if(TextUtils.isEmpty(eaddress.getText().toString().trim())||TextUtils.isEmpty(email.getText().toString().trim())||TextUtils.isEmpty(ename.getText().toString().trim())
                ||TextUtils.isEmpty(etel.getText().toString().trim())||TextUtils.isEmpty(tbirth.getText().toString().trim())
                ||TextUtils.isEmpty(tsex.getText().toString())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if(!isMobile(etel.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "联系电话格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isEmail(email.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "邮箱格式输入错误！请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            contactInfo.setTel(etel.getText().toString().trim());
            contactInfo.setSex(tsex.getText().toString().trim());
            contactInfo.setName(ename.getText().toString().trim());
            contactInfo.setEmail(email.getText().toString().trim());
            contactInfo.setAddress(eaddress.getText().toString().trim());
            contactInfo.setBirth(tbirth.getText().toString().trim());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                   UserInfoService service = new UserInfoService();
                    StatusAndMsgJsonBean infoJsonBean = service.updateuserinfo(s,contactInfo);
                    Log.d("update" ,infoJsonBean.getMsg()+"");

                    if (infoJsonBean.getStatus() == 0) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), infoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                }
            }).start();
        }

        }

    private void back() {
        new Thread(new Runnable() {
        @Override
        public void run() {
            //读取SharePreferences的cookies
            SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
            String s = pref.getString("sessionid","");

            UserInfoDao userInfoDao = EntityManager.getInstance().getUserInfoDao();
            ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
            //获取用户数据
            UserInfoService userInfoService = new UserInfoService();
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
                userInfoDao.deleteAll();
                userInfoDao.insert(userInfo);
            }else{
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(),userLoginJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
            ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);
            if(contactHttpResponseObject.getStatus()==0){
                ArrayList<ContactJsonBean> contactJsonBean = contactHttpResponseObject.getData();
                contactInfoDao.deleteAll();
                for(int i =0;i<contactJsonBean.size();i++){
                    for(int j=0;j<contactJsonBean.get(i).getEmpContactList().size();j++){
                        contactInfoDao.insert(contactJsonBean.get(i).getEmpContactList().get(j).getEmployee());
                    }
                }

                Intent intent = new Intent(PersonalDetailsEditActivity.this, PersonalDetailsActivity.class);
                intent.putExtra("ci",userLoginJsonBean.getData());
                intent.putExtra("index",2);
                startActivity(intent);
                finish();
            }else{
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
    }).start();

    }

    public void initData() {
        eaddress.setText(contactInfo.getAddress());
        tbirth.setText(contactInfo.getBirth());
        email.setText(contactInfo.getEmail());
        tsex.setText(contactInfo.getSex());
        etel.setText(contactInfo.getTel());
        ename.setText(contactInfo.getName());


      /*  DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        DepartmentInfo departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(contactInfo.getDcid())).unique();
        if(departmentInfo!=null) {
            DutyInfoDao dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
            DutyInfo dutyInfo = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(contactInfo.getPcid())).unique();
            if (dutyInfo != null) {
                tdepart.setText(departmentInfo.getName()+""+dutyInfo.getName());
            }
        }*/

    }

    //选择性别
    public void onSexSelectPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "男","女"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("性别选择");
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
                tsex.setText(item);
                //showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    //选择出生日期
    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTitleText("出生日期选择");
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2025, 12, 31);
        picker.setRangeStart(1900, 1, 1);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tbirth.setText(year + "-" + month + "-" + day);
                // showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
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
        Timer tExit ;
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
