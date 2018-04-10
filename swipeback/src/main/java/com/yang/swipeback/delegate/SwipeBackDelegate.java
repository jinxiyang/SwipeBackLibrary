package com.yang.swipeback.delegate;

import android.support.annotation.NonNull;
import android.view.View;

import com.yang.swipeback.layout.SwipeBackLayout;
import com.yang.swipeback.listener.OnSwipeBackListener;


/**
 * Created by yang on 2018/3/24.
 */

public abstract class SwipeBackDelegate {

    protected SwipeBackLayout mSwipeBackLayout;

    protected OnSwipeBackListener onSwipeBackListener;

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

    public OnSwipeBackListener getOnSwipeBackListener() {
        return onSwipeBackListener;
    }

    public void setOnSwipeBackListener(@NonNull OnSwipeBackListener onSwipeBackListener) {
        this.onSwipeBackListener = onSwipeBackListener;
        onSwipeBackListener.setSwipeBackDelegate(this);
    }
}
