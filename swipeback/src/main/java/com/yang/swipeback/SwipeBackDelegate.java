package com.yang.swipeback;

import android.view.View;


/**
 * Created by yang on 2018/3/24.
 */

public abstract class SwipeBackDelegate {

    protected SwipeBackLayout mSwipeBackLayout;

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public void setSwipeBackEnable(boolean enable){
        mSwipeBackLayout.setEnableGesture(enable);
    }

    public boolean isSwipeBackEnable() {
        return mSwipeBackLayout.isEnableGesture();
    }

    public abstract View getPreView();
}
