package com.nice.otis.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 作者：吕清锋
 * 创建时间：7.5
 * 时间：13:55
 * 版本：v1.0.0
 * 类描述：常用Fragment的封装，简单的刷新实现等
 * 修改时间：
 */

public abstract class BaseFragment<D extends ViewDataBinding> extends Fragment {
    protected D mDataBinding;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mDataBinding = DataBindingUtil.bind(rootView);
        mContext = getContext();

        initViews();
        return rootView;
    }

    protected abstract void initViews();

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRefreshHandler != null){
            mRefreshHandler.onActivityFinish();
        }
    }


    private List mListData;
    private SRefreshHandler mRefreshHandler;

    public <T> void setListData(List<T> listData) {
        mListData = listData;
    }
    public void initLoadData(){
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(null);
    }
    public void onRefresh(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(isEnd);
    }

    public void onLoadMore(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullUpToRefresh(isEnd);
    }

    private void initRefreshHandler() {
        if (mRefreshHandler == null) {
            mRefreshHandler = new SRefreshHandler(this);
        }
    }
    protected static class SRefreshHandler extends RefreshHandler {
        private WeakReference<BaseFragment> mActivity;

        SRefreshHandler(BaseFragment activity) {
            super(activity.getRefreshView());
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                if (mActivity.get().mListData != null) {
                    mActivity.get().mListData.clear();
                }
            }
            mActivity.get().loadListData(page);
        }
    }

    protected ViewGroup getRefreshView() {
        return null;
    }

    protected void loadListData(int page) {
    }
}
