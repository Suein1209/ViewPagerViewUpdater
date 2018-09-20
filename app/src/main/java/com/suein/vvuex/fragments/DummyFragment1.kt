package com.suein.vvuex.fragments

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.suein.sviewupdate.comm.ViewPagerUpdateFragmentBase
import com.suein.vvuex.R
import com.suein.vvuex.databinding.FragmentDummyBinding

class DummyFragment1 : ViewPagerUpdateFragmentBase() {

    private var binding: FragmentDummyBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dummy, container, false)
        binding = DataBindingUtil.bind(view)
        return view
    }

    override fun onUpdate() {
        binding!!.tvCenterText.text = "View Updated"
        Log.e("suein", "DummyFragment1 view updated")
    }
}
