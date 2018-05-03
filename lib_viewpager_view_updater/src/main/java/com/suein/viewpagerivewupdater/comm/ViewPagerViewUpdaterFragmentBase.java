package com.suein.viewpagerivewupdater.comm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.common.base.Preconditions;
import com.suein.viewpagerivewupdater.ViewPagerPositionEvent;
import com.suein.viewpagerivewupdater.ViewPagerUpdater;

import utils.v4.FragmentPagerItem;

public abstract class ViewPagerViewUpdaterFragmentBase extends Fragment {

    public static String ARG_PAGE_INDEX = FragmentPagerItem.getPositionKey();

    public abstract void onUpdate();

    private int pageIndex = -1;

    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(ARG_PAGE_INDEX)) {
                pageIndex = Preconditions.checkNotNull(args.getInt(ARG_PAGE_INDEX));
            }
        }
        isFirstUpdatedView = true;
        super.onCreate(savedInstanceState);
    }

    private boolean isFirstUpdatedView = true;

    public boolean isFirstUpdatedView() {
        return isFirstUpdatedView;
    }

    public void setFirstUpdatedView(boolean isFirstUpdatedView) {
        this.isFirstUpdatedView = isFirstUpdatedView;

    }

    private boolean isFirstSeenView = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstSeenView && pageIndex == 0) {
            isFirstSeenView = false;
            ViewPagerUpdater.getInstance().onSelectedEvent(new ViewPagerPositionEvent(ViewPagerPositionEvent.ScrollState.SELECTED, 0));
        }
    }
}
