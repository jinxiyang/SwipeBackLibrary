package com.yang.swipeback.library;

import android.view.View;

/**
 * Created by yang on 2018/3/24.
 */

public abstract class SwipeBackDelegate {
    private boolean enable = true;

    protected SwipeBackLayout mSwipeBackLayout;
    protected SwipeBackLayout mPreSwipeBackLayout;

    public abstract View findViewById(int id);

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    public SwipeBackLayout getPreSwipeBackLayout() {
        if (mPreSwipeBackLayout == null){
            mPreSwipeBackLayout = findPreSwipeBackLayout();
        }
        return mPreSwipeBackLayout;
    }

    public abstract SwipeBackLayout findPreSwipeBackLayout();

    public void setSwipeBackEnable(boolean enable){
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void release(){
        mPreSwipeBackLayout = null;
    }
}
