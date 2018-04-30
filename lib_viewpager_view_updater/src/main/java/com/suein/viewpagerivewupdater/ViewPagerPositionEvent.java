package com.suein.viewpagerivewupdater;

import android.support.annotation.NonNull;

public class ViewPagerPositionEvent {
    @NonNull
    public ScrollState scrollState;

    @NonNull
    public int currentPage;

    public int nextPage = -1;

    public enum ScrollState {
        SCROLLING,
        SELECTED
    }

    public ViewPagerPositionEvent(@NonNull ScrollState scrollState, @NonNull int currentPage) {
        this.scrollState = scrollState;
        this.currentPage = currentPage;
    }

    public ViewPagerPositionEvent(@NonNull ScrollState scrollState, @NonNull int currentPage, int nextPage) {
        this.scrollState = scrollState;
        this.currentPage = currentPage;
        this.nextPage = nextPage;
    }

    @Override
    public String toString() {
        return "ViewPagerPositionEvent{" +
                "scrollState=" + scrollState +
                ", currentPage=" + currentPage +
                ", nextPage=" + nextPage +
                '}';
    }
}
