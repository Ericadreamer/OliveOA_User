package com.oliveoa.view.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.oliveoa.controller.UserInfoService;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.view.LoginActivity;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isPassword;

public class PasswordEditActivity extends AppCompatActivity implements OnClickListener{
    private ImageView back,ivold,ivnew,ivpwd; //返回，原密码，新密码，确认密码
    private EditText eold,enew,epwd;
    private Button btn;
    private Boolean showPassword = true;
    private  String comfirmpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);

        initView();
        initData();
        setupEvents();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        eold = (EditText) findViewById(R.id.et_password_old);
        enew = (EditText) findViewById(R.id.et_password_new);
        epwd = (EditText) findViewById(R.id.et_password);
        ivold = (ImageView) findViewById(R.id.iv_see_password_old);
        ivnew = (ImageView) findViewById(R.id.iv_see_password_new);
        ivpwd = (ImageView) findViewById(R.id.iv_see_password);
        btn = (Button) findViewById(R.id.btn);

        ivold.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));
        ivnew.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));
        ivpwd.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));


        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordEditActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",3);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void initData() {

    }

    private void setupEvents() {
        ivold.setOnClickListener(this);
        ivnew.setOnClickListener(this);
        ivpwd.setOnClickListener(this);

    }

    /**
     * 设置密码可见和不可见的相互转换
     */

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_see_password_old:
                setOldPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
            case R.id.iv_see_password_new:
                setNewPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
            case R.id.iv_see_password:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;

                default:
                    break;

        }
    }

    //原密码
    private void setOldPasswordVisibility() {
        if (showPassword) {  //显示密码
            ivold.setImageDrawable(getResources().getDrawable(R.drawable.ic_see_pass));
          /*  eold.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            eold.setSelection(eold.getText().toString().length());*/
            eold.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword = false;
        }
        else {  //隐藏密码
            ivold.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));
           /* eold.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            eold.setSelection(eold.getText().toString().length());*/
            eold.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword = true;
        }
    }

    //新密码
    private void setNewPasswordVisibility() {
        if (showPassword) {  //显示密码
            ivnew.setImageDrawable(getResources().getDrawable(R.drawable.ic_see_pass));
            enew.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword = !showPassword;
        }
        else {  //隐藏密码
            ivnew.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));
            enew.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword = !showPassword;
        }

    }

    //确认密码
    private void setPasswordVisibility() {
        if (showPassword) {  //显示密码
            ivpwd.setImageDrawable(getResources().getDrawable(R.drawable.ic_see_pass));
            epwd.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPassword = !showPassword;
        }
        else {  //隐藏密码
            ivpwd.setImageDrawable(getResources().getDrawable(R.drawable.ic_nosee_pass));
            epwd.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPassword = !showPassword;
        }

    }

    public void save() {
        final String originpwd = eold.getText().toString().trim();
        String newpwd = enew.getText().toString().trim();
        comfirmpwd = epwd.getText().toString().trim();

        if (TextUtils.isEmpty(originpwd)||TextUtils.isEmpty(newpwd)||TextUtils.isEmpty(comfirmpwd)) {
            Toast.makeText(getApplicationContext(), "信息不得为空！请检查输入信息", Toast.LENGTH_SHORT).show();
        } else if(!newpwd.equals(comfirmpwd)){
            Toast.makeText(getApplicationContext(), "新密码与确认密码输入不一致！请重新输入", Toast.LENGTH_SHORT).show();
        } /*else if(!isPassword(comfirmpwd)){
            Toast.makeText(getApplicationContext(), "密码格式输入错误！请按错提示输入", Toast.LENGTH_SHORT).show();
        } */else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    UserInfoService userInfoService = new UserInfoService();
                    StatusAndMsgJsonBean statusAndMsgJsonBean = userInfoService.updatepassword(s,originpwd,comfirmpwd);
                    Log.d("update" ,statusAndMsgJsonBean.getMsg()+"");

                    if (statusAndMsgJsonBean.getStatus() == 0) {
                        pref.edit().remove("sessionid").commit();//移除指定数值
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "修改成功！请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PasswordEditActivity.this, LoginActivity.class);
                        intent.putExtra("index",3);
                        startActivity(intent);
                        finish();
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
