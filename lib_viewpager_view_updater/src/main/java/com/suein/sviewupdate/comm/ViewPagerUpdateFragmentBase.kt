package com.suein.sviewupdate.comm

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.suein.sviewupdate.*
import com.wemakeprice.partner.ui.comm.viewupdate.ViewPagerUpdateListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import utils.v4.FragmentPagerItem

/**
 * SViewPagerUpdate를 적용할 Fragment의 추상클래스
 * Email : suein1209@gmail.com
 * Created by suein1209 on 2018. 8. 6..
 */
abstract class ViewPagerUpdateFragmentBase : Fragment() {

    @Suppress("PrivatePropertyName")
    private val ARG_PAGE_INDEX = FragmentPagerItem.getPositionKey()

    abstract fun onUpdate()

    private var pageIndex = -1

    fun getPageIndex(): Int {
        return pageIndex
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        checkNotNullSafety(arguments) {
            SLog.e("[Info][Error] ViewPagerViewUpdaterFragmentBase arguments is null.")
        }?.run {
            if (containsKey(ARG_PAGE_INDEX)) {
                checkNotNull(getInt(ARG_PAGE_INDEX)) {
                    SLog.e("[Info][Error] ViewPagerViewUpdaterFragmentBase arguments is null..")
                }.let {
                    pageIndex = it
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        val currentIndex = (context as Activity).getCurrentItemIndex()
        if (isCurrentView(ViewPagerUpdatePositionEvent(context!!::class.qualifiedName, ViewPagerUpdatePositionEvent.ScrollState.SELECTED, currentIndex)) && isNeedUpdate()) {
            SLog.i("[Info][onResume] Update view! Index is $currentIndex")
            onUpdate()
            resetUpdateFlag()
        }
        EventBus.getDefault().register(this)
    }

    private fun isCurrentView(event: ViewPagerUpdatePositionEvent) = pageIndex == getUpdateViewIndex(event) && context!!::class.qualifiedName == event.keyClassName

    private fun getUpdateViewIndex(event: ViewPagerUpdatePositionEvent): Int =
            if (event.scrollState == ViewPagerUpdatePositionEvent.ScrollState.SELECTED) event.currentPage else event.nextPage

    /**
     * [ViewPagerUpdateListener]로 부터 update 이벤트가 전달되면 현재 구현돼 있는 fragment를 view update 한다.
     */
    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onViewUpdateEvent(event: ViewPagerUpdatePositionEvent) {
        if (isCurrentView(event) && (isFirstShow() || isNeedUpdate() || event.isForceUpdate)) {
            SLog.i("[Info][EventBus] Update view! Index is ${getUpdateViewIndex(event)}")
            onUpdate()
            resetUpdateFlag()
        }
    }
}