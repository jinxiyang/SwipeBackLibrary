package com.yang.swipeback.library;

/**
 * Created by yang on 2016/10/13.
 */

public interface ISwipeBackFragment {
    SwipeBackLayout getSwipeBackLayout();

    ISwipeBackFragment getPreFragment();

    boolean isLocking();

    void setLockable(boolean lockable);

    void setSwipeBackEnable(boolean enable);
}
