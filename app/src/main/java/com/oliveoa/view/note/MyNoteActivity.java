package com.oliveoa.view.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.greendao.NoteInfoDao;
import com.oliveoa.pojo.NoteInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyNoteActivity extends AppCompatActivity {
    private ImageView back,add;
    private RecyclerView mContentRv;
    private FloatingActionButton btn_fab;
    private List<NoteInfo> noteInfos;
    private NoteInfoDao noteInfoDao;
    private String TAG = this.getClass().getSimpleName();
    private View listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);

        initView();
    }

    public void initView() {
        Log.d(TAG,"reloading view");
        back = (ImageView) findViewById(R.id.iback);
        //add = (ImageView) findViewById(R.id.iadd);
        btn_fab = (FloatingActionButton)findViewById(R.id.fab_add);
        initData();
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
                NoteInfo noteInfo = new NoteInfo();
                Intent intent = new Intent(MyNoteActivity.this, EditNoteActivity.class);
                intent.putExtra("index",1);
                intent.putExtra("noteInfo",noteInfo);
                startActivity(intent);
                finish();
            }
        });

    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{


        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(MyNoteActivity.this)
                    .inflate(R.layout.item_note, parent, false));
        }

        @Override
        public void onBindViewHolder(final ContentAdapter.ContentHolder holder, final int position) {
            //holder.itemTv.setText("Item "+new DecimalFormat("00").format(position));
             holder.itemContent.setText(noteInfos.get(position).getContent());
             holder.itemTime.setText(noteInfos.get(position).getUpdatetime());

             holder.item_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,holder.itemContent.getText().toString().trim()+"----"+noteInfos.get(position).toString());
                    Intent intent = new Intent(MyNoteActivity.this, EditNoteActivity.class);
                    intent.putExtra("index",0);
                    intent.putExtra("noteinfo",noteInfos.get(position));
                    startActivity(intent);
                    finish();

                }
            });
             //长按删除
            holder.item_note.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG,holder.itemContent.getText().toString().trim()+"----"+noteInfos.get(position).toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyNoteActivity.this);
                    builder.setTitle("警告");
                    builder.setMessage("您正在试图删除这条便签，确定删除吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            noteInfoDao.deleteAll();
                            for (int i =0;i<noteInfos.size();i++){
                                if(!noteInfos.get(i).equals(noteInfos.get(position))){
                                    noteInfoDao.insert(noteInfos.get(i));
                                    Log.e(TAG,"depleting");
                                }
                            }
                            finish();
                            Intent intent = new Intent(MyNoteActivity.this, MyNoteActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                    return false;
                }
            });


        }

        @Override
        public int getItemCount() {
            return noteInfos.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            private TextView itemContent,itemTime;
            private CardView item_note;

            public ContentHolder(View itemView) {
                super(itemView);
               itemContent = (TextView) itemView.findViewById(R.id.note);
               itemTime = (TextView) itemView.findViewById(R.id.time);
               item_note = (CardView) itemView.findViewById(R.id.card_view);
               listview = itemView;
            }
        }

    }


    public void initData() {
        noteInfoDao = EntityManager.getInstance().getNoteInfoDao();
        noteInfos = noteInfoDao.queryBuilder().orderDesc(NoteInfoDao.Properties.Orderby).list();
        Log.e(TAG,noteInfos.toString());

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
