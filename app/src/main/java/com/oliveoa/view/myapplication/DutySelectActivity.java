package com.oliveoa.view.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_select);

        dutyInfos= getIntent().getParcelableArrayListExtra("alldt");
        index = getIntent().getIntExtra("index",index);///编辑职务0，添加职务1，编辑员工2，添加员工3，编辑物品4.添加物品5

        Log.d(TAG,"index="+index+"dutyInfos="+dutyInfos.toString());
        initData();
    }

    public void initData(){
        dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
        if(index==0||index==1){
            temp = dutyInfoDao.queryBuilder().unique();
            Log.d(TAG,"temp="+temp.toString());
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
                    dialog.setMessage("是否确定退出选择,直接返回职务编辑页面？");
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
       /*  Intent intent = new Intent(DutySelectActivity.this,EditDutyInfoActivity.class);
         intent.putExtra("dpname","无" );//部门名
         intent.putExtra("dtname","无" );//上级职务名
         intent.putExtra("index", 1);
         intent.putExtra("dt",temp);
         startActivity(intent);
         finish();*/
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
                    if (index == 0) {  //编辑职务选择
                        //EditDtSelect(tname);
                    }
                }
            });
        }
    }

/**
     *   MethodName  EditPpSelect(TextView tname)
     *   Description 编辑物品选择
     *   @Author Erica
     */

     private void EditPpSelect(final TextView tname) {
        /* if (pp != null) {
             Log.e(TAG, pp.toString());
             String dcid = null;
             for (int i = 0; i < dutyInfos.size(); i++) {
                 Log.e(TAG, dutyInfos.get(i).getName() + "," + tname.getText().toString());
                 if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                     pp.setPcid(dutyInfos.get(i).getPcid());
                     dcid = dutyInfos.get(i).getDcid();
                     Log.e(TAG, dutyInfos.get(i).getPcid());
                     break;
                 }
             }
             propertiesInfoDao.deleteAll();
             propertiesInfoDao.insert(pp);
             Log.e(TAG, "propertiesInfoDao.queryBuilder().unique().toString()======" + propertiesInfoDao.queryBuilder().unique().toString());
             final String finalDcid = dcid;
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                     String s = pref.getString("sessionid", "");

                     DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                     OneDepartmentInfoJsonBean onedepartmentInfoJsonBean = departmentInfoService.getdepartmentinfo(s, finalDcid);
                     if (onedepartmentInfoJsonBean.getStatus() == 0) {
                         Intent intent = new Intent(DutySelectActivity.this, EditGoodsActivity.class);
                         intent.putExtra("index", 1);
                         intent.putExtra("dname", onedepartmentInfoJsonBean.getData().getName());
                         intent.putExtra("pname", tname.getText().toString());
                         intent.putExtra("pp",pp);
                         startActivity(intent);
                         finish();

                     } else {
                         Looper.prepare();//解决子线程弹toast问题
                         Toast.makeText(getApplicationContext(), onedepartmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                         Looper.loop();// 进入loop中的循环，查看消息队列
                     }
                 }
             }).start();
         }*/
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
