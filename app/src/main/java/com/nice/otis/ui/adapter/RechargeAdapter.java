package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.base.RechargeBean;
import com.nice.otis.databinding.ItemLayoutConsumptionBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class RechargeAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutConsumptionBinding>> {
    private List<RechargeBean.ListBean> mList;

    public RechargeAdapter(List<RechargeBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutConsumptionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_consumption,parent,false);
        return new BindingViewHolder<>(ItemLayoutConsumptionBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutConsumptionBinding> holder, int position) {
        RechargeBean.ListBean listBean = mList.get(position);
        holder.getBinding().content.setText("您与"+listBean.getCreate_time()+"成功购买积分"+listBean.getPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
