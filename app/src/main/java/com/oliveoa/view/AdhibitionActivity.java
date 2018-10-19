package com.oliveoa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.Adapter.AdhibitionGridViewAdapter;
import com.oliveoa.common.StatusAndDataHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.MeetingApplicationService;
import com.oliveoa.controller.MessageService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.MessageDao;
import com.oliveoa.greendao.WorkdetailAndStatusDao;
import com.oliveoa.jsonbean.MeetingApplicationInfoJsonBean;
import com.oliveoa.jsonbean.MessageJsonbean;
import com.oliveoa.pojo.IssueWork;
import com.oliveoa.pojo.MeetingApplication;
import com.oliveoa.pojo.MeetingApplicationAndStatus;
import com.oliveoa.pojo.WorkDetail;
import com.oliveoa.pojo.WorkdetailAndStatus;
import com.oliveoa.util.DateFormat;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.ScreenSizeUtils;
import com.oliveoa.view.approval.MainApprovalActivity;
import com.oliveoa.view.documentmanagement.DocumentManagementActivity;
import com.oliveoa.view.meetingmanagement.MyMeetingActivity;
import com.oliveoa.view.myapplication.LeaveInfoActivity;
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

    private Boolean FLAG = false;

    private RecyclerView mContentRv;
    private TextView ttitle,tcontext,tvtip;
    private String TAG = this.getClass().getSimpleName();
    private List<com.oliveoa.pojo.Message> list;
    private Timer timer;
    private TimerTask task;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 要做的事情
            initData();
            super.handleMessage(msg);
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<com.oliveoa.pojo.Message>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(list!=null&list.size()!=0){
                        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(mContext));
                        mContentRv.setAdapter(new AdhibitionActivity.ContentAdapter(list));
                    }
                    break;
                case 2:
                    tvtip.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };


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
                //msg();
            }
        });
        timer = new Timer();
        if(task!=null){
            task.cancel();
        }
        task = new task();
        timer.schedule(task, 2000, 3000);//意思是在2秒后执行第一次，之后每3000秒在执行一次.timer不保证精确度且在无法唤醒cpu,不适合后台任务的定时。

        return rootview;


    }

    @Override
    public void onDestroy() {
        timer.cancel();
        task=null;
        super.onDestroy();
    }

    @Override
    public void onStop() {
        timer.cancel();
        task=null;
        super.onStop();
    }



    public void initView() {
        gridView = (AdhibitionGridView) rootview.findViewById(R.id.gridview);
        loadingDialog = new LoadingDialog(getActivity(), "正在加载数据", true);

        initData();
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

        while (FLAG) {
            try {
                Thread.sleep(3*1000);
                initData();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    //加载消息数据
    private void initData() {
        final MessageDao messageDao = EntityManager.getInstance().getMessageInfo();
        final List<com.oliveoa.pojo.Message> list = messageDao.queryBuilder().orderDesc(MessageDao.Properties.Orderby).list();
        if(list!=null&&list.size()!=0){
            Log.i(TAG, "IF(LIST!=NULL)=="+list.toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");
                    ArrayList<com.oliveoa.pojo.Message> temp = new ArrayList<>();
                    //Todo Service
                    MessageService service = new MessageService();
                    //Todo Service.Method
                    MessageJsonbean messageJsonbean = service.getMessagetome(s,list.get(0).getOrderby());
                    //ToCheck JsonBean.getStatus()
                    if (messageJsonbean.getStatus() == 0) {
                        //Log.i("DOCUMENT=",messageJsonbean.getData().toString());
                        if(messageJsonbean.getData()!=null&&messageJsonbean.getData().size()!=0) {
                            temp = messageJsonbean.getData();
                            Log.e(TAG, "TEMP=="+temp.toString());
                            messageDao.deleteAll();
                            for (int i=0;i<temp.size();i++){
                                messageDao.insert(temp.get(i));
                            }
                            //新建一个Message对象，存储需要发送的消息
                            Message message = new Message();
                            message.what = 1;
                            message.obj = temp;
                            //然后将消息发送出去
                            handler.sendMessage(message);
                        }else{
                            Log.i(TAG, "MSGLIST===="+list.toString());
                            Message message = new Message();
                            message.what = 1;
                            message.obj =list;
                            handler.sendMessage(message);
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(mContext,messageJsonbean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                }
            }).start();
        }
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
                meetingApplicationAndStatusDao.deleteAll();
                MeetingApplicationAndStatus application = new MeetingApplicationAndStatus();
                DateFormat dateFormat = new DateFormat();
                int i, j = 0;
                StatusAndDataHttpResponseObject<ArrayList<MeetingApplication>> arrayListStatusAndDataHttpResponseObject = service.getApplicationIdoing(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
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
                        Log.e(TAG,"doing - application["+i+"]-------"+application.toString());
                    }
                    //startActivity(new Intent(getActivity(), MyApplicationActivity.class));
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIwilldo(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
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
                        Log.e(TAG,"willdo-application["+i+"]-------"+application.toString());
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
                arrayListStatusAndDataHttpResponseObject = service.getApplicationIdone(s);
                if (arrayListStatusAndDataHttpResponseObject.getStatus() == 0) {
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
                        Log.e(TAG,"done-application["+i+"]-------"+application.toString());
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


    //消息更新
    private class ContentAdapter extends RecyclerView.Adapter<AdhibitionActivity.ContentAdapter.ContentHolder> {
        private List<com.oliveoa.pojo.Message> msg;
        public ContentAdapter(List<com.oliveoa.pojo.Message> msg) {
            this.msg = msg;
        }

        @Override
        public AdhibitionActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AdhibitionActivity.ContentAdapter.ContentHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_message, parent, false));
        }

        @Override
        public void onBindViewHolder(final AdhibitionActivity.ContentAdapter.ContentHolder holder, final int position) {
            holder.tvcontent.setText(msg.get(position).getMsg());
            //holder.tvtitle.setText(msg.get(position).getTitle());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.tvcontent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tvcontent.getText().toString().trim() + "----" + msg.get(position).toString());
                    customDialog(msg.get(position).getMsg());
                }
            });
        }

        @Override
        public int getItemCount() {
            if(msg.size()<3){
                return msg.size();
            }else {
                return 3;
            }
        }


        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView tvtitle, tvcontent;
            private LinearLayout cardView;

            public ContentHolder(View itemView) {
                super(itemView);
                //tvtitle = (TextView) itemView.findViewById(R.id.title);
                tvcontent = (TextView) itemView.findViewById(R.id.content);
                cardView = (LinearLayout) itemView.findViewById(R.id.card_view);
            }
        }
    }


    /**
     * 自定义对话框
     */
    private void customDialog(String content) {
        final Dialog dialog = new Dialog(getActivity(),R.style.Theme_AppCompat_Light_Dialog);
        View view = View.inflate(getActivity(), R.layout.dialog_normal, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        TextView tcontent = (TextView)view.findViewById(R.id.tcontent);
        TextView ttitle = (TextView)view.findViewById(R.id.tip);
        ttitle.setText("消息详情");
        tcontent.setText(content);
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(getActivity()).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(getActivity()).getScreenWidth() * 0.75f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
            /*cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });*/
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    class task extends TimerTask{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message message = new Message();
            message.what = 1;
            mhandler.sendMessage(message);
        }
    }
}
