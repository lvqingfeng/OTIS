package com.nice.otis.recycler_listener;

import android.support.v7.widget.RecyclerView;

/**
 * 作者：lv
 * 创建时间：05月26日
 * 时间：10:12
 * 版本：v1.0.0
 * 类描述：RecyclerView的长按点击事件
 * 修改时间：
 */
public abstract class OnRecyclerItemLongClickListener extends OnRecyclerItemListener {

    public OnRecyclerItemLongClickListener(RecyclerView recyclerView) {
        super(recyclerView);
    }
    //实现点击事件的处理，暴露出长按事件的处理方式
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh) {

    }

}
