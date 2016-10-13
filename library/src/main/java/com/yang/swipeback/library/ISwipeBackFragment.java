package com.yang.swipeback.library;

import android.support.v4.app.Fragment;

/**
 * Created by yang on 2016/10/13.
 */

public interface ISwipeBackFragment {
    SwipeBackLayout getSwipeBackLayout();

    Fragment getPreFragment();

    boolean isLocking();

    void setLockable(boolean lockable);

    void setSwipeBackEnable(boolean enable);
}
