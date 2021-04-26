package com.mcbedrock.minecraftnews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mcbedrock.minecraftnews.betaChangelog.betaRecyclerViewFragment;
import com.mcbedrock.minecraftnews.bedrockRealeseChangelog.realeseRecyclerViewFragment;
import com.mcbedrock.minecraftnews.javaRealeseChangelog.javaRecyclerViewFragment;
import com.mcbedrock.minecraftnews.snapshotChangelog.snapshotRecyclerViewFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new javaRecyclerViewFragment();
            case 2:
                return new betaRecyclerViewFragment();
            case 3:
                return new snapshotRecyclerViewFragment();
        }

        return new realeseRecyclerViewFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
