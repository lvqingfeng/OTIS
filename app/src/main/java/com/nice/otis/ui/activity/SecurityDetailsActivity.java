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
import com.nice.otis.bean.HeadLineDetailsBean;
import com.nice.otis.databinding.ActivitySecurityDetailsBinding;
import com.nice.otis.utils.DateUtils;
import com.nice.otis.utils.LoginHelper;

public class SecurityDetailsActivity extends BaseActivity<ActivitySecurityDetailsBinding> {

    private String id;

    public static void actionStart(Activity activity, String id) {
        Intent intent = new Intent(activity, SecurityDetailsActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("详细信息", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id = getIntent().getStringExtra("id");
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateHeadlineDetails(id, LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<HeadLineDetailsBean>(mContext, new SubscriberOnNextListener<HeadLineDetailsBean>() {
            @Override
            public void onNext(HeadLineDetailsBean result) {
                if (result.getStatusCode() == 1) {
                    mDataBinding.title.setText(result.getHeadInfo().getTitle());
                    mDataBinding.content.setText(result.getHeadInfo().getComment());
                    mDataBinding.time.setText(DateUtils.getShortTime(Long.decode(result.getHeadInfo().getCtime())*1000));
                    mDataBinding.tag.setText("标签:\u3000" + result.getHeadInfo().getTag_str());
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_security_details;
    }
}
