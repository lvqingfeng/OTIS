package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.PersonBean;
import com.nice.otis.databinding.ActivityPersonalBinding;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.utils.UserUtils;

public class PersonalActivity extends BaseActivity<ActivityPersonalBinding> {
    private static int versionCode;
    private static String versionName;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PersonalActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("个人中心", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        initData();
        mDataBinding.versionMessage.setText(getAppVersionName(mContext));
        mDataBinding.editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPassActivity.actionStart(PersonalActivity.this);
            }
        });

        mDataBinding.exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginHelper.getInstance().setOnline(false);
                UserUtils.quit(mContext);
                LoginActivity.actionStart(PersonalActivity.this, LoginActivity.FROE_OTHER);
                finish();
            }
        });

        mDataBinding.person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonDataActivity.actionStart(PersonalActivity.this);
            }
        });

        mDataBinding.member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberActivity.actionStart(PersonalActivity.this);
            }
        });

        mDataBinding.point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyPointActivity.actionStart(PersonalActivity.this);
            }
        });

        mDataBinding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUsActivity.actionStart(PersonalActivity.this);
            }
        });
    }

    private void initData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .getPersonData(LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<PersonBean>(mContext, new SubscriberOnNextListener<PersonBean>() {
            @Override
            public void onNext(PersonBean result) {
                if (result.getStatusCode() == 1) {
                    if (TextUtils.isEmpty(result.getData().getTruename())) {
                        mDataBinding.nickName.setText("电梯维修");
                    } else {
                        mDataBinding.nickName.setText(result.getData().getTruename());
                    }
                    mDataBinding.nums.setText(result.getData().getService_record() + "次\n累计查询次数");
                    mDataBinding.integral.setText(result.getData().getPoint() + "分\n我的积分");
                }
            }
        }));
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {

        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }
}
