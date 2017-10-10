package com.nice.otis.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
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
import com.nice.otis.bean.ToubleBean;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.databinding.ActivityTroubleshootingBinding;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class TroubleshootingActivity extends BaseActivity<ActivityTroubleshootingBinding> {

    private String brand_id;
    private String attr_id;
    private String model;//型号
    private String brand;

    private TroubleshootingActivity instance;
    private boolean status = false;
    private Camera camera;
    private Camera.Parameters parameters;
    private String is_pay;
    public static void actionStart(Activity activity, String brand_id, String attr_id
            , String brand, String model) {
        Intent intent = new Intent(activity, TroubleshootingActivity.class);
        intent.putExtra("brand_id", brand_id);
        intent.putExtra("attr_id", attr_id);
        intent.putExtra("brand", brand);
        intent.putExtra("model", model);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("故障查询", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        brand_id = getIntent().getStringExtra("brand_id");
        attr_id = getIntent().getStringExtra("attr_id");
        brand = getIntent().getStringExtra("brand");
        model = getIntent().getStringExtra("model");
        mDataBinding.title.setText("当前所选品牌:\u3000" + brand + "\u3000" + model);
        mDataBinding.question.setVisibility(View.INVISIBLE);
        mDataBinding.query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = mDataBinding.search.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(mContext, "请输入故障代码", Toast.LENGTH_SHORT).show();
                    return;
                }
                RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                        .getToubleData(brand_id, attr_id, code), new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ToubleBean>() {

                    @Override
                    public void onNext(ToubleBean result) {
                        if (result.getStatusCode() == 1) {
                            mDataBinding.question.setVisibility(View.VISIBLE);
                            mDataBinding.answer.setVisibility(View.INVISIBLE);
                            mDataBinding.content.setText("故障代码:" + result.getInfo().get(0).getCode() + "\n故障原因:" + result.getInfo().get(0).getReason());
                            mDataBinding.answer.setText(result.getInfo().get(0).getAnswer());
//                            mDataBinding.button.setText("查看解决方案("+result.getInfo().get(0).getPoint()+"积分)");
                            is_pay = result.getInfo().get(0).getIs_pay();
                        }else {
                            Toast.makeText(mContext, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

            }
        });

        mDataBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(is_pay)){
                    Toast.makeText(instance, "先去付钱吧", Toast.LENGTH_SHORT).show();
                }else {
                    mDataBinding.answer.setVisibility(View.VISIBLE);
                }

            }
        });
        mDataBinding.flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxPermissions.getInstance(mContext).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            try {
                                camera = Camera.open();
                                instance = TroubleshootingActivity.this;
                                if (!status) {
                                    mDataBinding.openFlash.setText("关闭手电筒");
                                    status = true;
                                    new Thread(new TurnOnLight()).start();
                                } else {
                                    status = false;
                                    mDataBinding.openFlash.setText("打开手电筒");
                                    instance.parameters.setFlashMode("off");
                                    instance.camera.setParameters(instance.parameters);
                                }
                            } catch (Exception e) {
                                Toast.makeText(instance, "请打开权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_troubleshooting;
    }

    private class TurnOnLight implements Runnable {
        @Override
        public void run() {
            instance.parameters = instance.camera.getParameters();
            instance.parameters.setFlashMode("torch");
            instance.camera.setParameters(instance.parameters);
        }
    }
}
