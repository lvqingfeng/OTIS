package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.HeadlineListBean;
import com.nice.otis.databinding.ActivitySecurityBinding;
import com.nice.otis.recycler_listener.OnRecyclerItemClickListener;
import com.nice.otis.ui.adapter.SecurityAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SecurityActivity extends BaseActivity<ActivitySecurityBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<HeadlineListBean.DataBean.HeadInfoBean> mList;
    private SecurityAdapter securityAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SecurityActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("安全知识", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mDataBinding.spring.setHeader(new DefaultHeader(mContext));
        mDataBinding.spring.setFooter(new DefaultFooter(mContext));
        mDataBinding.spring.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        securityAdapter = new SecurityAdapter(mList);
        mDataBinding.recyclerView.setAdapter(securityAdapter);
        initLoadData();

        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String id = mList.get(vh.getLayoutPosition()).getId();
                SecurityDetailsActivity.actionStart(SecurityActivity.this, id);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object)
                .updateHeadLineList(LoginHelper.getInstance().getUserToken()
                        , "2", String.valueOf(page), "15"), new ProgressSubscriber<>(mContext
                , new SubscriberOnNextListener<HeadlineListBean>() {
            @Override
            public void onNext(HeadlineListBean result) {
                if (result.getStatusCode() == 1) {
                    isEnd = Integer.parseInt(result.getData().getTotal()) == 1;
                    mList.addAll(result.getData().getHeadInfo());
                    securityAdapter.notifyDataSetChanged();
                    mDataBinding.spring.onFinishFreshAndLoad();
                } else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.spring;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security;
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
