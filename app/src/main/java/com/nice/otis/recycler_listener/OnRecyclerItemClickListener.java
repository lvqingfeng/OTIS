package com.nice.otis.recycler_listener;


import android.support.v7.widget.RecyclerView;

/**
 * 作者：lv
 * 创建时间：05月26日
 * 时间：10:09
 * 版本：v1.0.0
 * 类描述：RecycleView的点击事件
 * 修改时间：
 */
public abstract class OnRecyclerItemClickListener extends OnRecyclerItemListener {

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        super(recyclerView);
    }
    //实现长按事件的处理，暴露出点击事件的处理方式
    @Override
    public void onItemLongClick(RecyclerView.ViewHolder vh) {

    }
}
