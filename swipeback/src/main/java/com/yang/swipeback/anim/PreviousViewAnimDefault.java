package com.yang.swipeback.anim;

import android.view.View;

/**
 * Author: 杨进玺
 * Time: 2018/4/10  16:20
 */

public class PreviousViewAnimDefault implements IPreviousViewAnim {

    @Override
    public void onScroll(View view, int left, int top, int dx, int dy, float scrollPercent) {
        //mScrollPercent 变化时: 0 -> 0.95 -> 1
        //背景translationX的变化: -0.4 -> 0 -> 0
        float translationX = (float) (0.4 / 0.95 * (scrollPercent - 0.95) * view.getWidth());
        if (translationX > 0) {
            translationX = 0;
        }
        view.setTranslationX(translationX);
    }

    @Override
    public void backToOriginalPositionWithoutAnim(View view) {
        view.setTranslationX(0);
    }

}
