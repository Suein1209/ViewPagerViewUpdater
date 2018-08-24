package com.suein.sviewupdate

/**
 * View update를 위해 전달되는 event
 * Email : suein1209@gmail.com
 * Created by suein1209 on 2018. 8. 6..
 */
class ViewPagerUpdatePositionEvent(val scrollState: ScrollState, val currentPage: Int = 0, val nextPage: Int = -1, val isForceUpdate: Boolean = false) {
    enum class ScrollState {
        SCROLLING,
        SELECTED
    }

    override fun toString(): String {
        return "ViewPagerUpdatePositionEvent{" +
                "scrollState=" + scrollState +
                ", currentPage=" + currentPage +
                ", nextPage=" + nextPage +
                '}'.toString()
    }
}
