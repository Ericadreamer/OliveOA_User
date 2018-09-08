package com.oliveoa.view.notice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveoa.view.R;
import com.oliveoa.view.TabLayoutBottomActivity;
import com.oliveoa.view.note.EditNoteActivity;
import com.oliveoa.view.notice.NoticeActivity;

public class NoticeActivity extends Fragment {
    private RecyclerView mContentRv;
    private Context mContext;
    private View rootview;
    private FloatingActionButton btn_fab;
    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_notice, container, false);
        this.mContext = getActivity();

        initData();

        return rootview;

    }

    public void initView() {
        //mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
        //mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_fab = (FloatingActionButton)rootview.findViewById(R.id.fab_add);
        //mContentRv.setAdapter(new ContentAdapter());


        //additem = (LinearLayout) findViewById(R.id.note_list);



        //点击事件
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("dhjhdjshd","dsdsdss") ;
                Intent intent = new Intent(mContext, AddNoticeActivity.class);
                startActivity(intent);
            }
        });

        /*additem.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNoteActivity.this, EditNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

    }

    public void onClickFab(View v){
        Intent intent = new Intent(getActivity(), AddNoticeActivity.class);
        startActivity(intent);

        //Snackbar.make(rootview.findViewById(R.id.fab_add), "Show The Snackbar", Snackbar.LENGTH_SHORT).show();
    }

    public void initData() {
initView();
    }
}
