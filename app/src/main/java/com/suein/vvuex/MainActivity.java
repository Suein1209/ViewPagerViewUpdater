package com.suein.vvuex;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.suein.viewpagerivewupdater.ViewPagerUpdater;
import com.suein.vvuex.databinding.ActivityMainBinding;
import com.suein.vvuex.fragments.DummyFragment;

import utils.v4.FragmentPagerItem;
import utils.v4.FragmentPagerItemAdapter;
import utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("페이지 1", DummyFragment.class));
        pages.add(FragmentPagerItem.of("페이지 2", DummyFragment.class));
        pages.add(FragmentPagerItem.of("페이지 3", DummyFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);

        binding.viewPager.setAdapter(adapter);
        ViewPagerUpdater.getInstance().setViewPager(binding.viewPager);
        ViewPagerUpdater.getInstance().setWithoutPage(2, 3);
    }
}
