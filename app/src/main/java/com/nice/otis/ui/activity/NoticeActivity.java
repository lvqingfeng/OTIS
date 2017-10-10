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
import com.nice.otis.bean.NoticeBean;
import com.nice.otis.databinding.ActivityNoticeBinding;
import com.nice.otis.recycler_listener.OnRecyclerItemClickListener;
import com.nice.otis.ui.adapter.NoticeAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseActivity<ActivityNoticeBinding>
        implements SpringView.OnFreshListener {
    private boolean isEnd;
    private List<NoticeBean.DataBean> mList;
    private NoticeAdapter noticeAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, NoticeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("公告", DEFAULT_TITLE_TEXT_COLOR);
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
        noticeAdapter = new NoticeAdapter(mList);
        mDataBinding.recyclerView.setAdapter(noticeAdapter);
        initLoadData();

        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String id = mList.get(vh.getLayoutPosition()).getId();
                NoticeDetailsActivity.actionStart(NoticeActivity.this, id);
            }
        });
    }

    @Override
    protected void loadListData(int page) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateNoticeList(LoginHelper.getInstance().getUserToken(), String.valueOf(page), "15"), new ProgressSubscriber<NoticeBean>(mContext, new SubscriberOnNextListener<NoticeBean>() {
            @Override
            public void onNext(NoticeBean result) {
                if (result.getStatusCode() == 1) {
                    isEnd = Integer.parseInt(result.getTotal()) == 1;
                    mList.addAll(result.getData());
                    noticeAdapter.notifyDataSetChanged();
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
        return R.layout.activity_notice;
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
