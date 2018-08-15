package com.oliveoa.view.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.util.LinesEditView;
import com.oliveoa.view.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class RegularWorkerActivity extends AppCompatActivity {
    private ImageView back,save;
    private TextView tStartDate,tEndDate,tDuty,taddPerson; //到岗日期，截止日期，所属部门职务，添加审批人
    private LinearLayout addPersonList;  //审批人列表
    private String tmpdname;
    private String tmppname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_worker);
        initView();
        initData();
    }
    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        save = (ImageView) findViewById(R.id.isave);
        tStartDate = (TextView) findViewById(R.id.start_date);
        tEndDate = (TextView) findViewById(R.id.end_date);
        tDuty = (TextView) findViewById(R.id.content_dpcid);
        taddPerson = (TextView) findViewById(R.id.person_add);
        addPersonList = (LinearLayout) findViewById(R.id.approve_list);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularWorkerActivity.this, MyApplicationActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        taddPerson.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularWorkerActivity.this, SelectPersonApprovingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //试用期总结
        LinesEditView linesEditView = new LinesEditView(RegularWorkerActivity.this);
        String test = linesEditView.getContentText();

        // 默认添加一个Item
        addViewItem(null);


    }

    private void initData() {

    }
    private void save() {

    }

    //添加审批人操作
    public void personadd(){

    }

    //加载R.layout.item_approve列表
    private void addViewItem(View view) {

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem(){

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem(){

    }

    //到岗日期选择
    public void onYearMonthDayPicker1(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTitleText("离职日期选择");
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2111, 1, 30);
        picker.setRangeStart(2018, 8, 1);
//        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tStartDate.setText(year + "/" + month + "/" + day);
                // showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "/" + picker.getSelectedMonth() + "/" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "/" + month + "/" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "/" + picker.getSelectedMonth() + "/" + day);
            }
        });
        picker.show();
    }

    //截止日期选择
    public void onYearMonthDayPicker2(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTitleText("离职日期选择");
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2111, 1, 30);
        picker.setRangeStart(2018, 8, 1);
//        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tEndDate.setText(year + "/" + month + "/" + day);
                // showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "/" + picker.getSelectedMonth() + "/" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "/" + month + "/" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "/" + picker.getSelectedMonth() + "/" + day);
            }
        });
        picker.show();
    }

    /**
     * 以下部门职务的联动直接从公司端复制过来的，缺少一些东西会标红，所以先注释掉了
     * @param
     */
    /*//多级联动，选择部门职务
    public void onLinkagePicker(View view) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(DimissionActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + ":" + options2Items.get(options1).get(option2);
                 + options3Items.get(options1).get(options2).get(options3).getPickerViewText()
                ;
                tDuty.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)
                .setTitleText("部门职务选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.rgb(0, 178, 238))//设置分割线的颜色
                .setDividerType(com.contrarywind.view.WheelView.DividerType.WRAP)
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleSize(16)
                .setCancelColor(Color.rgb(0, 178, 238))
                .setSubmitColor(Color.rgb(0, 178, 238))
                .setSubCalSize(16)
                .setTextColorCenter(Color.rgb(0, 178, 238))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        //String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        // Toast.makeText(EditEmployeeinfoActivity.this, str, Toast.LENGTH_SHORT).show();
                        tDuty.setText(options1Items.get(options1) + ":" + options2Items.get(options2));
                        tmpdname = options1Items.get(options1);
                        tmppname = options2Items.get(options1).get(options2);
                    }
                })
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    //联动数据获取
    private void getOptionData() {
        DepartmentDAOImpl departmentDAO = new DepartmentDAOImpl(AddEmployeeinfoActivity.this);
        DutyDAOImpl dutyDAO = new DutyDAOImpl(AddEmployeeinfoActivity.this);

        departmentInfos = departmentDAO.getDepartments();
        //选项1
        for (int i = 0; i < departmentInfos.size(); i++) {
            options1Items.add(departmentInfos.get(i).getName());
        }

        //选项2
        for (int i = 0; i < departmentInfos.size(); i++) {
            dutyInfos = dutyDAO.getDutys(departmentInfos.get(i).getDcid());
            //Log.i("EditEmployeeinfo.DTInfo",dutyInfos.toString());
            ArrayList<String> options2Item = new ArrayList<>();
            for (int j = 1; j < dutyInfos.size(); j++) {
                options2Item.add(dutyInfos.get(j).getName());
            }
            options2Items.add(options2Item);
        }

        --------数据源添加完毕---------
    }*/


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
