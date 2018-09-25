package com.oliveoa.view.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentAndDuty;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.mine.PersonalDetailsEditActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DutySelectActivity extends AppCompatActivity {


    private List<DutyInfo> dutyInfos;
    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDTlistView;
    private String dtname;
    private TextView tvname;
    private DutyInfoDao dutyInfoDao;
    private int index;
    private DutyInfo temp;
    private DepartmentAndDuty dpdt;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_select);

        dutyInfos= getIntent().getParcelableArrayListExtra("alldt");
        index = getIntent().getIntExtra("index",index);//调岗0，招聘1,个人资料修改2

        Log.d(TAG,"index="+index+"dutyInfos="+dutyInfos.toString());
        initData();
    }

    public void initData(){
        if(index==0||index==1) {
            departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
            try {
                dpdt = departmentAndDutyDao.queryBuilder().unique();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "获取相关数据失败", Toast.LENGTH_SHORT).show();
            }
        }
        if(index==2){
            contactInfoDao = EntityManager.getInstance().getContactInfo();
            contactInfo = contactInfoDao.queryBuilder().unique();
        }

        initview();
    }

    public void initview(){
        back =(ImageView)findViewById(R.id.null_back);
        addDTlistView = (LinearLayout)findViewById(R.id.duty_list);

        // 默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DutySelectActivity.this);
                dialog.setTitle("提示");
                if(index==0) {
                    dialog.setMessage("是否确定退出选择,直接返回调岗申请创建页面？");
                }
                if(index==1) {
                    dialog.setMessage("是否确定退出选择,直接返回招聘申请创建页面？");
                }
                if(index==2) {
                    dialog.setMessage("是否确定退出选择,直接返回个人资料修改创建页面？");
                }
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        back();

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

    }

    private void back() {
        if(index==0) {
            dpdt.setDcid("");
            dpdt.setPcid("");
            dpdt.setDpname("");
            dpdt.setPname("");
            departmentAndDutyDao.insert(dpdt);
            Log.e(TAG,departmentAndDutyDao.queryBuilder().unique().toString());
            Intent intent = new Intent(DutySelectActivity.this, AdjustPostActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==1) {
            dpdt.setDcid("");
            dpdt.setPcid("");
            dpdt.setDpname("");
            dpdt.setPname("");
            departmentAndDutyDao.insert(dpdt);
            Log.e(TAG,departmentAndDutyDao.queryBuilder().unique().toString());
            Intent intent = new Intent(DutySelectActivity.this, RecruitmentActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==2) {
            if(contactInfo!=null){
                contactInfo.setDcid("");
                contactInfo.setPcid("");
                contactInfoDao.deleteAll();
                contactInfoDao.insert(contactInfo);
            }
            Intent intent = new Intent(DutySelectActivity.this, PersonalDetailsEditActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }

    }

    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDTlistView.getChildCount(); i++) {
            final View childAt = addDTlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.select_item);

            final TextView tname = (TextView) childAt.findViewById(R.id.dname);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG,tname.getText().toString());
                    if(index==0)  {  //调岗申请选择
                        jobTransfer(tname);
                    }
                    if(index==1)  {  //招聘申请选择
                        recruitment(tname);
                    }
                    if(index==2)  {  //个人资料修改选择
                        userinfo(tname);
                    }
                }
            });
        }
    }
    /**
     *   MethodName  userinfo(TextView tname)
     *   Description 个人资料修改
     *   @Author Erica
     */
    private void userinfo(TextView tname) {
        for (int i = 0; i < dutyInfos.size(); i++) {
            Log.e(TAG, dutyInfos.get(i).getName() + "," + tname.getText().toString());
            if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                if(contactInfo!=null) {
                    contactInfo.setPcid(dutyInfos.get(i).getDcid());
                    Log.e(TAG, dutyInfos.get(i).getPcid());
                }
                break;
            }
        }
        contactInfoDao.deleteAll();
        contactInfoDao.insert(contactInfo);
        Intent intent = new Intent(DutySelectActivity.this,PersonalDetailsEditActivity.class);
        intent.putExtra("index", 1);
        intent.putExtra("ci",contactInfo);
        startActivity(intent);
        finish();
    }
    /**
     *   MethodName  recruitment(TextView tname)
     *   Description 招聘申请
     *   @Author Erica
     */
    private void recruitment(TextView tname) {
        for (int i = 0; i < dutyInfos.size(); i++) {
            Log.e(TAG, dutyInfos.get(i).getName() + "," + tname.getText().toString());
            if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                dpdt.setPcid( dutyInfos.get(i).getPcid());
                dpdt.setPname( dutyInfos.get(i).getName());
                Log.e(TAG, dutyInfos.get(i).getName());
                Log.e(TAG, dutyInfos.get(i).getPcid());
                break;
            }
        }
        departmentAndDutyDao.deleteAll();
        departmentAndDutyDao.insert(dpdt);
        Intent intent = new Intent(DutySelectActivity.this, RecruitmentActivity.class);
        intent.putExtra("index", 1);
        startActivity(intent);
        finish();
    }


    /**
     *   MethodName  jobTransfer(TextView tname)
     *   Description 调岗申请
     *   @Author Erica
     */

    private void jobTransfer(TextView tname) {
        for (int i = 0; i < dutyInfos.size(); i++) {
            Log.e(TAG, dutyInfos.get(i).getName() + "," + tname.getText().toString());
            if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                dpdt.setPcid( dutyInfos.get(i).getPcid());
                dpdt.setPname( dutyInfos.get(i).getName());
                Log.e(TAG, dutyInfos.get(i).getName());
                Log.e(TAG, dutyInfos.get(i).getPcid());
                break;
            }
        }
        departmentAndDutyDao.deleteAll();
        departmentAndDutyDao.insert(dpdt);
        Intent intent = new Intent(DutySelectActivity.this, AdjustPostActivity.class);
        intent.putExtra("index", 1);
        startActivity(intent);
        finish();
    }





    //添加ViewItem
    private void addViewItem(View view) {
        if (dutyInfos!=null) {//如果有职务则按数组大小加载布局
            for(int i = 0;i <dutyInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_duty_selectitem, null);
                addDTlistView.addView(hotelEvaluateView);
                InitDataViewItem();
            }
            if(dutyInfos.size()==0){

            }else{
                sortHotelViewItem();
            }

        }
    }

/*
     * Item加载数据
    */

    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addDTlistView.getChildCount(); i++) {
            View childAt = addDTlistView.getChildAt(i);
            tvname = (TextView)childAt.findViewById(R.id.dname);
            tvname.setText(dutyInfos.get(i).getName());
        }

        Log.e(TAG, "职务名称：" + tvname.getText().toString());
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

     /*
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
