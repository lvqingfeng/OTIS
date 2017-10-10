package com.nice.otis.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * 作者:吕清锋
 * 时间：2017/7/6
 * 版本：V1.0.0
 * 类描述：加载banner图的时候
 */


public class GlideImageLoader extends ImageLoader {
    //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
