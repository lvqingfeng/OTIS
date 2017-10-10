package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.bean.RankListBean;
import com.nice.otis.databinding.LayoutRankListBinding;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class RankListAdapter extends RecyclerView.Adapter<BindingViewHolder<LayoutRankListBinding>> {
    private List<RankListBean.InfoBean> mList;

    public RankListAdapter(List<RankListBean.InfoBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<LayoutRankListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rank_list, parent, false);
        return new BindingViewHolder<>(LayoutRankListBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<LayoutRankListBinding> holder, int position) {
        RankListBean.InfoBean infoBean = mList.get(position);
        holder.getBinding().title.setText("标题:\u3000" + infoBean.getTitle());
        holder.getBinding().reason.setText("原 因:\u3000" + infoBean.getReason());
        holder.getBinding().answer.setText("解决办法:\u3000" + infoBean.getAnswer());
        holder.getBinding().search.setText("查看次数:" + infoBean.getSearch_count());
        holder.getBinding().brandName.setText("品牌名称:" + infoBean.getBrand_name());
        holder.getBinding().code.setText("故障代码:" + infoBean.getCode());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
