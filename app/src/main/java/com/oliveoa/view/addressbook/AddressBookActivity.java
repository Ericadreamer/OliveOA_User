package com.oliveoa.view.addressbook;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.oliveoa.view.R;

public class AddressBookActivity extends Fragment {

    private ExpandableListView exlist_staff;
    private ImageView back;
    private Context mContext;
    private View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_address_book, container, false);
        initView();
        this.mContext = getActivity();
        return rootview;
    }

    private void initView() {

    }
}
