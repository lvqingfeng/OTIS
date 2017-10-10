package com.nice.otis.guide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 作者:吕清锋
 * 时间:2017/7/8
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class Myadapter extends FragmentPagerAdapter{
    public Myadapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                //返回第1个界面
                return new Item1();
            case 1:
                //返回第2个界面
                return new Item2();
            case 2:
                //返回第3个界面
                return new Item3();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        //返回的数量要和getItem中返回的界面相同
        return 3;
    }
}
