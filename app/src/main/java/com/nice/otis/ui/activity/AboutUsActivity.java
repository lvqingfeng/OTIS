package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nice.otis.R;
import com.nice.otis.api.ApiService;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, AboutUsActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("关于我们",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.webView.loadUrl(ApiService.BASE_URL+ApiService.ABOUT_US);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }
}
