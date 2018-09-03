package com.oliveoa.view.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;

import java.util.List;


public class WaitActivity1 extends AppCompatActivity {

    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addOAlistView;
    private LinearLayout addLAlistView;
    private LinearLayout addBTAlistView;
    private TextView tvname,tvtype;
    private List<OvertimeApplication> oa;
    private List<OvertimeApplicationApprovedOpinionList> oaaol;
    private List<LeaveApplication> la;
    private List<LeaveApplicationApprovedOpinionList> laaol;
    private List<BusinessTripApplication> bta;
    private List<BusinessTripApplicationApprovedOpinionList> btaaol;

    private BusinessTripApplicationDao btaDao;
    private LeaveApplicationDao laDao;
    private OvertimeApplicationDao oaDao;

    private BusinessTripApplicationApprovedOpinionListDao btaaoldao;
    private LeaveApplicationApprovedOpinionListDao laoldao;
    private OvertimeApplicationApprovedOpinionListDao oaaoldao;

    private String TAG = this.getClass().getSimpleName();
    private ImageView back,add;
    private ImageView isapproved;

    /*protected void onCreate(Bundle savedInstanceState) {
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
                *//*intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfos);
                intent.putExtra("index",departmentInfos.size());
                setAddDepartmentinfo(departmentInfos.size());*//*
                startActivity(intent);
                finish();
            }
        });
//        tvname = (TextView)findViewById(R.id.item_name);
//        tvtype = (TextView)findViewById(R.id.item_type);

        addOAlistView = (LinearLayout)findViewById(R.id.wait_list);
//        addLAlistView = (LinearLayout)findViewById(R.id.la_list);
//        addBTAlistView = (LinearLayout)findViewById(R.id.bta_list);
        initData();
        addViewItem(null);

    }

    private void initData(){
        btaDao = EntityManager.getInstance().getBusinessTripApplicationInfo();
        laDao = EntityManager.getInstance().getLeaveApplicationInfo();
        oaDao = EntityManager.getInstance().getOvertimeApplicationInfo();

        btaaoldao = EntityManager.getInstance().getBusinessTripApplicationApprovedOpinionListInfo();
        laoldao = EntityManager.getInstance().getLeaveApplicationApprovedOpinionListInfo();
        oaaoldao = EntityManager.getInstance().getOvertimeApplicationApprovedOpinionListInfo();

        la = laDao.queryBuilder()
                .orderAsc(LeaveApplicationDao.Properties.Orderby)
                .list();

        oa = oaDao.queryBuilder()
                .orderAsc(OvertimeApplicationDao.Properties.Orderby)
                .list();

        bta = btaDao.queryBuilder()
                .orderAsc(BusinessTripApplicationDao.Properties.Orderby)
                .list();

        laaol = laoldao.queryBuilder()
                .orderAsc(LeaveApplicationApprovedOpinionListDao.Properties.Orderby)
                .list();

        oaaol = oaaoldao.queryBuilder()
                .orderAsc(OvertimeApplicationApprovedOpinionListDao.Properties.Orderby)
                .list();

        btaaol = btaaoldao.queryBuilder()
                .orderAsc(BusinessTripApplicationApprovedOpinionListDao.Properties.Orderby)
                .list();

        Log.i("la:",la.toString());
        Log.i("oa:",oa.toString());
        Log.i("bta:",bta.toString());
        Log.i("laaol:",laaol.toString());
        Log.i("oaaol:",oaaol.toString());
        Log.i("btaaol:",btaaol.toString());

    }

    *//**
     * Item排序
     *//*
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addOAlistView.getChildCount(); i++) {
            final View childAt = addOAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            //final View child = LayoutInflater.from(mContext).inflate(R.layout.item_list_1, null);
            //final LinearLayout item =childAt.findViewWithTag(child);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WaitActivity1.this, OvertimeInfoActivity.class);
                    intent.putExtra("index", finalI);
                    startActivity(intent);
                    finish();
                   // Toast.makeText(WaitActivity1.this, "你点击了加班申请", Toast.LENGTH_SHORT).show();
                    }
            });
       }
        for (int i = 0; i < addLAlistView.getChildCount(); i++) {
            final View childAt = addLAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WaitActivity1.this, LeaveInfoActivity.class);
                    intent.putExtra("index", finalI);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(WaitActivity1.this, "你点击了请假申请", Toast.LENGTH_SHORT).show();
                }
            });
        }
        for (int i = 0; i < addBTAlistView.getChildCount(); i++) {
            final View childAt = addBTAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WaitActivity1.this, BusinessInfoActivity.class);
                    intent.putExtra("index", finalI);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(WaitActivity1.this, "你点击了出差申请", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {

        if (la == null&&bta==null&&oa==null) {//如果申请列表为0，加载空布局
            Toast.makeText(WaitActivity1.this, "当前没有申请！", Toast.LENGTH_SHORT).show();
        } else {//如果有申请则按数组大小加载布局
            for(int i = 0;i <oa.size(); i ++){
                View hotelEvaluateView = View.inflate(WaitActivity1.this, R.layout.item_list_1, null);
                 addOAlistView.addView(hotelEvaluateView);
                InitOADataViewItem();

            }
            for(int i = 0;i <la.size(); i ++){
                View hotelEvaluateView = View.inflate(WaitActivity1.this, R.layout.item_list_1, null);
                addLAlistView.addView(hotelEvaluateView);
                InitLADataViewItem();

            }
            for(int i = 0;i <bta.size(); i ++){
                View hotelEvaluateView = View.inflate(WaitActivity1.this, R.layout.item_list_1, null);
                addBTAlistView.addView(hotelEvaluateView);
                InitBTADataViewItem();

            }
            sortHotelViewItem();

        }
    }

    *//**
     * Item加载数据
     *//*
    private void InitOADataViewItem() {
        int i;
        Log.e(TAG,"addOAlistView.size"+addOAlistView.getChildCount());
        for (i = 0; i < addOAlistView.getChildCount(); i++) {
            View childAt = addOAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            tvname = (TextView) childAt.findViewById(R.id.item_name);
            tvtype = (TextView) childAt.findViewById(R.id.item_type);
            isapproved = (ImageView) childAt.findViewById(R.id.isapproved);

            tvname.setText(oa.get(i).getReason());
            tvtype.setText("加班申请");

            for (int n = 0; n < oaaol.size(); n++) {
                if (oaaol.get(n).getOaid().equals(oa.get(i).getOaid())) {
                    int flag = oaaol.get(n).getIsapproved();
                    switch (flag) {
                        case -2:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case -1:
                            isapproved.setImageResource(R.drawable.refuse_pic);
                            break;
                        case 0:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case 1:
                            isapproved.setImageResource(R.drawable.pass_pic);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        Log.e(TAG, "加班理由：" + tvname.getText().toString());
    }
    private void InitLADataViewItem() {
        int i;
        Log.e(TAG,"addLAlistView.size"+addLAlistView.getChildCount());
        for (i = 0; i < addLAlistView.getChildCount(); i++) {
            View childAt = addLAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            tvname = (TextView) childAt.findViewById(R.id.item_name);
            tvtype = (TextView) childAt.findViewById(R.id.item_type);
            isapproved = (ImageView) childAt.findViewById(R.id.isapproved);

            tvname.setText(la.get(i).getReason());
            tvtype.setText("请假申请");

            for (int n = 0; n < laaol.size(); n++) {
                if (laaol.get(n).getLaid().equals(la.get(i).getLaid())) {
                    int flag = laaol.get(n).getIsapproved();
                    switch (flag) {
                        case -2:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case -1:
                            isapproved.setImageResource(R.drawable.refuse_pic);
                            break;
                        case 0:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case 1:
                            isapproved.setImageResource(R.drawable.pass_pic);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        Log.e(TAG, "请假理由：" + tvname.getText().toString());
    }
    private void InitBTADataViewItem() {
        int i;
        Log.e(TAG,"addBTAlistView.size"+addBTAlistView.getChildCount());
        for (i = 0; i < addBTAlistView.getChildCount(); i++) {
            View childAt = addBTAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.application_item);
            tvname = (TextView) childAt.findViewById(R.id.item_name);
            tvtype = (TextView) childAt.findViewById(R.id.item_type);
            isapproved = (ImageView) childAt.findViewById(R.id.isapproved);

            tvname.setText(bta.get(i).getTask());
            tvtype.setText("出差申请");

            for (int n = 0; n < btaaol.size(); n++) {
                if (btaaol.get(n).getBtaid().equals(bta.get(i).getBtaid())) {
                    int flag = btaaol.get(n).getIsapproved();
                    switch (flag) {
                        case -2:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case -1:
                            isapproved.setImageResource(R.drawable.refuse_pic);
                            break;
                        case 0:
                            isapproved.setImageResource(R.drawable.wait_pic);
                            break;
                        case 1:
                            isapproved.setImageResource(R.drawable.pass_pic);
                            break;
                        default:
                            break;
                    }
                }
            }
            }
        Log.e(TAG, "出差请假理由：" + tvname.getText().toString());
    }*/


    }



