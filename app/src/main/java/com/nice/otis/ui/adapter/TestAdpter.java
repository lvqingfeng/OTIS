package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.databinding.ItemLayoutTextBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/8/23
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class TestAdpter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutTextBinding>> {
    private List<String>  mList;

    public TestAdpter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutTextBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_text,parent,false);
        return new BindingViewHolder<>(ItemLayoutTextBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutTextBinding> holder, int position) {
        holder.getBinding().text.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
