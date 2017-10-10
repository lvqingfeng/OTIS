package com.nice.otis.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.otis.R;

/**
 * 作者:吕清锋
 * 时间:2017/7/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class Item2 extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.guide_second,container,false);
        return view;
    }
}
