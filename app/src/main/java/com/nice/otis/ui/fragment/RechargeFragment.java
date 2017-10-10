package com.nice.otis.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.LazyFragment;
import com.nice.otis.base.RechargeBean;
import com.nice.otis.databinding.FragmentConsumptionBinding;
import com.nice.otis.ui.adapter.RechargeAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class RechargeFragment extends LazyFragment<FragmentConsumptionBinding>
implements SpringView.OnFreshListener{
    private boolean isEnd;
    private List<RechargeBean.ListBean> mList;
    private RechargeAdapter rechargeAdapter;

    public static RechargeFragment newInstance() {
        RechargeFragment fragment = new RechargeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        mList=new ArrayList<>();
        mDataBinding.springView.setHeader(new DefaultHeader(mContext));
        mDataBinding.springView.setFooter(new DefaultFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        rechargeAdapter = new RechargeAdapter(mList);
        mDataBinding.recyclerView.setAdapter(rechargeAdapter);

    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateRecharge(LoginHelper.getInstance().getUserId(),LoginHelper.getInstance().getUserToken(),String.valueOf(page),"15"),new ProgressSubscriber<RechargeBean>(mContext, new SubscriberOnNextListener<RechargeBean>() {
            @Override
            public void onNext(RechargeBean result) {
                if (result.getStatusCode()==1){
                    isEnd=ApiService.BASE_URL.equals(result.getTotal());
                    mList.addAll(result.getList());
                    rechargeAdapter.notifyDataSetChanged();
                    mDataBinding.springView.onFinishFreshAndLoad();
                }else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    mDataBinding.springView.onFinishFreshAndLoad();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void loadData() {
        initLoadData();
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    public void onRefresh() {
        onRefresh(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}
