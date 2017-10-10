package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nice.otis.R;
import com.nice.otis.api.ApiService;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.databinding.ActivityAgreementBinding;

public class AgreementActivity extends BaseActivity<ActivityAgreementBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AgreementActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("用户协议",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.BASE_URL+ApiService.USER_AGREEMENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }
}
