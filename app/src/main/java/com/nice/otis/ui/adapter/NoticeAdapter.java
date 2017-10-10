package com.nice.otis.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;
import com.nice.otis.base.BindingViewHolder;
import com.nice.otis.bean.NoticeBean;
import com.nice.otis.databinding.ItemLayoutNoticeBinding;
import com.nice.otis.utils.DateUtils;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class NoticeAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutNoticeBinding>> {
    private List<NoticeBean.DataBean> mList;

    public NoticeAdapter(List<NoticeBean.DataBean> mList) {
        this.mList = mList;
    }

    @Override
    public BindingViewHolder<ItemLayoutNoticeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_notice,parent,false);
        return new BindingViewHolder<>(ItemLayoutNoticeBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<ItemLayoutNoticeBinding> holder, int position) {
        NoticeBean.DataBean dataBean = mList.get(position);
        holder.getBinding().title.setText(dataBean.getTitle());
        holder.getBinding().content.setText(dataBean.getContent());
        holder.getBinding().time.setText(DateUtils.getShortTime(Long.decode(dataBean.getCreate_time())*1000));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
