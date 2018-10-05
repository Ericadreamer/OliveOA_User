package com.oliveoa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.common.ContactHttpResponseObject;
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.MessageService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.jsonbean.ContactJsonBean;
import com.oliveoa.jsonbean.MessageJsonbean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.ScreenSizeUtils;
import com.oliveoa.view.documentmanagement.ReceiveActivity;
import com.oliveoa.view.documentmanagement.ReceiveDocumentActivity;
import com.oliveoa.view.myapplication.AddApplicationActivity;
import com.oliveoa.view.myapplication.WaitActivity1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MessageListActivity extends AppCompatActivity {

    private RecyclerView mContentRv;
    private ImageView back;
    private View listview;
    private Context mcontext;

    private TextView ttitle,tcontext,tvtip;
    private String TAG = this.getClass().getSimpleName();
    private List<com.oliveoa.pojo.Message> list;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<com.oliveoa.pojo.Message>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(list!=null&list.size()!=0){
                        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(mcontext));
                        mContentRv.setAdapter(new MessageListActivity.ContentAdapter(list));
                    }else{
                        tvtip.setVisibility(View.VISIBLE);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        mcontext =this;
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        tvtip = (TextView)findViewById(R.id.tvtip);

        initData();
        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageListActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();

            }
        });
    }
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                ArrayList<com.oliveoa.pojo.Message> temp = new ArrayList<>();
                //Todo Service
                MessageService service = new MessageService();
                //Todo Service.Method
                MessageJsonbean messageJsonbean = service.getMessagetome(s,0);
                //ToCheck JsonBean.getStatus()
                if (messageJsonbean.getStatus() == 0) {
                    //Log.i("DOCUMENT=",messageJsonbean.getData().toString());
                    if(messageJsonbean.getData()!=null) {
                        temp = messageJsonbean.getData();
                        Log.e(TAG, temp.toString());
                        //新建一个Message对象，存储需要发送的消息
                        Message message = new Message();
                        message.what = 1;
                        message.obj = temp;
                        //然后将消息发送出去
                        handler.sendMessage(message);
                    }else{
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(mcontext,messageJsonbean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
    }

    private class ContentAdapter extends RecyclerView.Adapter<MessageListActivity.ContentAdapter.ContentHolder> {
        private List<com.oliveoa.pojo.Message> msg;
        public ContentAdapter(List<com.oliveoa.pojo.Message> msg) {
            this.msg = msg;
        }

        @Override
        public MessageListActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MessageListActivity.ContentAdapter.ContentHolder(LayoutInflater.from(MessageListActivity.this)
                    .inflate(R.layout.item_message, parent, false));
        }

        @Override
        public void onBindViewHolder(final MessageListActivity.ContentAdapter.ContentHolder holder, final int position) {
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
            return msg.size();
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
        final Dialog dialog = new Dialog(MessageListActivity.this,R.style.Theme_AppCompat_Light_Dialog);
        View view = View.inflate(MessageListActivity.this, R.layout.dialog_normal, null);
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
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MessageListActivity.this).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(MessageListActivity.this).getScreenWidth() * 0.75f);
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
