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
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
 *  ClassName DepartmentSelectActivity
 *  Description 部门选择Activity
 *  @Author  Erica
 */

public class DepartmentSelectActivity extends AppCompatActivity {

    private List<DepartmentInfo> departmentInfos;
    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private int index;
    private TextView tvname;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_select);

        departmentInfos= getIntent().getParcelableArrayListExtra("alldp");
        index = getIntent().getIntExtra("index",index);//编辑部门0，添加部门1，编辑员工2，添加员工3，编辑资产4，添加资产5

        initData();
    }

    public void initData(){
        if(index==0||index==1) {
            departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
            temp = departmentInfoDao.queryBuilder().unique();
        }
        initview();
    }

    public void initview(){
        back =(ImageView)findViewById(R.id.info_back);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

       // 默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DepartmentSelectActivity.this);
                dialog.setTitle("提示");
                if(index==0) {
                    dialog.setMessage("是否确定退出选择,直接返回部门编辑页面？");
                }
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {back();
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

/**
     *   MethodName  back()
     *   Description 返回相应页面
     *   @Author Erica
   */

    private void back() {
        if(index==0) {
            if(temp!=null) {
                Log.e(TAG,temp.toString());
                temp.setDpid("");
                departmentInfoDao.deleteAll();
                departmentInfoDao.insert(temp);
                Log.e(TAG,departmentInfoDao.queryBuilder().unique().toString());
            }
         /*   Intent intent = new Intent(DepartmentSelectActivity.this, RedactDepartmentActivity.class);
            intent.putExtra("dpname", "无");
            intent.putExtra("index", 1);
            intent.putExtra("dp",temp);
            startActivity(intent);
            finish();*/
        }
    }

   /*
    *   MethodName  sortHotelViewItem()
    *   Description Item排序
    *   @Author Erica
    */

    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDPlistView.getChildCount(); i++) {
            final View childAt = addDPlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout)childAt.findViewById(R.id.depart_item);

            final TextView tname = (TextView)childAt.findViewById(R.id.dname);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        // Log.e(TAG,tname.getText().toString());
                      /*  if(index==0)  {  //编辑部门选择
                            EditDpSelect(tname);
                        }*/
                    }
            });
        }
    }


/**
     *   MethodName  EditPpSelect(TextView tname)
     *   Description 编辑物品选择
     *   @Author Erica
     */

    private void EditPpSelect(TextView tname) {
       /* if(pp!=null) {
            Log.e(TAG,pp.toString());
            String dcid =null;
            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    dcid = departmentInfos.get(i).getDcid();
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }
            final String finalDcid = dcid;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, finalDcid);
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 4);
                            intent.putParcelableArrayListExtra("alldt", dutyInfos);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }*/
    }


    //添加ViewItem
    private void addViewItem(View view) {
        if (departmentInfos == null) {//如果部门列表为0，加载空布局
           // View hotelEvaluateView = View.inflate(this, R.layout.activity_null_department, null);
            //addDPlistView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else {//如果有部门则按数组大小加载布局
            for(int i = 0;i <departmentInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_selectitem, null);
                addDPlistView.addView(hotelEvaluateView);
                InitDataViewItem();

            }
            sortHotelViewItem();

        }
    }

    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addDPlistView.getChildCount(); i++) {
            View childAt = addDPlistView.getChildAt(i);
            tvname = (TextView)childAt.findViewById(R.id.dname);

            tvname.setText(departmentInfos.get(i).getName());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString());
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
