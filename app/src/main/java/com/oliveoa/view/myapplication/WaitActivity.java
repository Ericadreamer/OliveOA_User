package com.oliveoa.view.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class WaitActivity extends Fragment {

    private Context mContext;
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addWaitlistView;
    private TextView tvname;
    private View rootview;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_wait, container, false);
        this.mContext = getActivity();
        //initView();

        return rootview;
    }


//    private void initView() {
//        tvname = (TextView) rootview.findViewById(R.id.item_name);
//        addWaitlistView = (LinearLayout) rootview.findViewById(R.id.wait_list);
//
//        addViewItem(null);
//        initData();
//    }
//
//    private void initData(){
//        btaDao = EntityManager.getInstance().getBusinessTripApplicationInfo();
//        laDao = EntityManager.getInstance().getLeaveApplicationInfo();
//        oaDao = EntityManager.getInstance().getOvertimeApplicationInfo();
//
//        la = laDao.queryBuilder()
//                .orderAsc(LeaveApplicationDao.Properties.Orderby)
//                .list();
//
//        oa = oaDao.queryBuilder()
//                .orderAsc(OvertimeApplicationDao.Properties.Orderby)
//                .list();
//
//        bta = btaDao.queryBuilder()
//                .orderAsc(BusinessTripApplicationDao.Properties.Orderby)
//                .list();
//
//        Log.i("la:",la.toString());
//        Log.i("oa:",oa.toString());
//        Log.i("bta:",bta.toString());
//
//    }
//
//    /**
//     * Item排序
//     */
//    private void sortHotelViewItem() {
//        //获取LinearLayout里面所有的view
//        for (int i = 0; i < addWaitlistView.getChildCount(); i++) {
//            final View childAt = addWaitlistView.getChildAt(i);
//            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
//            //final View child = LayoutInflater.from(mContext).inflate(R.layout.item_list_1, null);
//            //final LinearLayout item =childAt.findViewWithTag(child);
//
//            item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "你点击了www", Toast.LENGTH_SHORT).show();
//                    }
//            });
//       }
//    }
//
//    //添加ViewItem
//    private void addViewItem(View view) {
//        if (la == null&&bta==null&&oa==null) {//如果申请列表为0，加载空布局
//            Toast.makeText(mContext, "当前没有申请！", Toast.LENGTH_SHORT).show();
//        } else {//如果有申请则按数组大小加载布局
//            for(int i = 0;i <7; i ++){
//                View hotelEvaluateView = View.inflate(mContext, R.layout.item_list_1, null);
//                 addWaitlistView.addView(hotelEvaluateView);
//                InitDataViewItem();
//
//            }
//            sortHotelViewItem();
//
//        }
//    }
//
//    /**
//     * Item加载数据
//     */
//    private void InitDataViewItem() {
//        int i;
////        for (i = 0; i <  addWaitlistView.getChildCount(); i++) {
////            View childAt =  addWaitlistView.getChildAt(i);
////            tvname = (TextView)childAt.findViewById(R.id.dname);
////
////            tvname.setText(departmentInfo.get(i).getName());
////        }
//        Log.e(TAG, "部门名称：" + tvname.getText().toString());
//    }


}
