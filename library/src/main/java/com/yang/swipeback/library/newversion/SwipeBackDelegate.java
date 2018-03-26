package com.yang.swipeback.library.newversion;

import android.view.View;


/**
 * Created by yang on 2018/3/24.
 */

public abstract class SwipeBackDelegate {

    protected SwipeBackLayout mSwipeBackLayout;

    public abstract View findViewById(int id);

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
