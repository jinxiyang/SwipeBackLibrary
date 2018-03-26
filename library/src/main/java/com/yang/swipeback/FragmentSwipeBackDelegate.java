package com.yang.swipeback;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;


public class FragmentSwipeBackDelegate extends SwipeBackDelegate {

    private Fragment fragment;

    //默认不锁住动画生成
    private boolean mLocking = false;

    public FragmentSwipeBackDelegate(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View findViewById(int id) {
        return null;
    }

    @Override
    public View getPreView() {
        return null;
    }


    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (mLocking) {
            return null;
        }
        return fragment.onCreateAnimation(transit, enter, nextAnim);
    }
}
