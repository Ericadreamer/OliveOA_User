package com.oliveoa.view.documentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.view.R;
import com.oliveoa.view.note.MyNoteActivity;
import com.oliveoa.view.notice.MySubmissionActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DraftActivity extends AppCompatActivity {
    private RecyclerView mContentRv;
    private ImageView back, add;
    private View listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        initView();
        initData();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.iback);
        add = (ImageView) findViewById(R.id.add);

        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new DraftContentAdapter());

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraftActivity.this, DocumentManagementActivity.class);
                intent.getIntExtra("index", 0);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DraftActivity.this, DraftAddActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void initData() {

    }

    private class DraftContentAdapter extends RecyclerView.Adapter<DraftContentAdapter.ContentHolder> {


        @Override
        public DraftContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DraftContentAdapter.ContentHolder(LayoutInflater.from(DraftActivity.this)
                    .inflate(R.layout.item_document, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }


        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView itemTitle, itemContext;
            private CardView item_document;

            public ContentHolder(View itemView) {
                super(itemView);
                itemTitle = (TextView) itemView.findViewById(R.id.title);
                itemContext = (TextView) itemView.findViewById(R.id.content);
                item_document = (CardView) itemView.findViewById(R.id.card_view);
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
