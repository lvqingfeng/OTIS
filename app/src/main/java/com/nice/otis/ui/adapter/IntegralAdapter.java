package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.bean.IntegralBean;
import com.nice.otis.databinding.ItemLayoutIntegralBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class IntegralAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutIntegralBinding>> {
    private List<IntegralBean.ListBean> mList;

    public IntegralAdapter(List<IntegralBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutIntegralBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout__integral,parent,false);
        return new BindingViewHolder<>(ItemLayoutIntegralBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutIntegralBinding> holder, int position) {
        IntegralBean.ListBean listBean = mList.get(position);
        holder.getBinding().point.setText(listBean.getPoint()+"积分");
        holder.getBinding().money.setText(listBean.getPrice()+"元");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
