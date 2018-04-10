package com.yang.swipeback.listener;

import android.view.View;

import com.yang.swipeback.delegate.SwipeBackDelegate;

/**
 * 滑动返回监听接口，需要activity、fragment实现
 *
 *
 * 注：不同的应用可能维护者不同的activity栈、fragment事务栈。若有需要，开发者可以继承实现符合自己项目的栈。
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
     * 找到底下一层界面View，它是当前界面的背景，滑动时背景联动
     */
    View findPreviousView();

    /**
     * 当边缘滑动要开始时，可以设置底下一层界面View（即背景）可见
     */
    void onStart();

    /**
     * 返回到原始位置时。手指侧边缘滑动，然后又滑动回原位置，可以设置底下一层界面View（即背景）的View不可见，回到原始状态
     */
    void onBackToOriginPosition();

    /**
     * 当手指离开时，边缘滑动超过阈值，这里去结束当前界面，activity.finish(), fragment出栈
     */
    void finish();
}
