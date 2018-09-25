package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.controller.JobTransferApplicationService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.JobTransferApplicationDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.ApproveNumber;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.pojo.DepartmentAndDuty;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.JobTransferApplication;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;
import com.oliveoa.widget.LoadingDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 原部门职务根据输入的被调员工编号自动获取
 */

public class AdjustPostActivity extends AppCompatActivity {

    private ImageView back,save,delete,epselect,dpdtselect;
    private EditText eReason; //被调员工编号
    private TextView tOriginalDuty,tTargetDuty,taddPerson,tname,tepname; //原部门职务，目标部门职务，添加审批人
    private String tmpdname;
    private String tmppname;

    private View textAreaView;
    private LinearLayout addlistView;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private DepartmentInfoDao departmentInfoDao;
    private ContactInfoDao contactInfoDao;
    private JobTransferApplicationDao applicationDao;
    private List<ApproveNumber> eps;
    private ApproveNumberDao approveNumberDao;
    private JobTransferApplication application;
    private ContactInfo ci;
    private DepartmentAndDutyDao departmentAndDutyDao;
    private DepartmentAndDuty departmentAndDuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_post);
        index = getIntent().getIntExtra("index",index);//审批人选择1，列表0，被调岗员工选择2
        Log.e("IDDEX=", String.valueOf(index));
        initView();

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tepname = (TextView) findViewById(R.id.employee_name);
        tOriginalDuty = (TextView) findViewById(R.id.original_dpcid);
        tTargetDuty = (TextView) findViewById(R.id.target_dpcid);
        taddPerson = (TextView) findViewById(R.id.person_add);

        dpdtselect = (ImageView)findViewById(R.id.dpdtselect);
        epselect = (ImageView)findViewById(R.id.epselect);

        addlistView = (LinearLayout) findViewById(R.id.approve_list);

        textAreaView = (LinesEditView)findViewById(R.id.reason);
        eReason = textAreaView.findViewById(R.id.id_et_input);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==1){
                    if(eps!=null){
                        approveNumberDao.deleteAll();
                        departmentAndDutyDao.deleteAll();
                    }
                }
                Intent intent = new Intent(AdjustPostActivity.this, MainApplicationActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addPerson();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        dpdtselect.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    selectdpdt();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        tTargetDuty.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    selectdpdt();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        epselect.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addEmployee();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        tepname.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                try {
                    addEmployee();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        // 默认添加一个Item
        if(index==1||index==2){
            addViewItem(null);
        }

    }

    private void initData() {
        applicationDao = EntityManager.getInstance().getJobTransferApplicationDao();
        contactInfoDao = EntityManager.getInstance().getContactInfo();
        approveNumberDao = EntityManager.getInstance().getApproveNumberDao();
        application = new JobTransferApplication();
        departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
        departmentAndDuty = new DepartmentAndDuty();
        ci = new ContactInfo();
        DateFormat dateFormat= new DateFormat();
        application = applicationDao.queryBuilder().unique();
        departmentAndDuty = departmentAndDutyDao.queryBuilder().unique();
        //加载暂存本地表的数据
        if(index==1||index==2) {
            eps = approveNumberDao.queryBuilder().list();

            Log.e(TAG,"EPS="+eps);
            if(application!=null){
                eReason.setText(application.getReason());
                Log.e(TAG,"Application="+application.toString());

                if(application.getEid()!=null&&!application.getEid().equals("")) {
                    ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(application.getEid())).unique();
                    Log.e(TAG,"ci="+ci.toString());

                    if (ci != null) {
                        tepname.setText(ci.getName());
                    }
                }
            }
            if(departmentAndDuty!=null){
                tTargetDuty.setText(departmentAndDuty.getDpname()+""+departmentAndDuty.getPname());
            }

        }


    }
    //添加审批人操作
    private void addPerson() throws ParseException {
         //保存表单数据
        //JobTransferApplication ap = new JobTransferApplication();
        if(application!=null) {
            application.setReason(eReason.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);

            LoadingDialog loadingDialog = new LoadingDialog(AdjustPostActivity.this, "正在加载数据", true);
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    //Todo Service
                    UserInfoService userInfoService = new UserInfoService();
                    //Todo Service.Method
                    ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                    departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                    contactInfoDao = EntityManager.getInstance().getContactInfo();
                    departmentInfoDao.deleteAll();
                    contactInfoDao.deleteAll();

                    //ToCheck JsonBean.getStatus()
                    if (contactHttpResponseObject.getStatus() == 0) {
                        ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                        Log.d("userinfo", contactInfos.toString());
                        if (contactInfos.size() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            for (int i = 0; i < contactInfos.size(); i++) {
                                Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                                DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                                departmentInfoDao.insert(departmentInfo);
                                Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                                for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                    if (contactInfos.get(i).getEmpContactList().get(j).getEmployee() != null) {
                                        Log.d(TAG, "contactInfos.get(i).getEmpContactList().get(" + j + ").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                        contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                    }
                                }
                            }
                            Intent intent = new Intent(AdjustPostActivity.this, SelectPersonApprovingActivity.class);
                            intent.putExtra("index", 7);
                            startActivity(intent);
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循7环，查看消息队列
                    }

                }
            }).start();
        }
    }

    //添加被调岗员工
    private void addEmployee() throws ParseException {
        //保存表单数据
        JobTransferApplication ap = new JobTransferApplication();

        ap.setReason(eReason.getText().toString().trim());
        Log.e(TAG,ap.toString());
        applicationDao.deleteAll();
        applicationDao.insert(ap);

        LoadingDialog loadingDialog  = new LoadingDialog(AdjustPostActivity.this,"正在加载数据",true);
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                //Todo Service
                UserInfoService userInfoService = new UserInfoService();
                //Todo Service.Method
                ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                contactInfoDao = EntityManager.getInstance().getContactInfo();
                departmentInfoDao.deleteAll();
                contactInfoDao.deleteAll();

                //ToCheck JsonBean.getStatus()
                if (contactHttpResponseObject.getStatus()==0) {
                    ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                    Log.d("userinfo", contactInfos.toString());
                    if(contactInfos.size()==0){
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(),"该公司未创建更多的部门和员工", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }else {
                        for (int i = 0; i < contactInfos.size(); i++) {
                            Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                            DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                            departmentInfoDao.insert(departmentInfo);
                            Log.d(TAG, "contactInfos.get(i).getEmpContactList().size():" + contactInfos.get(i).getEmpContactList().size());
                            for (int j = 0; j < contactInfos.get(i).getEmpContactList().size(); j++) {
                                if (contactInfos.get(i).getEmpContactList().get(j).getEmployee()!= null) {
                                    Log.d(TAG, "contactInfos.get(i).getEmpContactList().get("+j+").getEmployee()" + contactInfos.get(i).getEmpContactList().get(j).getEmployee().toString());
                                    contactInfoDao.insert(contactInfos.get(i).getEmpContactList().get(j).getEmployee());
                                }
                            }
                        }
                        Intent intent = new Intent(AdjustPostActivity.this, SelectPersonApprovingActivity.class);
                        intent.putExtra("index",10);
                        startActivity(intent);
                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }

            }
        }).start();
    }

    //添加目标职位
    private void selectdpdt() throws ParseException {
        //保存表单数据
        if(application!=null) {
            application.setReason(eReason.getText().toString().trim());
            Log.e(TAG, application.toString());
            applicationDao.deleteAll();
            applicationDao.insert(application);


            LoadingDialog loadingDialog = new LoadingDialog(AdjustPostActivity.this, "正在加载数据", true);
            loadingDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    //Todo Service
                    UserInfoService userInfoService = new UserInfoService();
                    //Todo Service.Method
                    ContactHttpResponseObject contactHttpResponseObject = userInfoService.contact(s);

                    departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
                    departmentAndDutyDao = EntityManager.getInstance().getDepartmentAndDutyDao();
                    departmentInfoDao.deleteAll();
                    departmentAndDutyDao.deleteAll();

                    //ToCheck JsonBean.getStatus()
                    if (contactHttpResponseObject.getStatus() == 0) {
                        ArrayList<ContactJsonBean> contactInfos = contactHttpResponseObject.getData();
                        Log.d("userinfo", contactInfos.toString());
                        if (contactInfos.size() == 0) {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该公司未创建更多的部门和岗位", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        } else {
                            for (int i = 0; i < contactInfos.size(); i++) {
                                Log.d("departmentinfo", contactInfos.get(i).getDepartment().toString());
                                DepartmentInfo departmentInfo = contactInfos.get(i).getDepartment();
                                departmentInfoDao.insert(departmentInfo);
                            }
                            Intent intent = new Intent(AdjustPostActivity.this, DepartmentSelectActivity.class);
                            intent.putExtra("index", 0);
                            startActivity(intent);
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), contactHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }


    private void save() throws ParseException {
        //Toast.makeText(getApplicationContext(), "你点击了保存", Toast.LENGTH_SHORT).show();
       if(departmentAndDuty!=null) {
           Log.e(TAG,departmentAndDuty.toString());
           DateFormat dateFormat = new DateFormat();
           if (TextUtils.isEmpty(eReason.getText().toString().trim())||eReason.getText().toString().trim().equals("请输入内容")){
               Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
           } else if (departmentAndDuty.getDcid() == null || departmentAndDuty.getPcid() == null || departmentAndDuty.getDcid().equals("") || departmentAndDuty.getPcid().equals("")) {
               Toast.makeText(getApplicationContext(), "请选择目标岗位！", Toast.LENGTH_SHORT).show();
           } else {
               application.setReason(eReason.getText().toString().trim());
               application.setAimdcid(departmentAndDuty.getDcid());
               application.setAimpcid(departmentAndDuty.getPcid());
               if (eps == null) {
                   Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
               } else if (eps.size() == 0) {
                   Toast.makeText(getApplicationContext(), "请选择审批人", Toast.LENGTH_SHORT).show();
               } else if(application.getEid()==null||application.getEid().equals("")){
                   Toast.makeText(getApplicationContext(), "请选择被调岗员工", Toast.LENGTH_SHORT).show();
               } else {
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           //读取SharePreferences的cookies
                           SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                           String s = pref.getString("sessionid", "");

                           JobTransferApplicationService service = new JobTransferApplicationService();
                           StatusAndMsgJsonBean statusAndMsgJsonBean = service.submitApplication(s, application, eps.toString());
                           if (statusAndMsgJsonBean.getStatus() == 0) {
                               Looper.prepare();
                               Toast.makeText(getApplicationContext(), "添加成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                               Looper.loop();
                           } else {
                               Looper.prepare();
                               Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                               Looper.loop();
                           }
                       }
                   }).start();
               }
           }
       }else{
           Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
       }
    }

    /**
     * Item排序
     */
    private void sortViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addlistView.getChildCount(); i++) {
            final View childAt = addlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            final ImageView delete = (ImageView)childAt.findViewById(R.id.delete);

            final int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tname = (TextView)childAt.findViewById(R.id.leave_type);
                    Log.e(TAG,"确定删除"+tname.getText().toString()+"??");
                    addlistView.removeView(item);
                    approveNumberDao.deleteAll();
                    ApproveNumber employee = new ApproveNumber();
                    int h=0;
                    for(int j=0;j<eps.size();j++){
                        if(!eps.get(j).getId().equals(eps.get(finalI).getId())){
                            employee.setId(eps.get(j).getId());
                            approveNumberDao.insert(employee);
                        }
                    }
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (eps==null) {
            /*Toast.makeText(OvertimeActivity.this, "当前没有审批！", Toast.LENGTH_SHORT).show();*/
        } else {
            for(int i = 0;i <eps.size(); i ++){
                View EvaluateView = View.inflate(AdjustPostActivity.this, R.layout.item_member, null);
                addlistView.addView(EvaluateView);
                InitDataViewItem();
            }
            sortViewItem();
        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addlistView.getChildCount(); i++) {
            View childAt = addlistView.getChildAt(i);
            LinearLayout item = (LinearLayout) childAt.findViewById(R.id.approve_person_item);
            tname = (TextView)childAt.findViewById(R.id.leave_type);

            ContactInfo ci = contactInfoDao.queryBuilder().where(ContactInfoDao.Properties.Eid.eq(eps.get(i).getId())).unique();
            if(ci!=null){
                tname.setText(ci.getName());
                Log.e("eid:",ci.getName());
            }else{
                tname.setText("");
            }

        }
    }


    //重写showToast
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
