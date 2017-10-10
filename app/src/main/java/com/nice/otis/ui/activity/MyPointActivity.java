package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nice.otis.R;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.base.LazyFragment;
import com.nice.otis.databinding.ActivityMyPointBinding;
import com.nice.otis.ui.adapter.MyFragmentPagerAdapter;
import com.nice.otis.ui.fragment.ConsumptionFragment;
import com.nice.otis.ui.fragment.RechargeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPointActivity extends BaseActivity<ActivityMyPointBinding> {
    private List<LazyFragment> mList;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MyPointActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("我的积分",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mList=new ArrayList<>();
        mList.add(ConsumptionFragment.newInstance());
        mList.add(RechargeFragment.newInstance());
        mDataBinding.viewPagerPoint.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),mList));
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPagerPoint);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_point;
    }
}
