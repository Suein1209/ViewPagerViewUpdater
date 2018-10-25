@file:Suppress("unused")

package com.suein.sviewupdate

import android.app.Activity
import android.util.Log
import com.suein.sviewupdate.comm.SLog
import com.suein.sviewupdate.comm.SViewPagerConstants
import com.suein.sviewupdate.comm.ViewPagerUpdateFragmentBase
import com.suein.sviewupdate.comm.checkNotNullSafety
import org.greenrobot.eventbus.EventBus
import utils.v4.FragmentPagerItems
import kotlin.reflect.KClass

/**
 * 페이지 이동시 동적으로 ViewPager의 View를 UI Update 하는 기능을 제공한다.
 * 일반적 ViewPager는 상위 Activity가 실행될때 포함된 최대 3개의 View를 한번에 update 한다.
 * Email : suein1209@gmail.com
 * Created by suein1209 on 2018. 8. 6..
 */
class ViewPagerUpdateManager {

    private object Holder {
        val INSTANCE = ViewPagerUpdateManager()
    }

    companion object {
        val instance: ViewPagerUpdateManager by lazy { Holder.INSTANCE }
    }

    var isEnableLogging = false
        set(value) {
            SLog.isEnableLogging = value
        }

    private var viewPageMap: HashMap<String, MutableList<UpdateItem>> = hashMapOf()

    private val currentViewMap: HashMap<String, Int> = hashMapOf()

    /**
     * SViewPagerUpdate를 적용시킬 Fragment view를 등록한다.
     */
    internal fun putViewPager(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>, pageIndex: Int) {
        checkNotNullSafety(viewPageMap[keyClass.qualifiedName]) {
            viewPageMap[keyClass.qualifiedName!!] = mutableListOf()
        }?.let {}
        viewPageMap[keyClass.qualifiedName]!!.add(UpdateItem(elementFragment.qualifiedName!!, pageIndex))
    }

    fun isExistKeyClass(keyClassName: String?) = viewPageMap.containsKey(keyClassName)
    fun isEmptyKeyClass() = viewPageMap.isEmpty()
    fun resetKeyClassMap() = viewPageMap.clear()

    private fun doProcNotExistsKeyClass(keyClassName: String?) {
        SLog.e("존재 하지 않는 Key Class 입니다. Class Name = $keyClassName")
        if (SViewPagerConstants.isOccursNotExistsClassKey) {
            error("존재 하지 않는 Key Class 입니다. Class Name = $keyClassName")
        }
    }

    /**
     * update flag가 설정되어 있는지 확인한다.
     */
    internal fun isNeedUpdate(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>): Boolean {
        if (isExistKeyClass(keyClass.qualifiedName)) {
            val fragmentName = elementFragment.qualifiedName
            checkNotNullSafety(viewPageMap[keyClass.qualifiedName]!!.find { it.fragmentClass == fragmentName }) {
                return false
            }?.run {
                return isNeedUpdate
            }
        } else {
            doProcNotExistsKeyClass(keyClass.qualifiedName)
        }
        return false
    }

    /**
     * 처음으로 보여지는지 확인한다.
     * (초기화 유/무 확인)
     */
    internal fun isFirstShow(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>): Boolean {
        if (isExistKeyClass(keyClass.qualifiedName)) {
            val fragmentName = elementFragment.qualifiedName
            checkNotNullSafety(viewPageMap[keyClass.qualifiedName]!!.find { it.fragmentClass == fragmentName }) {
                return false
            }?.run {
                return isFirstShow
            }
        } else {
            doProcNotExistsKeyClass(keyClass.qualifiedName)
        }
        return false
    }

    /**
     * update flag와 first show flag를 초기화 한다.
     */
    internal fun resetUpdateFlag(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>) {
        if (isExistKeyClass(keyClass.qualifiedName)) {
            val fragmentName = elementFragment.qualifiedName
            checkNotNullSafety(viewPageMap[keyClass.qualifiedName]!!.find { it.fragmentClass == fragmentName }) {
                error("존재 하지 않는 Key Class 입니다. Class Name = ${keyClass.qualifiedName}")
            }?.run {
                isNeedUpdate = false
                isFirstShow = false
            }
        } else {
            doProcNotExistsKeyClass(keyClass.qualifiedName)
        }
    }

