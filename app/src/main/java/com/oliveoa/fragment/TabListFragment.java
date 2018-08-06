package com.oliveoa.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.oliveoa.view.R;

public class TabListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String EXTRA_CONTENT = "content";

    private ListView mContentLv;
    private Context mContext;

    public static TabListFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabListFragment tabContentFragment = new TabListFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_list, null);
        this.mContext = getActivity();

        mContentLv = (ListView) contentView.findViewById(R.id.lv_content);
        mContentLv.setOnItemClickListener(this);
        ViewCompat.setNestedScrollingEnabled(mContentLv, true);
        mContentLv.setAdapter(new ContentAdapter());

        return contentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO
    }

    private class ContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_wait, null);
            contentView.findViewById(R.id.application_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent detailIntent = new Intent(getActivity(), PaletteDetailActivity.class);
                    detailIntent.putExtra(PaletteDetailActivity.EXTRA_INDEX, position);
                    startActivity(detailIntent);*/
                    Toast.makeText(mContext, "您点击了该列表", Toast.LENGTH_SHORT).show();
                }
            });
            return contentView;
        }
    }
}
