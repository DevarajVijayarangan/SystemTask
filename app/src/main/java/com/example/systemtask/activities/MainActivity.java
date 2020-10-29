package com.example.systemtask.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.systemtask.R;
import com.example.systemtask.adapters.MyFragmentPagerAdapter;
import com.example.systemtask.fragments.DashboardFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyFragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0 && viewPagerAdapter.getFragment(viewPager.getCurrentItem()) instanceof DashboardFragment
                && ((DashboardFragment) viewPagerAdapter.getFragment(viewPager.getCurrentItem())).isDetailFragmentShowed) {
            ((DashboardFragment) viewPagerAdapter.getFragment(viewPager.getCurrentItem())).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}