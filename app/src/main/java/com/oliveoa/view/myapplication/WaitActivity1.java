package com.oliveoa.view.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.List;


public class WaitActivity1 extends AppCompatActivity {

    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addWaitlistView;
    private TextView tvname;
    private List<OvertimeApplication> oa;
    private List<OvertimeApplicationApprovedOpinionList> oaaol;
    private List<LeaveApplication> la;
    private List<LeaveApplicationApprovedOpinionList> laaol;
    private List<BusinessTripApplication> bta;
    private List<BusinessTripApplicationApprovedOpinionList> btaaol;

    private BusinessTripApplicationDao btaDao;
    private LeaveApplicationDao laDao;
    private OvertimeApplicationDao oaDao;

    private String TAG = this.getClass().getSimpleName();
    private ImageView back,add;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        initView();
    }


    private void initView() {
        back = (ImageView)findViewById(R.id.iback);
        add = (ImageView)findViewById(R.id.iadd);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaitActivity1.this, TabLayoutBottomActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WaitActivity1.this, AddApplicationActivity.class);
                /*intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfos);
                intent.putExtra("index",departmentInfos.size());
                setAddDepartmentinfo(departmentInfos.size());*/
                startActivity(intent);
                finish();
            }
        });
        tvname = (TextView)findViewById(R.id.item_name);
        addWaitlistView = (LinearLayout)findViewById(R.id.wait_list);

        initData();
        addViewItem(null);

    }

    private void initData(){
        btaDao = EntityManager.getInstance().getBusinessTripApplicationInfo();
        laDao = EntityManager.getInstance().getLeaveApplicationInfo();
        oaDao = EntityManager.getInstance().getOvertimeApplicationInfo();

        la = laDao.queryBuilder()
                .orderAsc(LeaveApplicationDao.Properties.Orderby)
                .list();

        oa = oaDao.queryBuilder()
                .orderAsc(OvertimeApplicationDao.Properties.Orderby)
                .list();

        bta = btaDao.queryBuilder()
                .orderAsc(BusinessTripApplicationDao.Properties.Orderby)
                .list();

        Log.i("la:",la.toString());
        Log.i("oa:",oa.toString());
        Log.i("bta:",bta.toString());

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addWaitlistView.getChildCount(); i++) {
            final View childAt = addWaitlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            //final View child = LayoutInflater.from(mContext).inflate(R.layout.item_list_1, null);
            //final LinearLayout item =childAt.findViewWithTag(child);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(WaitActivity1.this, "你点击了www", Toast.LENGTH_SHORT).show();
                    }
            });
       }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        Log.i("la:",la.toString());
        Log.i("oa:",oa.toString());
        Log.i("bta:",bta.toString());
        if (la == null&&bta==null&&oa==null) {//如果申请列表为0，加载空布局
            Toast.makeText(WaitActivity1.this, "当前没有申请！", Toast.LENGTH_SHORT).show();
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <oa.size(); i ++){
                View hotelEvaluateView = View.inflate(WaitActivity1.this, R.layout.item_list_1, null);
                 addWaitlistView.addView(hotelEvaluateView);
                InitDataViewItem();

            }
            sortHotelViewItem();

        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i <  addWaitlistView.getChildCount(); i++) {
            View childAt =  addWaitlistView.getChildAt(i);
//            tvname = (TextView)childAt.findViewById(R.id.dname);
//
//            tvname.setText(departmentInfo.get(i).getName());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString());
    }


}
