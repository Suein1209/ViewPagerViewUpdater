package com.wemakeprice.partner.ui.comm.viewupdate

import android.support.v4.view.ViewPager
import com.suein.sviewupdate.ViewPagerUpdatePositionEvent
import com.suein.sviewupdate.updateMgr
import org.greenrobot.eventbus.EventBus
import kotlin.reflect.KClass

/**
 * View Pager의 페이지를 스크롤시 update 되는 view를 확인해 fragment에 event를 전달한다.
 * Email : suein1209@gmail.com
 * Created by suein1209 on 2018. 8. 6..
 */
class ViewPagerUpdateListener(private val activityClazz: KClass<*>) : ViewPager.OnPageChangeListener {

    init {
        updateMgr.setCurrnetItemIndex(activityClazz, 0)
    }

    private var scrollState: Int = 0
    private var isPostEvent = false

    private var currentItem = 0

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        //좌우 드래그의 상태를 보고 다음에 보여질 fragment가 어떤 것인지 전달한다
        if (ViewPager.SCROLL_STATE_DRAGGING == scrollState && !isPostEvent) {
            if (positionOffset == 0f && positionOffsetPixels == 0) {
                return
            }

            var viewPagerPositionEvent: ViewPagerUpdatePositionEvent? = null
            if (positionOffset >= 0.5) {
                viewPagerPositionEvent = ViewPagerUpdatePositionEvent(ViewPagerUpdatePositionEvent.ScrollState.SCROLLING, currentItem, currentItem - 1)

            } else if (positionOffset < 0.5) {
                viewPagerPositionEvent = ViewPagerUpdatePositionEvent(ViewPagerUpdatePositionEvent.ScrollState.SCROLLING, currentItem, currentItem + 1)
            }

            viewPagerPositionEvent?.let {
                EventBus.getDefault().post(it)
            }

            isPostEvent = true
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            isPostEvent = false
        }
        scrollState = state
    }

    override fun onPageSelected(position: Int) {
        currentItem = position
        updateMgr.setCurrnetItemIndex(activityClazz, position)

        if (!isPostEvent) {
            EventBus.getDefault().post(ViewPagerUpdatePositionEvent(ViewPagerUpdatePositionEvent.ScrollState.SELECTED, position))
        }
        isPostEvent = false
    }
}