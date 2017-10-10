package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BaseData;
import com.nice.otis.databinding.ActivityFeedBackBinding;
import com.nice.otis.utils.LoginHelper;

public class FeedBackActivity extends BaseActivity<ActivityFeedBackBinding> {
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("反馈",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        mDataBinding.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=mDataBinding.info.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(mContext, "反馈信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).updateFeedBack(LoginHelper.getInstance().getUserBean().getPhone(),LoginHelper.getInstance().getUserToken(),content),new ProgressSubscriber<BaseData>(mContext, new SubscriberOnNextListener<BaseData>() {
                    @Override
                    public void onNext(BaseData result) {
                        if (result.getStatusCode()==1){
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }
}
