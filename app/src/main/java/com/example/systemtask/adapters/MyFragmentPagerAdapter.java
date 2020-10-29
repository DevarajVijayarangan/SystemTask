package com.example.systemtask.adapters;


import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.systemtask.fragments.DashboardFragment;
import com.example.systemtask.fragments.HomeFragment;
import com.example.systemtask.fragments.NotificationsFragment;

import org.json.JSONArray;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] TAB_NAMES = {"Home", "Notifications", "Profile"};
    private Fragment mCurrentFragment;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public Fragment getFragment(int index) {
        return registeredFragments.get(index);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new NotificationsFragment();
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_NAMES[position];
    }


    @Override
    public int getCount() {
        return 3;
    }
}