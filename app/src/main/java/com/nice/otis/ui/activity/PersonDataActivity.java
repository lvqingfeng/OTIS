package com.nice.otis.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BaseData;
import com.nice.otis.bean.PersonBean;
import com.nice.otis.databinding.ActivityPersonDataBinding;
import com.nice.otis.utils.DialogUtils;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.utils.SinglePictureSelectHelper;
import com.nice.otis.view.GlideCircleTransform;
import com.nice.otis.view.SinglePictureSelectPopWindow;

import java.io.File;
import java.util.Calendar;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.OnCompressListener;

public class PersonDataActivity extends BaseActivity<ActivityPersonDataBinding> {
    private String trueName;
    private String nickName;
    private String sexId = "0";
    private String birth;
    private String mobile;
    private Calendar mCalendar;
    private String headImage;
    private SinglePictureSelectHelper mSinglePictureHelper;
    private SinglePictureSelectPopWindow mSinglePictureWindow;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PersonDataActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("个人信息", DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setText("保存");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        headImage = mSinglePictureHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.headImage, false);
        if (!TextUtils.isEmpty(headImage)) {
            File file = new File(headImage);
            if (file.length() > 200 * 1024) {
                pictureCompression(file);
            } else {
                updateHeadImage(file);
            }
        }
    }

    private void updateHeadImage(File file) {
        MultipartBody.Part part = ApiClient.getFileBody(file);
        RequestBody body = RequestBody.create(null, LoginHelper.getInstance().getUserId());
        RequestBody token = RequestBody.create(null, LoginHelper.getInstance().getUserToken());
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .uploadHeadImage(part, body, token), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BaseData>() {
            @Override
            public void onNext(BaseData result) {
                if (result.getStatusCode() == 1) {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void pictureCompression(File file) {
        ApiClient.pictureCompression(mContext, file, new OnCompressListener() {
            @Override
            public void onStart() {
                DialogUtils.showProgressDialog(mContext, "请稍后...", null, "压缩失败");
            }

            @Override
            public void onSuccess(File file) {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                updateHeadImage(file);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("压缩失败", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });
    }


    @Override
    protected void initViews() {
        initPop();
        mCalendar = Calendar.getInstance();
        initPersonData();
        mDataBinding.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSinglePictureWindow.showPopupWindow(mDataBinding.getRoot());
            }
        });
        mDataBinding.birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int yearNow = mCalendar.get(Calendar.YEAR);
                int monthNow = mCalendar.get(Calendar.MONTH);
                int dayNow = mCalendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(new ContextThemeWrapper(mContext, android.R.style.Theme_Holo_Light_Dialog_NoActionBar), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mDataBinding.birth.setText(String.valueOf(year) + "-" + (month + 1) + "-" + dayOfMonth);

                        birth = String.valueOf(year) + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                }, yearNow, monthNow, dayNow);
                dialog.setTitle("选择时间");
                dialog.show();
            }
        });

        mDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_man:
                        sexId = "1";
                        break;
                    case R.id.radio_woman:
                        sexId = "2";
                        break;
                    case R.id.radio_secrecy:
                        sexId="0";
                        break;
                }
            }
        });
        getRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trueName = mDataBinding.trueName.getText().toString();
                nickName = mDataBinding.nickName.getText().toString();
                mobile = mDataBinding.mobile.getText().toString();

                if (TextUtils.isEmpty(trueName)) {
                    Toast.makeText(mContext, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nickName)) {
                    Toast.makeText(mContext, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(birth)) {
                    Toast.makeText(mContext, "请选择出生年月", Toast.LENGTH_SHORT).show();
                    return;
                }
                upload();
            }


        });
    }

    private void initPop() {
        mSinglePictureHelper = SinglePictureSelectHelper.getInstance();
        mSinglePictureWindow = new SinglePictureSelectPopWindow(this);
        mSinglePictureHelper.init(this);
        mSinglePictureWindow.bindHelper(mSinglePictureHelper);
        mSinglePictureWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    private void initPersonData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .getPersonData(LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<PersonBean>() {
            @Override
            public void onNext(PersonBean result) {
                if (result.getStatusCode() == 1) {
                    mDataBinding.trueName.setText(result.getData().getTruename());
                    mDataBinding.nickName.setText(result.getData().getNickname());
                    mDataBinding.birth.setText(result.getData().getBirthday());
                    mDataBinding.mobile.setText(result.getData().getMobile());
                    sexId = result.getData().getGender();
                    birth = result.getData().getBirthday();
                    if (TextUtils.isEmpty(result.getData().getGender())){
                        mDataBinding.radioSecrecy.setChecked(true);
                    }else {
                        switch (result.getData().getGender()){
                            case "0":
                                mDataBinding.radioSecrecy.setChecked(true);
                                break;
                            case "1":
                                mDataBinding.radioMan.setChecked(true);
                                break;
                            case "2":
                                mDataBinding.radioWoman.setChecked(true);
                                break;
                            default:
                                break;
                        }
                    }
                    Glide.with(mContext).load(result.getData().getHeadimgurl())
                            .error(R.mipmap.ic_launcher_round)
                            .transform(new GlideCircleTransform(mContext)).into(mDataBinding.headImage);
                } else {
                    Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void upload() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .uploadPersonData(LoginHelper.getInstance().getUserToken()
                                , LoginHelper.getInstance().getUserId(), mobile, sexId, trueName, nickName, birth)
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BaseData>() {
                    @Override
                    public void onNext(BaseData result) {
                        if (result.getStatusCode() == 1) {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_data;
    }
}
