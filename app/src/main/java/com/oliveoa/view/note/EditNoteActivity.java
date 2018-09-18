package com.oliveoa.view.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.oliveoa.greendao.NoteInfoDao;
import com.oliveoa.pojo.NoteInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EditNoteActivity extends AppCompatActivity {
    private ImageView back,delete;
    private EditText enote;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private NoteInfo noteInfo;
    private NoteInfoDao noteInfoDao;
    private List<NoteInfo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        index = getIntent().getIntExtra("index",index);//修改0，创建新的便签1
        noteInfo =getIntent().getParcelableExtra("noteinfo");
        Log.e(TAG,"INDEX="+index);
        Log.e(TAG,"noteinfo="+noteInfo);
        initView();

    }

    public void initView() {
        back = (ImageView) findViewById(R.id.iback);
        delete = (ImageView) findViewById(R.id.delete);
        enote = (EditText) findViewById(R.id.note_edit);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                back();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                enote.setText("");
            }
        });
    }

    private void back() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);// 输出北京时间
        Log.e(TAG,"enote="+enote.getText().toString().trim());
        if(!enote.getText().toString().trim().equals("")) {
            if (index == 0) {
                NoteInfo temp = new NoteInfo();
                temp.setContent(enote.getText().toString().trim());
                temp.setUpdatetime(f.format(date));
                temp.setOrderby(list.size()-1);
                Log.i("info:", temp.toString());
                list = noteInfoDao.queryBuilder().list();
                if (list != null) {
                    noteInfoDao.deleteAll();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getContent().equals(noteInfo.getContent()) && list.get(i).getUpdatetime().equals(noteInfo.getUpdatetime())) {

                        } else {
                            noteInfoDao.insert(list.get(i));
                        }
                    }
                    noteInfoDao.insert(temp);
                }
            }
            if (index == 1) {
                NoteInfo temp = new NoteInfo();
                temp.setContent(enote.getText().toString().trim());
                temp.setUpdatetime(f.format(date));
                Log.e(TAG,list.toString());
                if(list==null){
                     temp.setOrderby(0);
                }else{
                    temp.setOrderby(list.size());
                }

                noteInfoDao.insert(temp);
            }
        }
        Intent intent = new Intent(EditNoteActivity.this, MyNoteActivity.class);
        startActivity(intent);
        finish();
    }

    public void initData() {
        noteInfoDao = EntityManager.getInstance().getNoteInfoDao();
        list = noteInfoDao.queryBuilder().list();
        if(index==0) {
            enote.setText(noteInfo.getContent());
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
