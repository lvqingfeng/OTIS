package com.nice.otis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BannerBean;
import com.nice.otis.databinding.ActivityMainBinding;
import com.nice.otis.index.BrandIndexActivity;
import com.nice.otis.ui.activity.FeedBackActivity;
import com.nice.otis.ui.activity.MaterialActivity;
import com.nice.otis.ui.activity.NoticeActivity;
import com.nice.otis.ui.activity.PersonalActivity;
import com.nice.otis.ui.activity.RanKingActivity;
import com.nice.otis.ui.activity.SecurityActivity;
import com.nice.otis.utils.GlideImageLoader;
import com.nice.otis.utils.LoginHelper;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private List<String> list_path;
    private List<BannerBean.AdvInfoBean> mList = new ArrayList<>();

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public class Presenter {
        //故障查询
        public void faultQuery() {

            if (LoginHelper.getInstance().checkIsOnline()) {
                BrandIndexActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }
        }

        //通知公告
        public void notice() {
            if (LoginHelper.getInstance().checkIsOnline()) {
                NoticeActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }

        }

        //排行榜
        public void rankingList() {
            if (LoginHelper.getInstance().checkIsOnline()) {
                RanKingActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }
        }

        //资料库
        public void database() {
            if (LoginHelper.getInstance().checkIsOnline()) {
                MaterialActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }
        }

        //安全知识
        public void safetyKnowledge() {
            if (LoginHelper.getInstance().checkIsOnline()) {
                SecurityActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }
        }

        //反馈信息
        public void feedbackInformation() {
            if (LoginHelper.getInstance().checkIsOnline()) {
                FeedBackActivity.actionStart(MainActivity.this);
            } else {
                toLogin();
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("掌上电梯", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_white_person);
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginHelper.getInstance().checkIsOnline()) {
                    PersonalActivity.actionStart(MainActivity.this);
                } else {
                    toLogin();
                }

            }
        });
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(new Presenter());
        initBanner();
        initLoadData();
    }

    private void initBanner() {
        list_path = new ArrayList<>();
        mList = new ArrayList<>();
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .loadBanner(LoginHelper.getInstance().getUserToken()), new Subscriber<BannerBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BannerBean result) {
                if (result.getStatusCode() == 1) {
                    if (result.getAdvInfo().size() > 0) {
                        mList.addAll(result.getAdvInfo());
                        for (int i = 0; i < result.getAdvInfo().size(); i++) {
                            list_path.add(result.getAdvInfo().get(i).getImg_url());
                        }
                    }
                    mDataBinding.banner.setImageLoader(new GlideImageLoader());
                    mDataBinding.banner.setBannerAnimation(Transformer.Default);
                    mDataBinding.banner.isAutoPlay(true);
                    mDataBinding.banner.setDelayTime(3000);
                    mDataBinding.banner.setIndicatorGravity(BannerConfig.CENTER);
                    mDataBinding.banner.setImages(list_path);
                    mDataBinding.banner.start();
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
