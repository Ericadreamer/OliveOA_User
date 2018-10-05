package com.oliveoa.view.documentmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

public class NuclearAdviseActivity extends AppCompatActivity {
    private ImageView back,save;
    private TextView ttitle,tcontent,tdeid,tvisapproval;
    private EditText eadvise;
    private Button btn_download;
    private OfficialDocument officialDocument;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuclear_advise);

        officialDocument = getIntent().getParcelableExtra("info");
        Log.i(TAG,officialDocument.toString());
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.save);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        eadvise = (EditText) findViewById(R.id.nuclear_advise);
        tvisapproval =(TextView) findViewById(R.id.isapproval);
        tdeid = (TextView)findViewById(R.id.deid);
        btn_download = (Button) findViewById(R.id.download);

        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuclearAdviseActivity.this, NuclearDraftActivity.class);
                intent.putExtra("index", 0);
                startActivity(intent);
                finish();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NuclearAdviseActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否下载附件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                save();
            }
        });

    }

    public void download() {

    }

    public void initData() {
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        contactInfo = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(officialDocument.getDraftEid())).unique();
        if(contactInfo!=null){
            tdeid.setText(contactInfo.getName());
        }else{
            tdeid.setText("");
        }

        tcontent.setText(officialDocument.getContent());
        ttitle.setText(officialDocument.getTitle());
//        switch (officialDocument.getNuclearDraftIsapproved()) {
//                case -1:tvisapproval.setText("不同意");break;
//                case 1:tvisapproval.setText("同意");break;
//                case 0:tvisapproval.setText("正在审核");break;
//                case -2:tvisapproval.setText("未审核");break;
//                default:break;
//        }

    }

    public void save() {
        if(TextUtils.isEmpty(eadvise.getText().toString().trim())||TextUtils.isEmpty(tvisapproval.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            if(tvisapproval.getText().toString().trim().equals("正在审核")||tvisapproval.getText().toString().trim().equals("未审核")){
                Toast.makeText(getApplicationContext(), "请选择是否同意此工作！", Toast.LENGTH_SHORT).show();
            }else{
                switch (tvisapproval.getText().toString().trim()){
                    case "同意":officialDocument.setNuclearDraftIsapproved(1);break;
                    case "不同意":officialDocument.setNuclearDraftIsapproved(-1);break;
                    default:break;
                }
                officialDocument.setNuclearDraftOpinion(eadvise.getText().toString().trim());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        DocumentService service = new DocumentService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = service.documentNuclear(s,officialDocument);
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "核稿成功！点击返回键返回待核稿工作列表", Toast.LENGTH_SHORT).show();
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
    //单项选择器
    public void onOptionPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "同意","不同意"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("核稿意见选择");
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
                tvisapproval.setText(item);
                //showToast("index=" + index + ", item=" + item);
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
