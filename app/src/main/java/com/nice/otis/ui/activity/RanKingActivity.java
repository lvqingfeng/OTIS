package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.RankListBean;
import com.nice.otis.databinding.ActivityRanKingBinding;
import com.nice.otis.ui.adapter.RankListAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RanKingActivity extends BaseActivity<ActivityRanKingBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd=true;
    private List<RankListBean.InfoBean> mList;
    private RankListAdapter rankListAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RanKingActivity.class);
        intent.putExtra("name", "123");
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("排行榜", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mList = new ArrayList<>();
        mDataBinding.springView.setHeader(new DefaultHeader(mContext));
        mDataBinding.springView.setFooter(new DefaultFooter(mContext));
        mDataBinding.springView.setListener(this);
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        setListData(mList);
        rankListAdapter = new RankListAdapter(mList);
        mDataBinding.recyclerView.setAdapter(rankListAdapter);
        initLoadData();
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .uploadRankList(LoginHelper.getInstance().getUserToken(), String.valueOf(page), "15")
                , new ProgressSubscriber<RankListBean>(mContext, new SubscriberOnNextListener<RankListBean>() {
                    @Override
                    public void onNext(RankListBean result) {
                        if (result.getStatusCode()==1){
                            mList.addAll(result.getInfo());
                            rankListAdapter.notifyDataSetChanged();
                            mDataBinding.springView.onFinishFreshAndLoad();
                        }else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    @Override
    protected ViewGroup getRefreshView() {
        return mDataBinding.springView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ran_king;
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
