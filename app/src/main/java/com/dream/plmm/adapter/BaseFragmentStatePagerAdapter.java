package com.dream.plmm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by likun on 16/9/9.
 */
public class BaseFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter {

    private List<T> list;
    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm,List<T> list){
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
