package com.oliveoa.view.addressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.oliveoa.Adapter.MyBaseExpandableListAdapter;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.Group;
import com.oliveoa.pojo.Item;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.mine.PersonalDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentEmployeeActivity extends AppCompatActivity {
    private transient ArrayList<Group> gData = null;
    private transient ArrayList<ArrayList<Item>> iData = null;
    private transient ArrayList<Item> lData = null;

    private Context mContext;
    private ExpandableListView exlist_staff;
    private MyBaseExpandableListAdapter myAdapter = null;

    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    private List<ContactInfo> employeeInfos;
    private List<DepartmentInfo> departmentInfos;

    private ContactInfoDao employeeInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_employee);

        initData();
    }

    public void initView() {
        mContext = DepartmentEmployeeActivity.this;
        back = (ImageView) findViewById(R.id.iback);
        exlist_staff = (ExpandableListView) findViewById(R.id.exlist_staff);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentEmployeeActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",2);
                startActivity(intent);
            }
        });
        exlist_staff.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.i("" + DepartmentEmployeeActivity.this, "group " + groupPosition);
                return false;
            }
        });

        if (employeeInfoDao.queryBuilder().count() > 0) {
            //数据准备
            gData = new ArrayList<Group>();
            iData = new ArrayList<ArrayList<Item>>();
            if (departmentInfos != null) {
                for (int i = 0; i < departmentInfos.size(); i++) {
                    gData.add(new Group(departmentInfos.get(i).getName()));

                    lData = new ArrayList<Item>();

                    employeeInfos = employeeInfoDao.queryBuilder().where(ContactInfoDao.Properties.Dcid.eq(departmentInfos.get(i).getDcid())).list();
                    if (employeeInfos != null) {
                        for (int j = 0; j < employeeInfos.size(); j++)
                            lData.add(new Item(R.drawable.yonghu, employeeInfos.get(j).getName() + "", employeeInfos.get(j).getEid()));
                    }

                    iData.add(lData);
                }

                myAdapter = new MyBaseExpandableListAdapter(gData, iData, mContext);
                exlist_staff.setAdapter(myAdapter);
            }

            exlist_staff.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.i(TAG, "被点击的员工Eid：" + iData.get(groupPosition).get(childPosition).getiEid());
                    ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
                    ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(iData.get(groupPosition).get(childPosition).getiEid())).unique();
                    if(ci!=null){
                        Intent intent = new Intent(mContext,PersonalDetailsActivity.class);
                        intent.putExtra("ci",ci);
                        intent.putExtra("index",0);
                        mContext.startActivity(intent);
                    }
                    return false;
                }
            });
            exlist_staff.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    //Log.i("" + EmployeelistActivity.this, "group " + groupPosition);
                    Log.i(TAG, "GroupSize=" + gData.get(groupPosition).getgName() + "_________ChildSize=" + iData.get(groupPosition).size());
                    if (iData.get(groupPosition).size() == 0)
                        Toast.makeText(getApplicationContext(), "提示：没有员工在" + gData.get(groupPosition).getgName() + "门哦！", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        }

    public void initData() {
        employeeInfoDao = EntityManager.getInstance().getContactInfo();
        departmentInfoDao =EntityManager.getInstance().getDepartmentInfo();
        departmentInfos = departmentInfoDao.queryBuilder().list();
        initView();
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
