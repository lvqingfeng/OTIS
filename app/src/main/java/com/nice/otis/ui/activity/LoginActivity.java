package com.nice.otis.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.nice.otis.Constant;
import com.nice.otis.MainActivity;
import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.LoginBean;
import com.nice.otis.bean.User;
import com.nice.otis.databinding.ActivityLoginBinding;
import com.nice.otis.utils.DialogUtils;
import com.nice.otis.utils.EditTextUtils;
import com.nice.otis.utils.IMEIUtils;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.utils.UserUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    public static final int FROE_REGISTER = 0x000123;
    public static final int FROE_OTHER = 0x000123;

    private String imei;

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("登录", DEFAULT_TITLE_TEXT_COLOR);
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
        /***
         * 若果不是第一次登陆，就默认设置记住密码
         * */
        User user = UserUtils.getUser(mContext);
        if (user != null) {
            mDataBinding.loginUsername.setText(user.getPhone());
            mDataBinding.loginPassword.setText(user.getPassword());
        }
        /***
         * 检测Button的能否点击的状态，只有这两个个输入框输入
         * 的内容长度都大于3时，button才可以点击
         * **/
        EditTextUtils.checkOnEditInputForButtonState(mContext, mDataBinding.btnLogin, mDataBinding.loginUsername, mDataBinding.loginPassword);

        mDataBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.actionStart(LoginActivity.this);
            }
        });
        mDataBinding.forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPassActivity.actionStart(LoginActivity.this);
            }
        });

        mDataBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mDataBinding.loginUsername.getText().toString().trim();
                String passWord = mDataBinding.loginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "请输入账号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passWord)) {
                    Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(userName, passWord);
            }
        });


        mDataBinding.agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgreementActivity.actionStart(LoginActivity.this);
            }
        });
    }

    private void login(final String userName, final String passWord) {
        DialogUtils.showProgressDialog(mContext, "正在登录中", "", "");
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).login(userName, passWord,imei), new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                hideDialogError();
            }

            @Override
            public void onNext(LoginBean result) {
                if (result.getStatusCode() == 1) {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                    UserUtils.putUserName(mContext, userName);
                    UserUtils.putUserPassword(mContext, passWord);
                    User user = UserUtils.getUser(mContext);
                    user.setPhone(userName);
                    user.setPassword(passWord);
                    user.setToken(result.getData().getToken());
                    user.setUid(result.getData().getUid());
                    UserUtils.saveUser(mContext, user);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent().setAction(Constant.ACTION_PAGE_REFRESH));
                    LoginHelper.getInstance().setOnline(true);
                    MainActivity.actionStart(LoginActivity.this);
                    finish();
                } else {
                    hideDialogError();
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void hideDialogError() {
        DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}
