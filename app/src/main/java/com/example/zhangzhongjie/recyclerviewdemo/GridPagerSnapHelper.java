package com.example.zhangzhongjie.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nd.android.pandareaderlib.util.Log;

/**
 * Created by zhangzhongjie on 2018/3/22.
 *
 * 扩展GridLayoutManager支持ViewPager效果，目前仅支持单列和双列
 */

public class GridPagerSnapHelper extends PagerSnapHelper {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int spanCount = 1;
    private int columnsCount = 1;

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mLayoutManager = recyclerView.getLayoutManager();
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mLayoutManager instanceof GridLayoutManager) {
                    spanCount = ((GridLayoutManager) mLayoutManager).getSpanCount();
                    int childCount = recyclerView.getChildCount();
                    if (childCount > 0){
                        columnsCount = getTotalSpace()/getDecoratedMeasurement(recyclerView.getChildAt(0));
                    }
                }
                Log.i("----------------------columnsCount="+columnsCount);
            }
        });

        Log.i("++++++++++spanCount = "+spanCount+";columnsCount = "+columnsCount);
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = super.calculateDistanceToFinalSnap(layoutManager, targetView);

        int targetViewPosition = mRecyclerView.getChildAdapterPosition(targetView);
        int offset = 0;


        if (targetViewPosition % (columnsCount * spanCount) == 0) {
            offset = getDecoratedStart(targetView);
        } else if (targetViewPosition % (columnsCount * spanCount) == spanCount) {
            offset = getDecoratedStart(targetView) + (getDecoratedMeasurement(targetView)) - getTotalSpace();
        }


        out[0] = offset;
        Log.i("----------------out2=" + out[0]);
        return out;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        Log.i("----------------findTargetSnapPosition1=" + position);
        final boolean forwardDirection;
        if (layoutManager.canScrollHorizontally()) {
            forwardDirection = velocityX > 0;
        } else {
            forwardDirection = velocityY > 0;
        }
        if (forwardDirection) {
            position += (spanCount * columnsCount - 1);
        }
        Log.i("----------------findTargetSnapPosition2=" + position);
        return position;

    }

    public int getDecoratedStart(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return mLayoutManager.getDecoratedLeft(view) - params.leftMargin;
    }

    public int getDecoratedMeasurement(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return mLayoutManager.getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    public int getTotalSpace() {
        return mLayoutManager.getWidth() - mLayoutManager.getPaddingLeft()
                - mLayoutManager.getPaddingRight();
    }


}
