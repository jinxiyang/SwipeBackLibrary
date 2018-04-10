package com.yang.swipeback.listener;


/**
 * 一次成功的滑动返回完成时监听器（仅用于activity）
 */
public interface OnSwipeBackCompletedListener {

    /**
     * 当滑动超过阈值，上层界面退出栈，下层界面显示时
     */
    void onBack();
}
