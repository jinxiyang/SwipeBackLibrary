package com.yang.swipeback.library;

import android.support.v4.app.FragmentActivity;

/**
 * Created by yang on 2016/10/13.
 */

public interface ISwipeBackActivity {

    SwipeBackLayout getSwipeBackLayout();

    FragmentActivity getPreActivity();


    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先; false: Activity不允许滑动退出
     */
    boolean swipeBackPriority();

    void setSwipeBackEnable(boolean enable);

    boolean isTransparent();
}
