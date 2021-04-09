package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listTabFragment = new ArrayList<>();
    private final List<String> listTabTitles = new ArrayList<>();



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listTabFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) listTabTitles.get(position);
    }

    public void AddFragment (Fragment fragment, String title) {
        listTabFragment.add(fragment);
        listTabTitles.add(title);
    }
}