    /**
     * 지정된 1개의 view의 update flag를 설정한다.
     * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
     */
    internal fun setOnUpdateView(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>) {
        if (isExistKeyClass(keyClass.qualifiedName)) {
            val fragmentName = elementFragment.qualifiedName
            checkNotNullSafety(viewPageMap[keyClass.qualifiedName]!!.find { it.fragmentClass == fragmentName }) {
                error("존재 하지 않는 Key Class 입니다. Class Name = ${keyClass.qualifiedName}")
            }?.run {
                isNeedUpdate = true
            }
        } else {
            doProcNotExistsKeyClass(keyClass.qualifiedName)
        }
    }

    /**
     * ViewPager에 등록된 모든 Fragment View의 update flag를 설정한다.
     * 각 해당하는 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
     */
    internal fun setOnUpdateViewAll(keyClass: KClass<*>) {
        if (isExistKeyClass(keyClass.qualifiedName)) {
            viewPageMap[keyClass.qualifiedName]!!.forEach {
                Log.e("ServiceDev2", "setOnUpdateViewAll = 3")
                it.isNeedUpdate = true
            }
        } else {
            doProcNotExistsKeyClass(keyClass.qualifiedName)
        }
    }

    internal fun setCurrnetItemIndex(keyClass: KClass<*>, index: Int) {
        currentViewMap[keyClass.qualifiedName!!] = index
    }

    internal fun getCurrentItemIndex(keyClass: KClass<*>): Int {
        return currentViewMap[keyClass.qualifiedName] ?: -1
    }
}

internal val updateMgr by lazy { ViewPagerUpdateManager.instance }

internal class UpdateItem(var fragmentClass: String, var index: Int = -1, var isNeedUpdate: Boolean = false, var isFirstShow: Boolean = true)

/**
 * update flag가 설정되어 있는지 확인한다.
 */
fun ViewPagerUpdateFragmentBase.isNeedUpdate() = updateMgr.isNeedUpdate(context!!::class, this::class)

/**
 * 처음으로 보여지는지 확인한다.
 * (초기화 유/무 확인)
 */
fun ViewPagerUpdateFragmentBase.isFirstShow() = updateMgr.isFirstShow(context!!::class, this::class)

/**
 * update flag와 first show flag를 초기화 한다.
 */
fun ViewPagerUpdateFragmentBase.resetUpdateFlag() = updateMgr.resetUpdateFlag(context!!::class, this::class)

/**
 * 현재 메모리 상에 존재 하는 fragment view를 강제로 update 한다.
 * 단, 호출되는 시점에는 update 하는 view가 bg상태에 있으면 update가 되지 않는다.
 */
fun ViewPagerUpdateFragmentBase.forceUpdateView(keyClass: KClass<*>) {
    EventBus.getDefault().post(ViewPagerUpdatePositionEvent(keyClass.qualifiedName, ViewPagerUpdatePositionEvent.ScrollState.SELECTED, getPageIndex(), -1, true))
}

/**
 * 현재 메모리 상에 존재 하는 fragment view를 강제로 update 한다.
 * 단, 호출되는 시점에는 update 하는 view가 bg상태에 있으면 update가 되지 않는다.
 */
fun ViewPagerUpdateFragmentBase.forceUpdateView() {
    EventBus.getDefault().post(ViewPagerUpdatePositionEvent(context!!::class.qualifiedName, ViewPagerUpdatePositionEvent.ScrollState.SELECTED, getPageIndex(), -1, true))
}

/**
 * 현재 메모리 상에 존재 하는 fragment view를 강제로 update 한다.
 * 단, 호출되는 시점에는 update 하는 view가 bg상태에 있으면 update가 되지 않는다.
 */
fun Activity.forceUpdateView(keyClass: KClass<*>, pageIndex: Int) {
    EventBus.getDefault().post(ViewPagerUpdatePositionEvent(keyClass.qualifiedName, ViewPagerUpdatePositionEvent.ScrollState.SELECTED, pageIndex, -1, true))
}

