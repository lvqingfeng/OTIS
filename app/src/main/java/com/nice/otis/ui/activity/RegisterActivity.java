package com.nice.otis.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nice.otis.Constant;
import com.nice.otis.MainActivity;
import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BaseData;
import com.nice.otis.bean.LoginBean;
import com.nice.otis.bean.RegisterBean;
import com.nice.otis.bean.User;
import com.nice.otis.databinding.ActivityRegisterBinding;
import com.nice.otis.service.RegisterCodeTimerService;
import com.nice.otis.utils.DialogUtils;
import com.nice.otis.utils.EditTextUtils;
import com.nice.otis.utils.IMEIUtils;
import com.nice.otis.utils.LogInUtils;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.utils.UserUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;
import rx.functions.Action1;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    private InputMethodManager mInputManager;
    private String imei;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("注册",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        RxPermissions.getInstance(mContext).request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            try {
                                imei = IMEIUtils.getIMEI(mContext);
                            } catch (Exception e) {
                                Toast.makeText(mContext, "请开启权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        //检测手机号和密码的输入
        EditTextUtils.checkOnEditInputForButtonState(mContext
                ,mDataBinding.registerBtnRegister,mDataBinding.registerPhone
                ,mDataBinding.registerPassword,mDataBinding.registerMessageCode);
        RegisterCodeTimerService.setCountDownTime(60000);
        RegisterCodeTimerService.setCountDownView(mDataBinding.registerBtnCode, R.drawable.bg_blue_fillet);
        mDataBinding.registerBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
                String mobile = mDataBinding.registerPhone.getText().toString();
                if (TextUtils.isEmpty(mobile)){
                    Toast.makeText(mContext, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                        , RetrofitManager.RetrofitType.Object).sendCaptcha(mobile,"reg")
                        ,new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BaseData>() {
                    @Override
                    public void onNext(BaseData result) {
                        if (result.getStatusCode()==1){
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                            RegisterCodeTimerService.startService(RegisterActivity.this, R.drawable.bg_blue_fillet);
                        }else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }
        });

        mDataBinding.registerBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputManager.hideSoftInputFromWindow(mDataBinding.getRoot().getWindowToken(), 0);
                String phone=mDataBinding.registerPhone.getText().toString().trim();
                String code=mDataBinding.registerMessageCode.getText().toString();
                String pass=mDataBinding.registerPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    showMessage("请输入手机号");
                    return;
                }

                if (TextUtils.isEmpty(code)){
                    showMessage("请获取验证码并填写");
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

                registers(phone,pass,code);
            }
        });


        mDataBinding.agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgreementActivity.actionStart(RegisterActivity.this);
            }
        });
    }
    private void registers(final String moboile, final String password, String captcha){
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).register(moboile,password,captcha,imei),new ProgressSubscriber<RegisterBean>(mContext, new SubscriberOnNextListener<RegisterBean>() {
            @Override
            public void onNext(RegisterBean result) {
                if (result.getStatusCode()==1){
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    DialogUtils.showProgressDialog(mContext, "正在登录中", "", "");
                    RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                            .login(moboile, password,imei), new Subscriber<LoginBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            hideDialogError();
                        }

                        @Override
                        public void onNext(LoginBean result) {
                            if (result.getStatusCode()==1){
                                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                UserUtils.putUserName(mContext, moboile);
                                UserUtils.putUserPassword(mContext, password);
                                User user = UserUtils.getUser(mContext);
                                user.setPhone(moboile);
                                user.setPassword(password);
                                user.setToken(result.getData().getToken());
                                user.setUid(result.getData().getUid());
                                UserUtils.saveUser(mContext, user);
                                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent().setAction(Constant.ACTION_PAGE_REFRESH));
                                LoginHelper.getInstance().setOnline(true);
                                MainActivity.actionStart(RegisterActivity.this);
                                finish();
                            }else {
                                hideDialogError();
                                Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterCodeTimerService.onActivityFinish();
    }

    private void hideDialogError() {
        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}

