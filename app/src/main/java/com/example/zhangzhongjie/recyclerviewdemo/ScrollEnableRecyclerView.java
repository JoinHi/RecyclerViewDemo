package com.example.zhangzhongjie.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangzhongjie on 2018/3/17.
 */

public class ScrollEnableRecyclerView extends RecyclerView {
    public ScrollEnableRecyclerView(Context context) {
        super(context);
    }

    public ScrollEnableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEnableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        return false;
        if (e.getAction() == MotionEvent.ACTION_MOVE){
            requestDisallowInterceptTouchEvent(false);
            return false;
        }
        return super.onTouchEvent(e);
    }
}
