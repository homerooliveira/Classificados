package com.oliveira.classificados.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oliveira.classificados.fragment.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public TabAdapter(FragmentManager fm) {
        super(fm);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    public void add(String title, Fragment fragment) {
        mTitles.add(title);
        mFragments.add(fragment);
    }

    public void loadData(){
        for (Fragment fragment : mFragments) {
            ((ListFragment) fragment).loadData();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
