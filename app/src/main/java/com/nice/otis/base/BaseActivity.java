package com.nice.otis.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzp.statusbar.StatusBarCompat;
import com.lzp.statusbar.view.TitleCompatLayout;
import com.nice.otis.R;
import com.nice.otis.ui.activity.LoginActivity;
import com.nice.otis.utils.AppManager;
import com.nice.otis.utils.DialogUtils;
import com.nice.otis.utils.RefreshHandler;
import com.nice.otis.utils.StatusbarColorUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * *作者：吕清锋
 * 创建时间：7月5日
 * 时间：14:00
 * 版本：v1.0.0
 * 类描述：常用的Activity的简单封装
 * 修改时间：
 */

public abstract class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity {

    protected static int DEFAULT_TITLE_TEXT_COLOR = -2;

    protected Context mContext;

    //标题栏
    private TextView mTvTitleLeftHintView;
    private CheckBox mCbxTitleRightView;
    private LinearLayout mLayoutTitleCenterView;
    private LinearLayout mLayoutTitleLeft;
    private ImageView mIvTitleBackIcon;
    private TitleCompatLayout mTitleCompatLayout;

    protected boolean isUseDefaultTitle = true;//是否使用BaseActivity中自己封装的标题


    protected D mDataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //清除状态栏的颜色
        StatusbarColorUtils.setWindow(this, Color.TRANSPARENT);
        //设置竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDataBinding = setContentView();
        mContext = this;

        AppManager.getAppManager().addActivity(this);
        //在调用所有的方法之前，用来初始化一些成员变量
        initFieldBeforeMethods();

        //初始化标题栏
        if (isUseDefaultTitle) {
            initTitle();
        }
        StatusBarCompat.setStatusBarColor(this, mTitleCompatLayout, isUseDefaultTitle);


