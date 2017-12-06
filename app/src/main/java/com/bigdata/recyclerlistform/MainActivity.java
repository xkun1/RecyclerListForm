package com.bigdata.recyclerlistform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigdata.recyclerlistform.adapter.LIstRoomAdapter;
import com.bigdata.recyclerlistform.listroom.inface.OnTableViewListener;
import com.bigdata.recyclerlistform.listroom.widget.CustomHorizontalScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //数目
    int len = 100;
    ListView mListView;
    LIstRoomAdapter roomAdapter;
    CustomHorizontalScrollView mScrollView;

    //记录滑动的位置
    private int mScrollViewx, mScrollViewy;

    OnTableViewListener tableViewListener;
    private ArrayList<HorizontalScrollView> mScrollViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mScrollView = (CustomHorizontalScrollView) findViewById(R.id.hushenListItemScroll);
        mListView.setOnScrollListener(onScrollListener);
        initAdapter();
        mListView.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        roomAdapter = new LIstRoomAdapter(this, len);
        roomAdapter.setTableViewListener(new OnTableViewListener() {
            @Override
            public void onTableViewScrollChange(int x, int y) {
                //头部下面的hs滑动
                changeAllScrollView(x, y);
            }
        });
        mScrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.onScrollChangeListener() {
            @Override
            public void onScrollChanged(HorizontalScrollView scrollView, int x, int y) {
                changeAllScrollView(x, y);
            }
        });
        roomAdapter.setTableViewCreatedListener(new LIstRoomAdapter.OnTableViewCreatedListener() {
            @Override
            public void onTableViewCreatedCompleted(CustomHorizontalScrollView mScrollView) {
                mScrollViews.add(mScrollView);
            }
        });
        roomAdapter.setItemClick(new LIstRoomAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            changeAllScrollView(mScrollViewx, mScrollViewy);
        }
    };

    /**
     * 改变位置
     *
     * @param x
     * @param y
     */
    private void changeAllScrollView(int x, int y) {
        mScrollViewx = x;
        mScrollViewy = y;
        if (mScrollViews.size() > 0) {
            if (tableViewListener != null) {
                tableViewListener.onTableViewScrollChange(x, y);
            }
            for (int i = 0; i < mScrollViews.size(); i++) {
                HorizontalScrollView scrollView = mScrollViews.get(i);
                //listview中的横向滑动
                scrollView.scrollTo(x, y);
                //listview头部的横向滑动
                mScrollView.scrollTo(x, y);
            }
        }
    }
}
