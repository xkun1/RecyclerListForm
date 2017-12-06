package com.bigdata.recyclerlistform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.bigdata.recyclerlistform.R;
import com.bigdata.recyclerlistform.listroom.inface.OnTableViewListener;
import com.bigdata.recyclerlistform.listroom.widget.CustomHorizontalScrollView;

/**
 * user:kun
 * Date:06/12/2017 or 9:44 AM
 * email:hekun@gamil.com
 * Desc:
 */

public class LIstRoomAdapter extends BaseAdapter {
    private Context mContext;
    private int size;
    private LayoutInflater inflater;
    private OnTableViewCreatedListener tableViewCreatedListener;
    private OnTableViewListener tableViewListener;
    private OnItemClick itemClick;

    public LIstRoomAdapter(Context mContext, int size) {
        this.mContext = mContext;
        this.size = size;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return size;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setTableViewCreatedListener(OnTableViewCreatedListener tableViewCreatedListener) {
        this.tableViewCreatedListener = tableViewCreatedListener;
    }

    public void setTableViewListener(OnTableViewListener tableViewListener) {
        this.tableViewListener = tableViewListener;
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.market_hushen_list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            tableViewCreatedListener.onTableViewCreatedCompleted(viewHolder.scrollView);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //点击事件回调
        viewHolder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClick(i);
            }
        });
        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClick(i);
            }
        });
        return view;
    }

    private class ViewHolder {
        CustomHorizontalScrollView scrollView;
        LinearLayout layoutClick;
        LinearLayout item_layout;

        public ViewHolder(View view) {
            view.setTag(this);
            scrollView=view.findViewById(R.id.hushenListItemScroll);
            item_layout =view.findViewById(R.id.item_layout);
            layoutClick =scrollView.findViewById(R.id.liner_layout);
            scrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.onScrollChangeListener() {
                @Override
                public void onScrollChanged(HorizontalScrollView scrollView, int x, int y) {
                    tableViewListener.onTableViewScrollChange(x, y);
                }
            });
        }
    }

    /**
     * 将CustomHorizontalScrollView添加到监听器里
     */
    public interface OnTableViewCreatedListener {
        void onTableViewCreatedCompleted(CustomHorizontalScrollView mScrollView);
    }
    public interface OnItemClick {
        void onItemClick(int position);
    }
}
