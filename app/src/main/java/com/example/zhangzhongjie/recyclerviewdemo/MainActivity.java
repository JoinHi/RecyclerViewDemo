package com.example.zhangzhongjie.recyclerviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class MainActivity extends AppCompatActivity {

    int count = 8;

    int next;

    private boolean isTouch = false;
    private RecyclerView recyclerView1;
    private MyAdapter1 adapter1;
    private LinearLayoutManager linearLayoutManager1;
    private RecyclerView recyclerView2;
    private MyAdapter1 adapter2;
    private LinearLayoutManager linearLayoutManager2;
    private RecyclerView recyclerView3;
    private MyAdapter1 adapter3;
    private LinearLayoutManager linearLayoutManager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initView2();
        initView3();
        initButton();
        initEvent();
    }

    private void initView3() {
        recyclerView3 = findViewById(R.id.recycler3);
        adapter3 = new MyAdapter1();
        recyclerView3.setAdapter(adapter3);
        linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(linearLayoutManager3);
//        LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
        MySnapHelper linearSnapHelper1 = new MySnapHelper();
//        StartSnapHelper linearSnapHelper1 = new StartSnapHelper();
//        PagerSnapHelper linearSnapHelper1 = new PagerSnapHelper();
        linearSnapHelper1.attachToRecyclerView(recyclerView3);
    }

    private void initView2() {
        recyclerView2 = findViewById(R.id.recycler2);
        adapter2 = new MyAdapter1();
        recyclerView2.setAdapter(adapter2);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager2);
//        LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
        StartSnapHelper linearSnapHelper1 = new StartSnapHelper();
//        PagerSnapHelper linearSnapHelper1 = new PagerSnapHelper();
        linearSnapHelper1.attachToRecyclerView(recyclerView2);
    }

    private void initEvent() {
        Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return !isTouch;
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (recyclerView1 == null)
                            return;
                        recyclerView1.smoothScrollToPosition(linearLayoutManager1.findFirstCompletelyVisibleItemPosition() + 1);
                    }
                });


        recyclerView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isTouch = false;
                        break;
                }
                return false;
            }
        });

    }

    private void initButton() {
    }

    private void initView() {
        recyclerView1 = findViewById(R.id.recycler1);
        adapter1 = new MyAdapter1();
        recyclerView1.setAdapter(adapter1);
//        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager1 = new ScaleLayoutManager(this, 0);
        recyclerView1.setLayoutManager(linearLayoutManager1);
//        LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
//        StartSnapHelper linearSnapHelper1 = new StartSnapHelper();
//        PagerSnapHelper linearSnapHelper1 = new PagerSnapHelper();
//        linearSnapHelper1.attachToRecyclerView(recyclerView1);
//        recyclerView1.scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count - 1);
//        recyclerView1.smoothScrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count);
    }

    public class MyAdapter1 extends RecyclerView.Adapter<ViewHolder1> {

        private int currentPosition;

        @Override
        public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_recyclerview1, parent, false);
            return new ViewHolder1(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder1 holder, int position) {
            holder.textView.setText(String.valueOf(position % count));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder1(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item1);
        }

    }

    public class StartSnapHelper extends LinearSnapHelper {

        private OrientationHelper mHorizontalHelper;

        @Override
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
            final int childCenter = getHorizontalHelper(layoutManager).getDecoratedStart(targetView);
            int[] position = super.calculateDistanceToFinalSnap(layoutManager, targetView);
            position[0] = childCenter;
            return position;
        }

        private int distanceToStart(@NonNull RecyclerView.LayoutManager layoutManager,
                                    @NonNull View targetView, OrientationHelper helper) {
            final int childCenter = helper.getDecoratedStart(targetView)
                    + (helper.getDecoratedMeasurement(targetView) / 2);
            final int containerCenter;
            if (layoutManager.getClipToPadding()) {
                containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
            } else {
                containerCenter = helper.getEnd() / 2;
            }
            return containerCenter;
        }

        @NonNull
        private OrientationHelper getHorizontalHelper(
                @NonNull RecyclerView.LayoutManager layoutManager) {
            mHorizontalHelper = mHorizontalHelper;
            if (mHorizontalHelper == null) {
                mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
            }
            return mHorizontalHelper;
        }
    }
}
