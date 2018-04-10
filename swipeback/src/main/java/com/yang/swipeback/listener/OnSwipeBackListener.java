package com.yang.swipeback.listener;

import android.view.View;

import com.yang.swipeback.delegate.SwipeBackDelegate;

/**
 * 不同的应用，开发者可能有不同的fragment事务栈，抽出接口，开发者实现符合自己应用的实现者
 *
 * Author: 杨进玺
 * Time: 2018/4/10  16:42
 */

public interface OnSwipeBackListener {

    /**
     * 传入滑动代理，下面在一些操作中使用
     * @param swipeBackDelegate
     */
    void setSwipeBackDelegate(SwipeBackDelegate swipeBackDelegate);

    /**
     * 找到上一个fragment的View，它是当前fragment的背景，滑动时背景联动
     */
    View findPreviousView();

    /**
     * 当边缘滑动要开始时，可以设置上一个fragment（即背景）的View可见
     */
    void onStart();

    /**
     * 返回到原始位置时。手指侧边缘滑动，然后又滑动回原位置，可以设置上一个fragment（即背景）的View不可见
     */
    void onBackToOriginPosition();

    /**
     * 当手指离开时，边缘滑动超过阈值，这里去结束当前fragment（即fragment出栈）
     */
    void finish();
}
