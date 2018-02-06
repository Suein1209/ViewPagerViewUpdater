package com.suein.viewpagerivewupdater;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
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
}
