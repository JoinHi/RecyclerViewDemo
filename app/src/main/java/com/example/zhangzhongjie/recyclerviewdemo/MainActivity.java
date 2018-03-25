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
import android.widget.Toast;

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
    private MyLinearLayoutManager linearLayoutManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initButton();
//        initEvent();
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
        findViewById(R.id.btn_recycler1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.scrollToPosition(next++);
            }
        });
    }

    private void initView() {
        recyclerView1 = findViewById(R.id.recycler1);
        adapter1 = new MyAdapter1();
        recyclerView1.setAdapter(adapter1);
//        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        linearLayoutManager1 = new ScaleLayoutManager(this,0, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager1 = new MyLinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager1);

//        LinearSnapHelper linearSnapHelper1 = new LinearSnapHelper();
//        StartSnapHelper linearSnapHelper1 = new StartSnapHelper();
        PagerSnapHelper linearSnapHelper1 = new PagerSnapHelper();
//        MyPagerSnapHelper linearSnapHelper1 = new MyPagerSnapHelper();
        linearSnapHelper1.attachToRecyclerView(recyclerView1);
//        recyclerView1.setNestedScrollingEnabled(false);
//        recyclerView1.scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % count);
        recyclerView1.scrollToPosition(count*80);
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
        public void onBindViewHolder(ViewHolder1 holder, final int position) {
            holder.textView.setText(String.valueOf(position % count));
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),String.valueOf(position%count),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
//            return count;
        }

        public int getCurrentPosition() {
            return 2;
        }
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        public TextView textView;
        public View rootView;

        public ViewHolder1(View itemView) {
            super(itemView);
            rootView = itemView;
            textView = itemView.findViewById(R.id.tv_item1);
        }

    }

}
