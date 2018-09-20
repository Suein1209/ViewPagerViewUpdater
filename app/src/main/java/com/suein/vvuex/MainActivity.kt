package com.suein.vvuex

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.suein.sviewupdate.*
import com.suein.vvuex.databinding.ActivityMainBinding
import com.suein.vvuex.fragments.DummyFragment1
import com.suein.vvuex.fragments.DummyFragment2
import com.suein.vvuex.fragments.DummyFragment3
import com.suein.vvuex.fragments.DummyFragment4
import com.wemakeprice.partner.ui.comm.viewupdate.ViewPagerUpdateListener
import utils.v4.FragmentPagerItem
import utils.v4.FragmentPagerItemAdapter
import utils.v4.FragmentPagerItems

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val pages = FragmentPagerItems(this)
        pages.add(FragmentPagerItem.of("페이지 1", DummyFragment1::class.java))
        pages.add(FragmentPagerItem.of("페이지 2", DummyFragment2::class.java))
        pages.add(FragmentPagerItem.of("페이지 3", DummyFragment3::class.java))
        pages.add(FragmentPagerItem.of("페이지 4", DummyFragment4::class.java))
        val adapter = FragmentPagerItemAdapter(supportFragmentManager, pages)

        binding!!.viewPager.adapter = adapter

        //update 발생시 event를 전달하는 update listener 등록
        binding!!.viewPager.addOnPageChangeListener(ViewPagerUpdateListener(this::class))

        //관리될 page 등록
        putViewPagers(pages)

        //등록된 모든 페이지 update flag on
//        setOnUpdateViewAll(MainActivity::class)

        //this@MainActivity 클래스(Key)로 지정된 페이지 update flag on
//        setOnUpdateViewAll()

        //현재 보여지는 페이지가 1(ex)페이지 라면 상속된 fragment의 onUpdate()를 호출한다.
//        forceUpdateView(1)
//        forceUpdateView(MainActivity::class, 1)

        //특정 페이지 update flag를 설정한다.
//        setOnUpdateView(DummyFragment1::class)
//        setOnUpdateView(MainActivity::class, DummyFragment1::class)
    }
}
