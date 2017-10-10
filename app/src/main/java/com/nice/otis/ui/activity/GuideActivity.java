package com.nice.otis.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nice.otis.MainActivity;
import com.nice.otis.R;
import com.nice.otis.guide.Myadapter;
import com.nice.otis.guide.ViewPagerIndicator;
import com.nice.otis.utils.LoginHelper;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class GuideActivity extends AppCompatActivity {
    private static final int sleepTime = 2400;
    private ViewPager mViewPager;
    private LinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        mViewPager=(ViewPager)findViewById(R.id.view_pager);
        mLl = (LinearLayout)findViewById(R.id.ll_view);
        mViewPager.setAdapter(new Myadapter(getSupportFragmentManager()));

        mViewPager.setOnPageChangeListener(new ViewPagerIndicator(this,mViewPager,mLl,3));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkIsLogin() {
        RxPermissions.getInstance(getApplicationContext()).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            try {
                                Thread.sleep(sleepTime);
                                nextStep();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void nextStep() {
        if (LoginHelper.getInstance().checkIsOnline()) {
            MainActivity.actionStart(GuideActivity.this);
            finish();
        } else {
            LoginActivity.actionStart(GuideActivity.this, LoginActivity.FROE_OTHER);
            finish();
        }
    }


}
