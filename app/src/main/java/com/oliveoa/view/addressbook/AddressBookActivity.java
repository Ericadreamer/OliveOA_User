package com.oliveoa.view.addressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.pojo.ContactInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.view.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressBookActivity extends Fragment {

    private ExpandableListView exlist_staff;
    private ImageView next;
    private Context mContext;
    private View rootview;

    private ListView listView;
    private SideBar sideBar;
    private ArrayList<User> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_address_book, container, false);
        this.mContext = getActivity();

        initView();
        initData();

        return rootview;
    }

    public void initView() {
        next = (ImageView) rootview.findViewById(R.id.inext);
        listView = (ListView) rootview.findViewById(R.id.listView);
        sideBar = (SideBar) rootview.findViewById(R.id.side_bar);
        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < list.size(); i++) {
                    if (selectStr.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                        listView.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DepartmentEmployeeActivity.class);
                startActivity(intent);
            }
        });


    }

    public void initData() {
        ContactInfoDao contactInfoDao = EntityManager.getInstance().getContactInfo();
        List<ContactInfo> contactInfos = contactInfoDao.queryBuilder().list();
        list = new ArrayList<>();
        for(int i = 0;i<contactInfos.size();i++){
            list.add(new User(contactInfos.get(i).getName(),contactInfos.get(i).getEid()));

        }
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        SortAdapter adapter = new SortAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
    }
