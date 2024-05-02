package com.example.cartyproject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StorePagerAdapter extends FragmentStateAdapter {
    public StorePagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new MyStoresFragment() : new AllStoresFragment();
    }

    @Override
    public int getItemCount() {
        return 2; // We have two tabs
    }
}

