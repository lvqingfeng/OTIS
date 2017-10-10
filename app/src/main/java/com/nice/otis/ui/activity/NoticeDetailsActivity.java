package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.NoticeDetailsBean;
import com.nice.otis.databinding.ActivityNoticeDetailsBinding;
import com.nice.otis.utils.DateUtils;
import com.nice.otis.utils.LoginHelper;

public class NoticeDetailsActivity extends BaseActivity<ActivityNoticeDetailsBinding> {

    private String id;
    public static void actionStart(Activity activity,String id) {
        Intent intent = new Intent(activity, NoticeDetailsActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("公告详情",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id=getIntent().getStringExtra("id");
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateNoticeDetails(id, LoginHelper.getInstance().getUserToken()),new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<NoticeDetailsBean>() {
            @Override
            public void onNext(NoticeDetailsBean result) {
                if (result.getStatusCode()==1){
                    mDataBinding.title.setText(result.getData().getTitle());
                    mDataBinding.content.setText(result.getData().getContent());
                    mDataBinding.time.setText(DateUtils.getShortTime(Long.decode(result.getData().getCreate_time())*1000));
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_details;
    }
}
