package com.nice.otis.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nice.otis.MainActivity;
import com.nice.otis.R;


/**
 * 作者:吕清锋
 * 时间:2017/7/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class Item3 extends Fragment {
    private View view;
    private TextView mBtnStart;
    private static final int sleepTime = 3000;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.guide_three,container,false);
        mBtnStart = ((TextView) view.findViewById(R.id.guide_start));
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.actionStart(getActivity());
                getActivity().finish();
            }
        });

        return view;

    }

}
