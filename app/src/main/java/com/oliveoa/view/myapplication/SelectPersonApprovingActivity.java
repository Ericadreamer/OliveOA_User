package com.oliveoa.view.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.oliveoa.Adapter.MyBaseExpandableListAdapter;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.JobTransferApplicationDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.Group;
import com.oliveoa.pojo.Item;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.notice.AddNoticeActivity;
import com.oliveoa.view.workschedule.ProtocolWorkActivity;
import com.oliveoa.view.workschedule.WorkAllocationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SelectPersonApprovingActivity extends AppCompatActivity {

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

    private ContactInfo employeeInfo;
    private DepartmentInfo departmentInfo;
    private List<ApproveNumber> eps ;
    private int index;
    private ApproveNumberDao approveNumberDao;
    private ApproveNumber approveNumber;
    private String mid;
    private MeetingApplicationDao meetingApplicationDao;
    private JobTransferApplicationDao jobTransferApplicationDao;
    private WorkDetailDao workDetailDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person_approving);

        index = getIntent().getIntExtra("index",index);//1-9添加审批人：加班、请假、出差、会议、离职、转正、调岗、招聘、物品；10选择调岗员工;11公告添加审批人;12工作拟定批阅人;13工作分配对象
        Log.e("IDDEX=", String.valueOf(index));
        initData();
    }
    public void initData(){
        employeeInfoDao = EntityManager.getInstance().getContactInfo();
        departmentInfoDao =EntityManager.getInstance().getDepartmentInfo();
        jobTransferApplicationDao = EntityManager.getInstance().getJobTransferApplicationDao();
        workDetailDao = EntityManager.getInstance().getWorkDetailDao();
        departmentInfos = departmentInfoDao.queryBuilder().list();
        //eps = approveNumberDao.queryBuilder().list();
        Log.e(TAG,""+employeeInfoDao.queryBuilder().count());
        Log.e(TAG,"departmentInfos:"+departmentInfoDao.queryBuilder().count());
        approveNumber = new ApproveNumber();
        initView();

    }

    private void initView() {
        mContext = SelectPersonApprovingActivity.this;

        exlist_staff = (ExpandableListView) findViewById(R.id.exlist_staff);
        back = (ImageView) findViewById(R.id.iback);

        exlist_staff.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.i("" + SelectPersonApprovingActivity.this, "group " + groupPosition);
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        if (employeeInfoDao.queryBuilder().count() > 0) {
            //数据准备
            gData = new ArrayList<Group>();
            iData = new ArrayList<ArrayList<Item>>();
            if(departmentInfos!=null) {
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
                    approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
                    meetingApplicationDao = EntityManager.getInstance().getMeetingApplicationDao();
                    Log.i(TAG, "被点击的员工Eid：" + iData.get(groupPosition).get(childPosition).getiEid());
                    mid = iData.get(groupPosition).get(childPosition).getiEid();
                    if (index == 0) {
                        MeetingApplication application = new MeetingApplication();
                        application = meetingApplicationDao.queryBuilder().unique();
                        Log.e(TAG,application.toString());
                        if (application != null) {
                            application.setAeid(mid);
                            meetingApplicationDao.deleteAll();
                            meetingApplicationDao.insert(application);
                            back();
                        }
                    }else if(index==10){
                        JobTransferApplication jobTransferApplication = new JobTransferApplication();
                        jobTransferApplication = jobTransferApplicationDao.queryBuilder().unique();
                        if(jobTransferApplication!=null){
                            jobTransferApplication.setEid(mid);
                            jobTransferApplicationDao.deleteAll();
                            jobTransferApplicationDao.insert(jobTransferApplication);
                            back();
                        }
                    }else if(index==12){
                        WorkDetail workDetail = new WorkDetail();
                        workDetail = workDetailDao.queryBuilder().unique();
                        if(workDetail!=null){
                            workDetail.setAeid(mid);
                            workDetailDao.deleteAll();
                            workDetailDao.insert(workDetail);
                            Log.e(TAG,workDetail.toString());
                            back();
                        }
                    }else if(index==13){
                       approveNumberDao.deleteAll();
                       ApproveNumber ap = new ApproveNumber();
                       ap.setId(mid);
                       approveNumberDao.insert(ap);
                       back();
                    }else {
                        ApproveNumber ap = approveNumberDao.queryBuilder().where(ApproveNumberDao.Properties.Id.eq(mid)).unique();
                        if (ap == null) {
                            approveNumber.setId(mid);
                            approveNumberDao.insert(approveNumber);
                            back();
                        } else {
                            Toast.makeText(getApplicationContext(), "提示：该审批人已被选择，请选择其他审批人！", Toast.LENGTH_SHORT).show();
                        }
                    }    //Log.i(TAG, "被点击的员工Eid：" + approveNumber.toString());
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

    private void back() {
        if(index==0) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, MeetingActivity.class);
            intent.putExtra("index", 1);//审批
            startActivity(intent);
            finish();
        }
        if(index==1) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, OvertimeActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==2) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, LeaveActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==3) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this,BusinessActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==4) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, MeetingActivity.class);
            intent.putExtra("index", 2);//参会
            startActivity(intent);
            finish();
        }
        if(index==5) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, DimissionActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==6) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, RegularWorkerActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==7) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, AdjustPostActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==8) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, RecruitmentActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==10) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, AdjustPostActivity.class);
            intent.putExtra("index", 2);//被调岗员工
            startActivity(intent);
            finish();
        }
        if(index==11) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, AddNoticeActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==12) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, ProtocolWorkActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==13) {
            Intent intent = new Intent(SelectPersonApprovingActivity.this, WorkAllocationActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
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