/**
 * 현재 메모리 상에 존재 하는 fragment view를 강제로 update 한다.
 * 단, 호출되는 시점에는 update 하는 view가 bg상태에 있으면 update가 되지 않는다.
 */
fun Activity.forceUpdateView(pageIndex: Int) {
    EventBus.getDefault().post(ViewPagerUpdatePositionEvent(this::class.qualifiedName, ViewPagerUpdatePositionEvent.ScrollState.SELECTED, pageIndex, -1, true))
}

/**
 * ViewPager에 등록된 모든 Fragment View의 update flag를 설정한다.
 * 각 해당하는 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun Activity.setOnUpdateViewAll() = updateMgr.setOnUpdateViewAll(this::class)

/**
 * ViewPager에 등록된 모든 Fragment View의 update flag를 설정한다.
 * 각 해당하는 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun Activity.setOnUpdateViewAll(keyClass: KClass<*>) = updateMgr.setOnUpdateViewAll(keyClass)

/**
 * 지정된 1개의 view의 update flag를 설정한다.
 * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun Activity.setOnUpdateView(elementFragment: KClass<out ViewPagerUpdateFragmentBase>) = updateMgr.setOnUpdateView(this::class, elementFragment)

/**
 * 지정된 1개의 view의 update flag를 설정한다.
 * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun Activity.setOnUpdateView(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>) = updateMgr.setOnUpdateView(keyClass, elementFragment)

/**
 * ViewPager에 등록된 모든 Fragment View의 update flag를 설정한다.
 * 각 해당하는 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun ViewPagerUpdateFragmentBase.setOnUpdateViewAll() = updateMgr.setOnUpdateViewAll(context!!::class)

/**
 * 지정된 1개의 view의 update flag를 설정한다.
 * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun ViewPagerUpdateFragmentBase.setOnUpdateView() = updateMgr.setOnUpdateView(context!!::class, this::class)

/**
 * 지정된 1개의 view의 update flag를 설정한다.
 * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun ViewPagerUpdateFragmentBase.setOnUpdateView(elementFragment: KClass<out ViewPagerUpdateFragmentBase>) = updateMgr.setOnUpdateView(context!!::class, elementFragment)

/**
 * 지정된 1개의 view의 update flag를 설정한다.
 * 설정된 화면의 onResume()메소드나 Drag를 통해 보여졌을때 화면을 update 한다.
 */
fun ViewPagerUpdateFragmentBase.setOnUpdateView(keyClass: KClass<*>, elementFragment: KClass<out ViewPagerUpdateFragmentBase>) = updateMgr.setOnUpdateView(keyClass, elementFragment)

/**
 * SViewPagerUpdate를 적용시킬 Fragment view를 등록한다.
 */
fun Activity.putViewPager(elementFragment: KClass<out ViewPagerUpdateFragmentBase>, pageIndex: Int) = updateMgr.putViewPager(this::class, elementFragment, pageIndex)

/**
 * SViewPagerUpdate를 적용시킬 Fragment view들을 등록한다.
 * view들을 등록후에는 첫번째 페이지를 update 시켜준다.
 * (명시적으로 0번째 페이지를 update 하지 않는다면 무시된다.)
 */
fun Activity.putViewPagers(elementFragment: FragmentPagerItems) {
    elementFragment.indices.forEach {
        updateMgr.putViewPager(this::class, elementFragment[it].fragmentClazz.kotlin, it)
    }
    setOnUpdateView(elementFragment[0].fragmentClazz.kotlin)
}

/**
 * [ViewPagerUpdateListener]를 통해서 update 된 현재 current view index를 반환한다.
 */
fun Activity.getCurrentItemIndex() = updateMgr.getCurrentItemIndex(this::class)

/**
 * KeyClass에 등록된 viewpager가 표시하는 current 화면의 index를 저장한다.
 */
internal fun Activity.setCurrnetItemIndex(index: Int) {
    updateMgr.setCurrnetItemIndex(this::class, index)
}