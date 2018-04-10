package com.yang.swipeback.delegate;

import android.support.annotation.NonNull;
import android.view.View;

import com.yang.swipeback.layout.SwipeBackLayout;
import com.yang.swipeback.listener.OnSwipeBackListener;


/**
 * 滑动返回代理抽象类
 *
 * Created by yang on 2018/3/24.
 */

public abstract class SwipeBackDelegate {

    protected SwipeBackLayout mSwipeBackLayout;

    protected OnSwipeBackListener onSwipeBackListener;

    protected boolean swipebackEnable = true;

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    /**
     * 设置是否支持滑动返回
     * @param enable
     */
    public void setSwipeBackEnable(boolean enable){
        swipebackEnable = enable;
        if (mSwipeBackLayout != null){
            mSwipeBackLayout.setEnableGesture(enable);
        }
    }

    /**
     * 是否支持滑动返回
     * @return
     */
    public boolean isSwipeBackEnable() {
        return mSwipeBackLayout != null ? mSwipeBackLayout.isEnableGesture() : swipebackEnable;
    }

    /**
     * 获取上一个界面（即背景界面），滑动界面时实现背景联动
     * @return
     */
    public abstract View getPreView();

    public OnSwipeBackListener getOnSwipeBackListener() {
        return onSwipeBackListener;
    }


    public void setOnSwipeBackListener(@NonNull OnSwipeBackListener onSwipeBackListener) {
        this.onSwipeBackListener = onSwipeBackListener;
        onSwipeBackListener.setSwipeBackDelegate(this);
    }
}
