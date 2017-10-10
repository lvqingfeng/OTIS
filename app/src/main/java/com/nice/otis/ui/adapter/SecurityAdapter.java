package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.bean.HeadlineListBean;
import com.nice.otis.databinding.ItemLayoutSecrityBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class SecurityAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutSecrityBinding>> {
    private List<HeadlineListBean.DataBean.HeadInfoBean> mList;

    public SecurityAdapter(List<HeadlineListBean.DataBean.HeadInfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutSecrityBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_secrity, parent, false);
        return new BindingViewHolder<>(ItemLayoutSecrityBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutSecrityBinding> holder, int position) {
        HeadlineListBean.DataBean.HeadInfoBean infoBean = mList.get(position);
        holder.getBinding().title.setText("标题:"+infoBean.getTitle());
        holder.getBinding().tag.setText("标签:"+infoBean.getTag_str());
        Glide.with(holder.mContext).load(infoBean.getImg_url()).into(holder.getBinding().icon);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
