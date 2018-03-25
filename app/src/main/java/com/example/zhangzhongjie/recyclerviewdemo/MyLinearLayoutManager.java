package com.example.zhangzhongjie.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by zhangzhongjie on 2018/3/20.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        RecyclerView.State state) {
//            if (mOrientation == VERTICAL) {
//                return 0;
//            }
//            return scrollBy(dx, recycler, state);

        Log.d("-------------", "scrollHorizontallyBy() called with: dx = [" + dx + "], recycler = [" + recycler + "], state = [" + state + "]");




        return super.scrollHorizontallyBy(dx, recycler, state);
    }


//    int scrollBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        if (getChildCount() == 0 || dy == 0) {
//            return 0;
//        }
//        mLayoutState.mRecycle = true;
//        ensureLayoutState();
//        final int layoutDirection = dy > 0 ? LayoutState.LAYOUT_END : LayoutState.LAYOUT_START;
//        final int absDy = Math.abs(dy);
//        updateLayoutState(layoutDirection, absDy, true, state);
//        final int consumed = mLayoutState.mScrollingOffset
//                + fill(recycler, mLayoutState, state, false);
//        if (consumed < 0) {
//            if (DEBUG) {
//                Log.d(TAG, "Don't have any more elements to scroll");
//            }
//            return 0;
//        }
//        final int scrolled = absDy > consumed ? layoutDirection * consumed : dy;
//        mOrientationHelper.offsetChildren(-scrolled);
//        if (DEBUG) {
//            Log.d(TAG, "scroll req: " + dy + " scrolled: " + scrolled);
//        }
//        mLayoutState.mLastScrollDelta = scrolled;
//        return scrolled;
//    }
}
