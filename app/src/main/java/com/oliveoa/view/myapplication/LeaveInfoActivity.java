package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.LeaveApplication;
import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LeaveInfoActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tname,ttype,tstatus,ttime,treason;

    private int index;
    private LeaveApplicationDao laDao;
    private List<LeaveApplication> la;
    private List<LeaveApplicationApprovedOpinionList> laaol;
    private LeaveApplicationApprovedOpinionListDao laaoldao;
    private ContactInfoDao cidao;
    private List<ContactInfo> ci;
    private LinearLayout addLAlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_info);

        index = getIntent().getIntExtra("index",index);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        tname = (TextView) findViewById(R.id.person_approving);
        ttype = (TextView) findViewById(R.id.leave_type);
        tstatus = (TextView) findViewById(R.id.status);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveInfoActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initData(){
        laDao = EntityManager.getInstance().getLeaveApplicationInfo();
        la = laDao.queryBuilder()
                .orderAsc(OvertimeApplicationDao.Properties.Orderby)
                .list();

        laaoldao = EntityManager.getInstance().getLeaveApplicationApprovedOpinionListInfo();
        laaol = laaoldao.queryBuilder()
                .where(LeaveApplicationApprovedOpinionListDao.Properties.Laid.eq(la.get(index).getLaid()))
                .orderDesc(LeaveApplicationApprovedOpinionListDao.Properties.Orderby)
                .list();

        cidao = EntityManager.getInstance().getContactInfo();
        ci = cidao.queryBuilder()
                .orderDesc(ContactInfoDao.Properties.Orderby)
                .list();

        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(la.get(index).getBegintime())+"--"+dateFormat.LongtoDatemm(la.get(index).getEndtime()));
        treason.setText(la.get(index).getReason());


        Log.i("la:",la.toString());
        Log.i("laaol:",laaol.toString());
        Log.i("ci:",ci.toString());

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addLAlistView.getChildCount(); i++) {
            final View childAt = addLAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LeaveInfoActivity.this, ApprovedInfoActivity.class);
                    intent.putExtra("oaaolcid", laaol.get(finalI).getLaaocid());
                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        Log.i("la:",la.toString());
        Log.i("laaol:",laaol.toString());
        Log.i("ci:",ci.toString());
        if (laaol==null) {
            Toast.makeText(LeaveInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <laaol.size(); i ++){
                View EvaluateView = View.inflate(LeaveInfoActivity.this, R.layout.approveapplication_listitem, null);
                addLAlistView.addView(EvaluateView);
                InitLADataViewItem();
            }
            sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitLADataViewItem() {
        Log.i("ci.size():", String.valueOf(ci.size()));
        int i;
        for (i = 0; i < addLAlistView.getChildCount(); i++) {
            View childAt = addLAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);


            for (int j=0;j<ci.size();j++){
                if(laaol.get(i).getEid().equals(ci.get(j).getEid())) {
                    tname.setText(ci.get(j).getName());
                    Log.e("eid:",ci.get(j).getName());
                    break;
                }
            }
            int flag = laaol.get(i).getIsapproved();
            switch (flag) {
                case -2:
                    tstatus.setText("待审核");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                case -1:
                    tstatus.setText("不同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_refuse));
                    break;
                case 0:
                    tstatus.setText("同意");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_pass));
                    break;
                case 1:
                    tstatus.setText("正在审核");
                    tstatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                default:
                    break;
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
