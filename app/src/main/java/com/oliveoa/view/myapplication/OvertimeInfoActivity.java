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
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.OvertimeApplication;
import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.MainActivity;
import com.oliveoa.view.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OvertimeInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView tname,tstatus,ttime,treason;
    private int index;
    private OvertimeApplicationDao oaDao;
    private List<OvertimeApplication> oa;
    private List<OvertimeApplicationApprovedOpinionList> oaaol;
    private OvertimeApplicationApprovedOpinionListDao oaaoldao;
    private ContactInfoDao cidao;
    private List<ContactInfo> ci;
    private LinearLayout addOAlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_info);

        index = getIntent().getIntExtra("index",index);

        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        ttime = (TextView) findViewById(R.id.time);
        treason = (TextView) findViewById(R.id.reason);
        addOAlistView = (LinearLayout)findViewById(R.id.approve_list);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OvertimeInfoActivity.this, WaitActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        initData();
        addViewItem(null);

    }
    private void initData(){
        oaDao = EntityManager.getInstance().getOvertimeApplicationInfo();
        oa = oaDao.queryBuilder()
                .orderAsc(OvertimeApplicationDao.Properties.Orderby)
                .list();

        oaaoldao = EntityManager.getInstance().getOvertimeApplicationApprovedOpinionListInfo();
        oaaol = oaaoldao.queryBuilder()
                .where(OvertimeApplicationApprovedOpinionListDao.Properties.Oaid.eq(oa.get(index).getOaid()) )
                .orderDesc(OvertimeApplicationApprovedOpinionListDao.Properties.Orderby)
                .list();

        cidao = EntityManager.getInstance().getContactInfo();
        ci = cidao.queryBuilder()
                .orderDesc(ContactInfoDao.Properties.Orderby)
                .list();

        DateFormat dateFormat = new DateFormat();
        ttime.setText(dateFormat.LongtoDatemm(oa.get(index).getBegintime())+"--"+dateFormat.LongtoDatemm(oa.get(index).getEndtime()));
        treason.setText(oa.get(index).getReason());


        Log.i("oa:",oa.toString());
        Log.i("oaaol:",oaaol.toString());
        Log.i("ci:",ci.toString());

    }
    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addOAlistView.getChildCount(); i++) {
            final View childAt = addOAlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);

            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OvertimeInfoActivity.this, ApprovedInfoActivity.class);
                    intent.putExtra("oaaolcid", oaaol.get(finalI).getOaaocid());
                    startActivity(intent);
                    finish();

                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        Log.i("oa:",oa.toString());
        Log.i("oaaol:",oaaol.toString());
        Log.i("ci:",ci.toString());
        if (oaaol==null) {
            Toast.makeText(OvertimeInfoActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0;i <oaaol.size(); i ++){
                View EvaluateView = View.inflate(OvertimeInfoActivity.this, R.layout.approveapplication_listitem, null);
                addOAlistView.addView(EvaluateView);
                InitOADataViewItem();
            }
            sortHotelViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitOADataViewItem() {
        Log.i("ci.size():", String.valueOf(ci.size()));
        int i;
        for (i = 0; i < addOAlistView.getChildCount(); i++) {
            View childAt = addOAlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.person_approving);
            tstatus = (TextView)childAt.findViewById(R.id.status);


            for (int j=0;j<ci.size();j++){
                    if(oaaol.get(i).getEid().equals(ci.get(j).getEid())) {
                        tname.setText(ci.get(j).getName());
                        Log.e("eid:",ci.get(j).getName());
                        break;
                    }
            }
            int flag = oaaol.get(i).getIsapproved();
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

