package com.suein.viewpagerivewupdater.comm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.common.base.Preconditions;
import com.suein.viewpagerivewupdater.ViewPagerPositionEvent;
import com.suein.viewpagerivewupdater.ViewPagerUpdater;

import lombok.AccessLevel;
import lombok.Getter;
import utils.v4.FragmentPagerItem;

public abstract class ViewPagerViewUpdaterFragmentBase extends Fragment {

    public static String ARG_PAGE_INDEX = FragmentPagerItem.getPositionKey();

    public abstract void onUpdate();

    @Getter(AccessLevel.PROTECTED)
    private int pageIndex = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(ARG_PAGE_INDEX)) {
                pageIndex = Preconditions.checkNotNull(args.getInt(ARG_PAGE_INDEX));
            }
        }
        super.onCreate(savedInstanceState);
    }

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst && pageIndex == 0){
            isFirst = false;
            ViewPagerUpdater.getInstance().onSelectedEvent(new ViewPagerPositionEvent(ViewPagerPositionEvent.ScrollState.SELECTED, 0));
        }
    }
}
