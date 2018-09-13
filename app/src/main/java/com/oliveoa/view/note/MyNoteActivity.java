package com.oliveoa.view.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.notice.AddNoticeActivity;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MyNoteActivity extends AppCompatActivity {
    private ImageView back,add;
    private RecyclerView mContentRv;
    private FloatingActionButton btn_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);

        initData();
        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        //add = (ImageView) findViewById(R.id.iadd);
        btn_fab = (FloatingActionButton)findViewById(R.id.fab_add);

        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new ContentAdapter());

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNoteActivity.this, TabLayoutBottomActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
                finish();
            }
        });


        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNoteActivity.this, EditNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(MyNoteActivity.this).inflate(R.layout.item_note, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {
            //holder.itemTv.setText("Item "+new DecimalFormat("00").format(position));
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            private TextView itemTv;

            public ContentHolder(View itemView) {
                super(itemView);
                itemTv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }

    }


    public void initData() {

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
