package com.nice.otis.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.nice.otis.base.ConsumptionBean;
import com.nice.otis.base.LazyFragment;
import com.nice.otis.databinding.FragmentConsumptionBinding;
import com.nice.otis.ui.adapter.ConsumptionAdapter;
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

public class ConsumptionFragment extends LazyFragment<FragmentConsumptionBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<ConsumptionBean.ListBean> mList;
    private ConsumptionAdapter consumptionAdapter;

    public static ConsumptionFragment newInstance() {
        ConsumptionFragment fragment = new ConsumptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        consumptionAdapter = new ConsumptionAdapter(mList);
        mDataBinding.recyclerView.setAdapter(consumptionAdapter);
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateConsumtion(LoginHelper.getInstance().getUserId(),LoginHelper.getInstance().getUserToken(),String.valueOf(page),"15"),new ProgressSubscriber<ConsumptionBean>(mContext, new SubscriberOnNextListener<ConsumptionBean>() {
            @Override
            public void onNext(ConsumptionBean result) {
                if (result.getStatusCode()==1){
                    isEnd=ApiService.BASE_URL.equals(result.getTotal());
                    mList.addAll(result.getList());
                    consumptionAdapter.notifyDataSetChanged();
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
        onLoadMore(isEnd);
    }

    @Override
    public void onLoadmore() {
        onLoadMore(isEnd);
    }
}
