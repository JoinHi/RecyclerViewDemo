package com.example.zhangzhongjie.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.shucheng.modularize.common.Module;

/**
 * Created by zhangzhongjie on 2018/3/19.
 */

public class ScrollModuleFactory {


    public static final int SNAP_DEFAULT = -1;//默认recyclerview滚动状态
    public static final int SNAP_START = 1;//滚动正常，滚动停止时item左对齐
    public static final int SNAP_CENTER = 2;//滚动正常，停止时item居中对齐
    public static final int SNAP_PAGER = 3;//viewpager 效果
    public static final int SNAP_GRID_PAGER = 4;//GridView 实现viewpager 效果


    public static ScrollModule getRecyclerViewModule(Context context, int orientation) {
        return getScrollModule(context, orientation, 1, SNAP_DEFAULT);
    }

    public static ScrollModule getViewPagerModule(Context context) {
        return getScrollModule(context, LinearLayoutManager.HORIZONTAL, 1, SNAP_PAGER);
    }

    public static ScrollModule getGridPagerModule(Context context, int spanCount) {
        return getScrollModule(context, LinearLayoutManager.HORIZONTAL, spanCount, SNAP_GRID_PAGER);
    }

    public static ScrollModule getScrollModule(Context context, int orientation, int mSpanCount, int snapPager) {
        return new ScrollModule(context, orientation, mSpanCount, snapPager);
    }

    public static class ScrollModule extends Module {


        private RecyclerView.Adapter mAdapter;
        private int mOrientation;
        private int mSpanCount;
        private int mSnapPager;

        public ScrollModule(Context context, int orientation, int spanCount, int snapPager) {
            super(context);
            mOrientation = orientation;
            mSpanCount = spanCount;
            mSnapPager = snapPager;
        }

        @Override
        public void requestRefresh() {
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }

        public void setAdapter(RecyclerView.Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, boolean attachToRoot) {
            if (mContentView == null) {
                mContentView = LayoutInflater.from(mContext).inflate(R.layout.module_scroll_list_layout, parent, false);
            }
            RecyclerView recyclerView = (RecyclerView) mContentView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, mOrientation, false);
            SnapHelper snapHelper = null;
            switch (mSnapPager) {
                case SNAP_DEFAULT:
                    break;
                case SNAP_START:
                    snapHelper = new StartSnapHelper();
                    break;
                case SNAP_CENTER:
                    break;
                case SNAP_PAGER:
                    snapHelper = new PagerSnapHelper();
                    break;
                case SNAP_GRID_PAGER:
                    layoutManager = new GridLayoutManager(mContext, mSpanCount, LinearLayoutManager.HORIZONTAL, false);
                    snapHelper = new GridPagerSnapHelper();
                    break;
            }
            recyclerView.setLayoutManager(layoutManager);
            if (snapHelper != null) {
                snapHelper.attachToRecyclerView(recyclerView);
            }


            recyclerView.setAdapter(mAdapter);
//        recyclerView.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
            return mContentView;
        }

    }

}
