package com.nice.otis.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * @author markzhai on 16/7/14
 * @version 1.3.0
 */
public class BindingViewHolder<D extends ViewDataBinding>
        extends RecyclerView.ViewHolder {

    private D mBinding;
    public Context mContext;

    public BindingViewHolder(D binding) {
        super(binding.getRoot());
        mBinding = binding;
        mContext = mBinding.getRoot().getContext();
    }

    public D getBinding() {
        return mBinding;
    }
}
