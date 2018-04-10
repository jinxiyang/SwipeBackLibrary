package com.yang.swipeback.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yang.swipeback.delegate.FragmentSwipeBackDelegate;
import com.yang.swipeback.delegate.SwipeBackDelegate;

import java.util.List;

/**
 * Author: 杨进玺
 * Time: 2018/4/10  17:18
 */

public class OnSwipeBackListenerFragmentDefault implements OnSwipeBackListener {

    FragmentSwipeBackDelegate delegate;

    @Override
    public void setSwipeBackDelegate(SwipeBackDelegate swipeBackDelegate) {
        this.delegate = (FragmentSwipeBackDelegate) swipeBackDelegate;
    }

    @Override
    public View findPreviousView() {
        Fragment currFragment = delegate.getFragment();
        List<Fragment> fragments = currFragment.getFragmentManager().getFragments();
        int index = fragments.indexOf(currFragment);
        if (index > 0 && fragments.get(index - 1) != null) {
            return fragments.get(index - 1).getView();
        }
        return null;
    }

    @Override
    public void onStart() {
        View preView = delegate.getPreView();
        if (preView != null && preView.getVisibility() != View.VISIBLE) {
            preView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackToOriginPosition() {
        View preView = delegate.getPreView();
        if (preView != null && preView.getVisibility() != View.INVISIBLE) {
            preView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void finish() {
        FragmentActivity activity = delegate.getFragment().getActivity();
        if (activity != null) {
            activity.getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
