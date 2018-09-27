package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.oliveoa.Adapter.AdhibitionGridViewAdapter;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingApplicationAndStatus;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.documentmanagement.DocumentManagementActivity;
import com.oliveoa.view.meetingmanagement.MyMeetingActivity;
import com.oliveoa.view.myapplication.MainApplicationActivity;
import com.oliveoa.view.note.MyNoteActivity;
import com.oliveoa.view.workschedule.MyWorkActivity;
import com.oliveoa.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class AdhibitionActivity extends Fragment {

    //private RadioButton application, schedule, approval, document, meeting, note;
    private Context mContext;
    private ImageView more;
    private LinearLayout addMlistView;
    private View rootview;

    AdhibitionGridViewAdapter adapter;
    AdhibitionGridView gridView;
    private LoadingDialog loadingDialog;

    //九宫格图片设置
    private int[] imgs={
            R.drawable.ic_menu_application,
            R.drawable.ic_menu_notify,
            R.drawable.ic_menu_examine,
            R.drawable.ic_menu_official,
            R.drawable.ic_menu_meeting,
            R.drawable.ic_menu_note,
    };

    //九宫格图片下方文字设置
    private String[] titles = new String[]{"我的申请", "工作日程","审批","公文管理", "会议管理","便签"};


    /*private String[] texts = {
            "我的申请",
            "工作日程",
            "审批",
            "公文管理",
            "会议管理",
            "便签",
    };*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_adhibition, container, false);
        this.mContext = getActivity();

        initView();
        setGridView();

        addMlistView = (LinearLayout) rootview.findViewById(R.id.message_list);
        //默认添加一个Item
        addViewItem(null);

        more = (ImageView) rootview.findViewById(R.id.more);

        //监听事件
        more.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                final Intent it = new Intent(getActivity(), MessageListActivity.class);
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(it); //执行
                    }
                };
                timer.schedule(task, 1000 * 0); //0秒后
            }
        });

        return rootview;

    }

    public void initView() {
        gridView = (AdhibitionGridView) rootview.findViewById(R.id.gridview);
        loadingDialog = new LoadingDialog(getActivity(), "正在加载数据", true);

        //九宫格跳转
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0://点击图片加班跳转
                    {
                        loadingDialog.show();
                        applicationinfo();
                    }
                    break;
                    case 1://点击图片请假跳转
                    {
                        loadingDialog.show();
                        scheduleinfo();
                    }
                    break;
                    case 2://点击图片出差跳转
                    {
                        loadingDialog.show();
                        approvalinfo();
                    }
                    break;
                    case 3://点击图片会议跳转
                    {
                        loadingDialog.show();
                        documentinfo();
                    }
                    break;
                    case 4://点击图片离职跳转
                    {
                        loadingDialog.show();
                        meetinginfo();
                    }
                    break;
                    case 5://点击图片转正跳转
                    {
                        loadingDialog.show();
                        noteinfo();
                    }
                    break;
                    default:
                        break;
                }
            }

        });

    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView() {
        int size = imgs.length;
        int length = 90;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        //gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(0); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
        adapter = new AdhibitionGridViewAdapter(getActivity(),imgs,titles);
        gridView.setAdapter(adapter);
    }


    //添加ViewItem
    private void addViewItem(View view) {

    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {

    }

    //我的申请
    private void applicationinfo() {
        final Intent it = new Intent(getActivity(), MainApplicationActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后
    }

    //工作日程
    private void scheduleinfo() {
        Log.e(TAG, "scheduleinfo() ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                Log.d(TAG, "cookie==" + s);
                WorkDetailService service = new WorkDetailService();
                StatusAndMsgAndDataHttpResponseObject<ArrayList<WorkDetail>> statusAndMsgAndDataHttpResponseObject = service.getsubmitedwork(s, 0);
                WorkdetailAndStatus workdetailAndStatus = new WorkdetailAndStatus();
                WorkdetailAndStatusDao workdetailAndStatusDao = EntityManager.getInstance().getWorkdetailAndStatusDao();
                workdetailAndStatusDao.deleteAll();
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    List<WorkDetail> workDetails = statusAndMsgAndDataHttpResponseObject.getData();
                    Log.e(TAG, workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for (int i = 0; i < workDetails.size(); i++) {
                        workdetailAndStatus.setWaid(workDetails.get(i).getSwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(0);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    // startActivity(new Intent(getActivity(), MyWorkActivity.class));

                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "网络错误，获取我的拟定工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                StatusAndMsgAndDataHttpResponseObject<ArrayList<IssueWork>> isswork = service.getIssueworktome(s, 0);
                if (isswork.getStatus() == 0) {
                    List<IssueWork> workDetails = isswork.getData();
                    Log.e(TAG, workDetails.toString());
                    DateFormat dateFormat = new DateFormat();
                    for (int i = 0; i < workDetails.size(); i++) {
                        workdetailAndStatus.setWaid(workDetails.get(i).getIwid());
                        workdetailAndStatus.setStarttime(dateFormat.LongtoDatedd(workDetails.get(i).getBegintime()));
                        workdetailAndStatus.setEndtime(dateFormat.LongtoDatedd(workDetails.get(i).getEndtime()));
                        workdetailAndStatus.setStatus(1);
                        workdetailAndStatus.setTheme(workDetails.get(i).getContent());
                        workdetailAndStatusDao.insert(workdetailAndStatus);
                    }
                    startActivity(new Intent(getActivity(), MyWorkActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "网络错误，获取分配与我工作信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();


    }

    //审批
    private void approvalinfo() {
        final Intent it = new Intent(getActivity(), MainApprovalActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后


    }

    //公文管理
    private void documentinfo() {
        final Intent it = new Intent(getActivity(), DocumentManagementActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后


    }

    //会议管理
    private void meetinginfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = mContext.getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                MeetingApplicationService service = new MeetingApplicationService();
                MeetingApplicationAndStatusDao meetingApplicationAndStatusDao = EntityManager.getInstance().getMeetingApplicationAndStatusDao();
                MeetingApplicationAndStatus application = new MeetingApplicationAndStatus();
                DateFormat dateFormat = new DateFormat();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIdoing(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for (i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s, arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                            application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                            application.setMaid(aaol.getMeetingApplication().getMaid());
                            application.setTheme(aaol.getMeetingApplication().getTheme());
                            application.setStatus(1);
                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    //startActivity(new Intent(getActivity(), MyApplicationActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIwilldo(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for (i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s, arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                            application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                            application.setMaid(aaol.getMeetingApplication().getMaid());
                            application.setTheme(aaol.getMeetingApplication().getTheme());
                            application.setStatus(2);

                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIdone(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
                    meetingApplicationAndStatusDao.deleteAll();
                    for (i = 0; i < arrayListStatusAndDataHttpResponseObject.getData().size(); i++) {
                        StatusAndDataHttpResponseObject<MeetingApplicationInfoJsonBean> statusAndDataHttpResponseObject = service.getApplicationInfo(s, arrayListStatusAndDataHttpResponseObject.getData().get(i).getMaid());
                        if (statusAndDataHttpResponseObject.getStatus() == 0) {
                            MeetingApplicationInfoJsonBean aaol = statusAndDataHttpResponseObject.getData();
                            Log.i(TAG, "aaol:" + aaol);
                            application.setStarttime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getBegintime()));
                            application.setEndtime(dateFormat.LongtoDatemm(aaol.getMeetingApplication().getEndtime()));
                            application.setMaid(aaol.getMeetingApplication().getMaid());
                            application.setTheme(aaol.getMeetingApplication().getTheme());
                            application.setStatus(3);
                        }
                        meetingApplicationAndStatusDao.insert(application);
                        //Log.e(TAG,"application["+i+"]-------"+application.toString());
                    }
                    startActivity(new Intent(getActivity(), MyMeetingActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }

        }).start();
    }

    //便签
    private void noteinfo() {
        final Intent it = new Intent(getActivity(), MyNoteActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
            }
        };
        timer.schedule(task, 1000 * 0); //0秒后

    }

}
