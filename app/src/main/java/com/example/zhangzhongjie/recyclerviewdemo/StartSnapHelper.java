package com.example.zhangzhongjie.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerview 左对齐的帮组类
 */
public class StartSnapHelper extends LinearSnapHelper {

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                targetView.getLayoutParams();
        int distanceToStart = layoutManager.getDecoratedLeft(targetView) - params.leftMargin;
        int[] position = new int[2];
        position[0] = distanceToStart;
        return position;
    }
}