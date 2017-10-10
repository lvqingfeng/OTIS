package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.bean.BrandAttrBean;
import com.nice.otis.databinding.ItemLayoutBrandAttrBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/5
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class BrandAttrAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutBrandAttrBinding>> {
    private List<BrandAttrBean.InfoBean> mList;

    public BrandAttrAdapter(List<BrandAttrBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutBrandAttrBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_brand_attr,parent,false);
        return new BindingViewHolder<>(ItemLayoutBrandAttrBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutBrandAttrBinding> holder, int position) {
        BrandAttrBean.InfoBean infoBean = mList.get(position);
        holder.getBinding().title.setText(infoBean.getTitle());
        holder.getBinding().info.setText(infoBean.getAttr_value());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
