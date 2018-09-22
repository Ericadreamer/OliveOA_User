package com.oliveoa.view.documentmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.view.R;

import java.util.List;

public class IssueDoneActivity extends Fragment {
    private Context mContext;
    private View rootview;
    private RecyclerView mContentRv;
    private TextView ttitle,tcontext;
    private View listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.activity_issue_done, container, false);
        this.mContext = getActivity();

        initViews();
        initData();

        return rootview;
    }

    public void initViews() {
        ttitle = (TextView) rootview.findViewById(R.id.title);
        tcontext = (TextView) rootview.findViewById(R.id.content);

        mContentRv = (RecyclerView) rootview.findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContentRv.setAdapter(new ContentAdapter());

    }

    private class ContentAdapter extends RecyclerView.Adapter<IssueDoneActivity.ContentAdapter.ContentHolder> {
        @Override
        public IssueDoneActivity.ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (getItemCount() == 0) {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.notice_null, parent, false));
            } else {
                return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_document, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(final IssueDoneActivity.ContentAdapter.ContentHolder holder, final int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ContentHolder extends RecyclerView.ViewHolder {

            private TextView tvtitle, tvcontent;
            private CardView cardView;

            public ContentHolder(View itemView) {
                super(itemView);
                tvtitle = (TextView) itemView.findViewById(R.id.title);
                tvcontent = (TextView) itemView.findViewById(R.id.content);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
            }
        }

    }


    public void initData() {

    }
}
