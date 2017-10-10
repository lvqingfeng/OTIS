package com.nice.otis.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nice.otis.base.LazyFragment;

import java.util.List;

/**
 * 作者:吕清锋
 * 时间:2017/9/18
 * 版本:v1.0
 * 类描述：
 * 修改时间:
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] ={"消费记录","充值记录"};
    private List<LazyFragment> fragments;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<LazyFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
