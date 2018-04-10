package com.yang.swipeback.anim;

import android.view.View;

/**
 *
 * 当手指从左边边缘开始滑动当前activity或者fragment时，背景页面（即上一个页面）的动画
 *
 * Author: 杨进玺
 * Time: 2018/4/10  16:07
 */

public interface IPreviousViewAnim {

    /**
     * @param left        New X coordinate of the left edge of the view
     * @param top         New Y coordinate of the top edge of the view
     * @param dx          Change in X position from the last call
     * @param dy          Change in Y position from the last call
     * @param scrollPercent
     */
    void onScroll(View view, int left, int top, int dx, int dy, float scrollPercent);


    void backToOriginalPositionWithoutAnim(View view);
}
