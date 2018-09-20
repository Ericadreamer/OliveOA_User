package com.oliveoa.view;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.oliveoa.view.addressbook.AddressBookActivity;
import com.oliveoa.view.mine.MineActivity;
import com.oliveoa.view.notice.NoticeActivity;
import com.oliveoa.view.AdhibitionActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*public int i;
    private RadioButton navButton0,navButton1,navButton2,navButton3;
    private ArrayList<RadioButton> navButtons;
    private Fragment fragment;
    private ArrayList<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navButtons = new ArrayList<RadioButton>();
        navButton0 = (RadioButton) findViewById(R.id.nav_button0);  navButtons.add(navButton0);
        navButton1 = (RadioButton) findViewById(R.id.nav_button1);  navButtons.add(navButton1);
        navButton2 = (RadioButton) findViewById(R.id.nav_button2);  navButtons.add(navButton2);
        navButton3 = (RadioButton) findViewById(R.id.nav_button3);  navButtons.add(navButton3);

        listFragment = new ArrayList<Fragment>();
        fragment = new AdhibitionActivity();    listFragment.add(fragment);
        fragment = new NoticeActivity();    listFragment.add(fragment);
        fragment = new AddressBookActivity();  listFragment.add(fragment);
        fragment = new MyProtocolActivity();   listFragment.add(fragment);

        //默认激活第一个按钮 和 第一个fragment
        navButton0.setChecked(true);
        displayFragment(0);

        //监听器
        initListener();

    }

    public void initListener() {

        navButton0.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment( 0 );

            }
        });

        navButton1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment( 1 );

            }
        });

        navButton2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment( 2 );

            }
        });

        navButton3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment( 3 );

            }
        });

    }

    public void displayFragment(int positon) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, listFragment.get(positon) ).commit();

    }*/
}
