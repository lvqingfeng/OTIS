package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.base.ConsumptionBean;
import com.nice.otis.databinding.ItemLayoutConsumptionBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class ConsumptionAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutConsumptionBinding>> {
    private List<ConsumptionBean.ListBean> mList;

    public ConsumptionAdapter(List<ConsumptionBean.ListBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutConsumptionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_layout_consumption,parent,false);
        return new BindingViewHolder<>(ItemLayoutConsumptionBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutConsumptionBinding> holder, int position) {
        ConsumptionBean.ListBean listBean = mList.get(position);
        holder.getBinding().content.setText(listBean.getCreate_time()
                +"查看「"+listBean.getConsumer_title()+"」花费积分:"+listBean.getPoint());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