        //设置标题栏属性
        setupTitle();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRefreshHandler != null)
            mRefreshHandler.onActivityFinish();
    }

    /**
     * 通过DataBindingUtils填充页面布局并返回数据绑定对象
     *
     * @return 当前页面数据绑定对象
     */
    protected D setContentView() {
        return DataBindingUtil.setContentView(this, getLayoutId());
    }

    /**
     * 用来初始化一些成员变量，这个方法调用的时间在所有方法调用之前
     * 因为不一定所有的子类都需要设置这个方法，所以不写成为抽象的方法了
     */
    protected void initFieldBeforeMethods() {

    }

    //设置标题的属性
    protected abstract void setupTitle();

    protected abstract void initViews();

    protected abstract int getLayoutId();


    protected LinearLayout openTitleLeftView(boolean isDefault) {
        return openTitleLeftView(this, isDefault);
    }

    /**
     * 关闭当前页面
     *
     * @param activity
     */
    private LinearLayout openTitleLeftView(final Activity activity, boolean isDefault) {
        if (isDefault) {
            mLayoutTitleLeft.setVisibility(View.VISIBLE);
        }
        if (activity != null) {
            mLayoutTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }

        return mLayoutTitleLeft;
    }


    /**
     * 设置左侧提示文字
     *
     * @param text
     */
    protected void setLeftHintText(String text) {
        mTvTitleLeftHintView.setText(text);
    }


    protected CheckBox setRightViewText(String text, int textColor) {
        if (mCbxTitleRightView.getVisibility() == View.GONE)
            mCbxTitleRightView.setVisibility(View.VISIBLE);
        mCbxTitleRightView.setText(text);
        if (textColor != DEFAULT_TITLE_TEXT_COLOR)
            mCbxTitleRightView.setTextColor(textColor);
        return mCbxTitleRightView;
    }

    protected CheckBox getRightView() {
        return mCbxTitleRightView;
    }

    public TitleCompatLayout getTitleLayout() {
        return mTitleCompatLayout;
    }

    protected void setTextTitleView(String text, int textColor) {

        TextView titleView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleView.setLayoutParams(params);
        titleView.setGravity(Gravity.CENTER);
        titleView.setSingleLine(true);
        titleView.setText(text == null ? "" : text);
        if (textColor != DEFAULT_TITLE_TEXT_COLOR)
            titleView.setTextColor(textColor);
        else
            titleView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitleColor));
        titleView.setTextSize(18);
        setTitleCenterView(titleView, false);
    }

    protected View setTitleCenterViewRes(int layoutRes, boolean isToLeftOrRightOf) {
        View titleCenterView = LayoutInflater.from(mContext).inflate(layoutRes, null);
        setTitleCenterView(titleCenterView, isToLeftOrRightOf);
        return titleCenterView;
    }

    /**
     * 设置中间的控件
     *
     * @param view              具体填充的控件
     * @param isToLeftOrRightOf 设置控件是否按照在某个控件的左边或者右边
     */
    protected void setTitleCenterView(View view, boolean isToLeftOrRightOf) {
        mLayoutTitleCenterView.removeAllViews();
        mLayoutTitleCenterView.addView(view);

        //设置控件是否按照在某个控件的左边或者右边
        if (isToLeftOrRightOf) {
            //获取CenterView的布局参数
            RelativeLayout.LayoutParams layoutParams = null;
            //判断如果它的布局参数不为空的时候，就直接取出赋值,如果为空就新建一个
            if (mLayoutTitleCenterView.getLayoutParams() != null) {
                layoutParams = (RelativeLayout.LayoutParams) mLayoutTitleCenterView.getLayoutParams();
            } else {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            //设置CenterView在布局中需要依附到哪些控件的旁边
            layoutParams.addRule(RelativeLayout.LEFT_OF, mCbxTitleRightView.getId());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.addRule(RelativeLayout.END_OF, mLayoutTitleLeft.getId());
            }
            layoutParams.addRule(RelativeLayout.RIGHT_OF, mLayoutTitleLeft.getId());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.addRule(RelativeLayout.START_OF, mCbxTitleRightView.getId());
            }
        }
    }


    private void initTitle() {
        mIvTitleBackIcon = (ImageView) findViewById(R.id.iv_app_title_layout_back);
        mTvTitleLeftHintView = (TextView) findViewById(R.id.tv_app_title_layout_left_hint);
        mCbxTitleRightView = (CheckBox) findViewById(R.id.tv_app_title_layout_right);
        mLayoutTitleCenterView = (LinearLayout) findViewById(R.id.ll_app_title_layout_center);
        mLayoutTitleLeft = (LinearLayout) findViewById(R.id.ll_app_title_layout_left);
        mTitleCompatLayout = (TitleCompatLayout) findViewById(R.id.title_compat_app_title_layout);
    }

    protected LinearLayout getLeftLayoutTitle() {
        return mLayoutTitleLeft;
    }

    protected ImageView getTitleBackIcon() {
        return mIvTitleBackIcon;
    }

    /**
     * 屏幕变亮
     */
    public void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    public void lightOff() {
        lightOff(0.65f);
    }

    /**
     * 屏幕变暗
     */
    public void lightOff(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    protected void showMessage(String msg) {
        Snackbar.make(mDataBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private List mListData;
    private SRefreshHandler mRefreshHandler;

    public <T> void setListData(List<T> listData) {
        mListData = listData;
    }

    public void initLoadData() {
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(null);
    }

    public void onRefresh(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(isEnd);
    }


    public void onLoadMore(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullUpToRefresh(isEnd);
    }

    private void initRefreshHandler() {
        if (mRefreshHandler == null) {
            mRefreshHandler = new SRefreshHandler(this);
        }
    }

    protected static class SRefreshHandler extends RefreshHandler {
        private WeakReference<BaseActivity> mActivity;

        SRefreshHandler(BaseActivity activity) {
            super(activity.getRefreshView());
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                if (mActivity.get().mListData != null) {
                    mActivity.get().mListData.clear();
                }
            }
            mActivity.get().loadListData(page);
        }
    }

    protected ViewGroup getRefreshView() {
        return null;
    }

    protected void loadListData(int page) {
    }

    protected void toLogin() {
        DialogUtils.alertDialog(mContext, "温馨提示", "登录已过期,是否重新登录?", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                LoginActivity.actionStart(BaseActivity.this,LoginActivity.FROE_OTHER);
            }
        });
    }
}
