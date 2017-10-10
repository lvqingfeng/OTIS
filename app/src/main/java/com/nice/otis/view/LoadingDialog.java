package com.nice.otis.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nice.otis.R;


/**
 * Created by Tongfang on 2017/7/8.
 */

public class LoadingDialog extends Dialog{

    private AnimationDrawable anim;

    private CharSequence mMessageText;
    private TextView mTvMessage;
    private ImageView imageView;
    private Animation operatingAnim;
    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.loading_layout);
        setCancelable(false);
        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.NoColor)));

        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.version_image_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView = (ImageView) findViewById(R.id.iv_loading);

//        ImageView ivLoading = (ImageView) findViewById(R.id.iv_loading);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
//        anim = (AnimationDrawable) ivLoading.getDrawable();
        setMessage(mMessageText);
    }

    public void setMessage(CharSequence text) {
        mMessageText = text;
        if (mTvMessage != null && mMessageText != null) {
            mTvMessage.setText(mMessageText);
        }
    }

    @Override
    public void show() {
        imageView.startAnimation(operatingAnim);
//        if (anim != null) {
//            anim.start();
//        }
        super.show();
    }

    @Override
    public void hide() {
        imageView.clearAnimation();
//        if (anim != null) {
//            anim.stop();
//        }
        super.hide();
    }

}
