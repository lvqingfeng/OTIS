package com.nice.otis.recycler_listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：lv
 * 创建时间：05月26日
 * 时间：9:56
 * 版本：v2.0.0
 * 类描述：
 * 修改时间：2016年10月12日 22:58:49 对onTouchEvent事件进行的修改，防止父控件会重复执行点击事件
 */
public abstract class OnRecyclerItemListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && child.isEnabled()) {
                if (!child.dispatchTouchEvent(e)) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemClick(vh);
                }
            }
            return true;
        }

        //长点击事件
        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                if (!child.dispatchTouchEvent(e)) {
                    RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
                    onItemLongClick(vh);
                }
            }
        }


    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh);

    public abstract void onItemLongClick(RecyclerView.ViewHolder vh);

}
