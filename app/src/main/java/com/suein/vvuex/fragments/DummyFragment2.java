package com.suein.vvuex.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suein.sviewupdate.comm.ViewPagerUpdateFragmentBase;
import com.suein.vvuex.R;
import com.suein.vvuex.databinding.FragmentDummyBinding;

public class DummyFragment2 extends ViewPagerUpdateFragmentBase {

    private FragmentDummyBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);
        binding = DataBindingUtil.bind(view);
        return view;
    }

    @Override
    public void onUpdate() {
        binding.tvCenterText.setText("View Updated");
        Log.e("suein", "DummyFragment2 view updated");
    }
}
