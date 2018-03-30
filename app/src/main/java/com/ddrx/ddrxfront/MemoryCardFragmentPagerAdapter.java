package com.ddrx.ddrxfront;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dokym on 2018/3/30.
 */

public class MemoryCardFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mList;

    public MemoryCardFragmentPagerAdapter(List<Fragment> list, FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
}
