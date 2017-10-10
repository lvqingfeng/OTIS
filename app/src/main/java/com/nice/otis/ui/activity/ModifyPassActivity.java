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
import com.nice.otis.databinding.ActivityModifyPassBinding;
import com.nice.otis.service.RegisterCodeTimerService;
import com.nice.otis.utils.EditTextUtils;
import com.nice.otis.utils.LogInUtils;

public class ModifyPassActivity extends BaseActivity<ActivityModifyPassBinding> {
    private String phone;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, ModifyPassActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("忘记密码",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        RegisterCodeTimerService.setCountDownTime(60000);
        RegisterCodeTimerService.setCountDownView(mDataBinding.registerBtnCode, R.drawable.bg_blue_fillet);
        EditTextUtils.checkOnEditInputForButtonState(mContext,mDataBinding.registerBtnRegister,mDataBinding.registerPhone,mDataBinding.registerMessageCode,mDataBinding.registerPassword);
        mDataBinding.registerBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone=mDataBinding.registerPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    showMessage("请输入手机号码");
                    return;
                }
                getMessageCode(phone);
            }
        });

        mDataBinding.registerBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = mDataBinding.registerMessageCode.getText().toString().trim();
                String pass = mDataBinding.registerPassword.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    showMessage("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    showMessage("请输入密码");
                    return;
                }

                if (!"1".equals(LogInUtils.checkPassword(pass))){
                    showMessage(LogInUtils.checkPassword(pass));
                    return;
                }
                forgetPasWord(phone,code,pass);
            }
        });
    }

    private void forgetPasWord(String mobile, String code, String pass) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).resetPassword(mobile,pass,code)
                ,new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BaseData>() {
            @Override
            public void onNext(BaseData result) {
                if (result.getStatusCode()==1){
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    LoginActivity.actionStart(ModifyPassActivity.this,LoginActivity.FROE_OTHER);
                }else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void getMessageCode(String phone) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).sendCaptcha(phone,"forget")
                ,new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BaseData>() {
                    @Override
                    public void onNext(BaseData result) {
                        if (result.getStatusCode()==1){
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                            RegisterCodeTimerService.startService(ModifyPassActivity.this, R.drawable.bg_blue_fillet);
                        }else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pass;
    }
}
