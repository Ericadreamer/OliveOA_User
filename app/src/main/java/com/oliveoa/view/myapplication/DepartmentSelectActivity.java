package com.oliveoa.view.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.OfficialDocumentDao;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentAndDuty;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.documentmanagement.IssueDocumentActivity;
import com.oliveoa.view.mine.PersonalDetailsEditActivity;

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
    private DepartmentAndDuty dpdt;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private ContactInfo contactInfo;
    private List<ApproveNumber> approveNumber;
    private ApproveNumberDao approveNumberDao;

    //private RecruitmentApplicationItemDao rpdaoitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_select);

        index = getIntent().getIntExtra("index",index);//调岗0，招聘1,个人资料修改2，3公文签发

        initData();
    }

    public void initData(){
        departmentAndDutyDao =EntityManager.getInstance().getDepartmentAndDutyDao();
        departmentAndDutyDao.deleteAll();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        departmentInfos = departmentInfoDao.queryBuilder().list();
        dpdt = new DepartmentAndDuty();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();

        if(index==2){
              contactInfo = contactInfoDao.queryBuilder().unique();
        }
        if(index==3){
            approveNumber = approveNumberDao.queryBuilder().list();
        }
        if(departmentInfos!=null){
            initview();
        }else{
            Toast.makeText(getApplicationContext(), "网络错误，部门岗位数据加载错误", Toast.LENGTH_SHORT).show();
        }

    }

    public void initview(){
        back =(ImageView)findViewById(R.id.null_back);
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
                    dialog.setMessage("是否确定退出选择,直接返回调岗申请创建页面？");
                }
                if(index==1) {
                    dialog.setMessage("是否确定退出选择,直接返回招聘申请创建页面？");
                }
                if(index==2) {
                    dialog.setMessage("是否确定退出选择,直接返回个人资料修改创建页面？");
                }
                if(index==3) {
                    dialog.setMessage("是否确定退出选择,直接返回签发公文页面？");
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

/**
     *   MethodName  back()
     *   Description 返回相应页面
     *   @Author Erica
   */

    private void back() {
        if(index==0) {
          dpdt.setDcid("");
          dpdt.setPcid("");
          dpdt.setDpname("");
          dpdt.setPname("");
          departmentAndDutyDao.insert(dpdt);
          Log.e(TAG,departmentAndDutyDao.queryBuilder().unique().toString());
          Intent intent = new Intent(DepartmentSelectActivity.this, AdjustPostActivity.class);
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
            Intent intent = new Intent(DepartmentSelectActivity.this, RecruitmentActivity.class);
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
            Intent intent = new Intent(DepartmentSelectActivity.this, PersonalDetailsEditActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==3){
            OfficialDocumentDao officialDocumentDao = EntityManager.getInstance().getOfficialDocumentDao();
            OfficialDocument officialDocument = officialDocumentDao.queryBuilder().unique();
            if(officialDocument!=null) {
                Log.i(TAG,officialDocument.toString());
                Intent intent = new Intent(DepartmentSelectActivity.this, IssueDocumentActivity.class);
                intent.putExtra("info",officialDocument);
                intent.putExtra("index", 1);
                startActivity(intent);
                finish();
            }
        }

    }

   /**
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
                    if(index==3)  {  //签发部门选择
                        issueDepartment(tname);
                    }
                    }
            });
        }
    }
    /**
     *   MethodName  issueDepartment(TextView tname){
     *   Description 签发部门
     *   @Author Erica
     */
    public void issueDepartment(TextView tname){
        for(int i=0;i<departmentInfos.size();i++){
            Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
            if(tname.getText().toString().equals(departmentInfos.get(i).getName())) {
                if(approveNumber!=null) {
                    ApproveNumber dp = new ApproveNumber();
                    dp = approveNumberDao.queryBuilder().where(ApproveNumberDao.Properties.Id.eq(departmentInfos.get(i).getDcid())).unique();
                    if(dp==null) {
                        dp.setId(departmentInfos.get(i).getDcid());
                        Log.e(TAG, departmentInfos.get(i).getDcid());
                        approveNumber.add(dp);
                        approveNumberDao.deleteAll();
                        for (int j = 0; j < approveNumber.size(); j++) {
                            approveNumberDao.insert(approveNumber.get(j));
                        }
                        back();
                    }else{
                        Toast.makeText(getApplicationContext(), "提示：签发部门不得重复选择，请选择其他签发部门！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }



    /**
     *   MethodName  userinfo(TextView tname)
     *   Description 个人资料修改
     *   @Author Erica
     */
    private void userinfo(TextView tname) {
        for(int i=0;i<departmentInfos.size();i++){
            Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
            if(tname.getText().toString().equals(departmentInfos.get(i).getName())) {
                if(contactInfo!=null) {
                    contactInfo.setDcid(departmentInfos.get(i).getDcid());
                    Log.e(TAG, departmentInfos.get(i).getDcid());
                }
                break;
            }
        }
        contactInfoDao.deleteAll();
        contactInfoDao.insert(contactInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoService service = new UserInfoService();
                DutyInfoJsonBean dutyInfoJsonBean = service.getPosition(dpdt.getDcid());
                if(dutyInfoJsonBean.getStatus()==0){
                    ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                    if(dutyInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                        intent.putExtra("index", 2);
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
    }

    /**
     *   MethodName  recruitment(TextView tname)
     *   Description 招聘申请
     *   @Author Erica
     */
    private void recruitment(TextView tname) {
        for(int i=0;i<departmentInfos.size();i++){
            Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
            if(tname.getText().toString().equals(departmentInfos.get(i).getName())) {
                //dcid = departmentInfos.get(i).getDcid();
                dpdt.setDcid(departmentInfos.get(i).getDcid());
                dpdt.setDpname(departmentInfos.get(i).getName());
                Log.e(TAG, departmentInfos.get(i).getDcid());
                break;
            }
        }
        departmentAndDutyDao.deleteAll();
        departmentAndDutyDao.insert(dpdt);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoService service = new UserInfoService();
                DutyInfoJsonBean dutyInfoJsonBean = service.getPosition(dpdt.getDcid());
                if(dutyInfoJsonBean.getStatus()==0){
                    ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                    if(dutyInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                        intent.putExtra("index", 1);
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
    }


    /**
     *   MethodName  jobTransfer(TextView tname)
     *   Description 调岗申请
     *   @Author Erica
     */

    private void jobTransfer(TextView tname) {

            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    //dcid = departmentInfos.get(i).getDcid();
                    dpdt.setDcid(departmentInfos.get(i).getDcid());
                    dpdt.setDpname(departmentInfos.get(i).getName());
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }
            departmentAndDutyDao.deleteAll();
            departmentAndDutyDao.insert(dpdt);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserInfoService service = new UserInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = service.getPosition(dpdt.getDcid());
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 0);
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
