package com.oliveoa.view.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PersonalDetailsActivity extends AppCompatActivity {
    private ImageView back,call;
    private TextView tname,tid,tsex,ttel,tduty,tbirth,tmail,taddress;
    private ContactInfo contactInfo;
    private ImageView imgUser;
    private int index;
    private TextView tedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        contactInfo = getIntent().getParcelableExtra("ci");
        index = getIntent().getIntExtra("index",index);//通讯录0，登录信息1
        Log.e("ContactInfo=",contactInfo.toString());
        //隐藏状态栏和actionbar
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        call = (ImageView) findViewById(R.id.call);
        imgUser = (ImageView) findViewById(R.id.img_user);
        tname = (TextView)findViewById(R.id.person_name);
        tid = (TextView) findViewById(R.id.person_id);
        tsex = (TextView) findViewById(R.id.person_sex);
        ttel = (TextView) findViewById(R.id.tel);
        tduty = (TextView) findViewById(R.id.department_duty);
        tbirth = (TextView) findViewById(R.id.birth);
        tmail = (TextView) findViewById(R.id.mail);
        taddress = (TextView) findViewById(R.id.address);
        tedit = (TextView) findViewById(R.id.edit);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetailsActivity.this, TabLayoutBottomActivity.class);
                if(index==0) {
                    intent.putExtra("index", 2);
                }else{
                    intent.putExtra("index",3);
                }
                startActivity(intent);
                finish();
            }
        });

        tedit.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetailsActivity.this, PersonalDetailsEditActivity.class);
                intent.putExtra("ci",contactInfo);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });

        //呼叫
        final String [] item = {"呼叫"};
        call.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalDetailsActivity.this);
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                  @Override
                 public void onClick(DialogInterface dialog,int i) {
                              //Toast.makeText(PersonalDetailsActivity.this, "选择了"item[i], Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent();
                      intent.setAction(Intent.ACTION_DIAL);   //显示拨号面板, 并在拨号面板上将号码显示出来
                      intent.setData(Uri.parse("tel:"+contactInfo.getTel()));
                      startActivity(intent);

                  }
              });

                dialog.show();
            }
        });
    }

    private void initData() {
        tid.setText(contactInfo.getId());
        taddress.setText(contactInfo.getAddress());
        tbirth.setText(contactInfo.getBirth());
        tmail.setText(contactInfo.getEmail());
        tsex.setText(contactInfo.getSex());
        ttel.setText(contactInfo.getTel());
        tname.setText(contactInfo.getName());
        if(contactInfo.getSex().equals("男")){
            imgUser.setImageResource(R.drawable.boy);
        }else{
            imgUser.setImageResource(R.drawable.user_pic);
        }

        DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        DepartmentInfo departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(contactInfo.getDcid())).unique();
        if(departmentInfo!=null) {
            DutyInfoDao dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
            DutyInfo dutyInfo = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(contactInfo.getPcid())).unique();
            if (dutyInfo != null) {
                tduty.setText(departmentInfo.getName()+""+dutyInfo.getName());
            }
        }
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
