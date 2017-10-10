package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.IntegralBean;
import com.nice.otis.databinding.ActivityMemberBinding;
import com.nice.otis.ui.adapter.IntegralAdapter;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends BaseActivity<ActivityMemberBinding> {
    private List<IntegralBean.ListBean> mList;
    private IntegralAdapter integralAdapter;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MemberActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("兑换专区",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mList=new ArrayList<>();
        mDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        integralAdapter = new IntegralAdapter(mList);
        mDataBinding.recyclerView.setAdapter(integralAdapter);
        loadData();
    }

    private void loadData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType
                .Object).updatePointCategory(),new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<IntegralBean>() {
            @Override
            public void onNext(IntegralBean result) {
                if (result.getStatusCode()==1){
                    mList.addAll(result.getList());
                    integralAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member;
    }
}
