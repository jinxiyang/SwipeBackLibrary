package com.yang.swipeback.listener;

import android.app.Activity;
import android.view.View;

import com.yang.swipeback.delegate.ActivitySwipeBackDelegate;
import com.yang.swipeback.delegate.SwipeBackDelegate;
import com.yang.swipeback.util.ActivityStack;
import com.yang.swipeback.util.TranslucentActivityUtils;

/**
 * Author: 杨进玺
 * Time: 2018/4/10  17:18
 */

public class OnSwipeBackListenerActivityDefault implements OnSwipeBackListener {

    ActivitySwipeBackDelegate delegate;

    @Override
    public void setSwipeBackDelegate(SwipeBackDelegate swipeBackDelegate) {
        this.delegate = (ActivitySwipeBackDelegate) swipeBackDelegate;
    }

    @Override
    public View findPreviousView() {
        Activity backActivity = ActivityStack.getInstance().getPreviousActivity();
        if (backActivity != null && backActivity != delegate.getActivity()){
            return backActivity.getWindow().getDecorView();
        }
        return null;
    }

    @Override
    public void onStart() {
        TranslucentActivityUtils.convertActivityToTranslucent(delegate.getActivity());
    }

    @Override
    public void onBackToOriginPosition() {
        TranslucentActivityUtils.convertActivityFromTranslucent(delegate.getActivity());
    }

    @Override
    public void finish() {
        Activity backActivity = ActivityStack.getInstance().getPreviousActivity();
        if (backActivity instanceof OnSwipeBackCompletedListener){
            ((OnSwipeBackCompletedListener) backActivity).onBack();
        }
        delegate.getActivity().finish();
        delegate.getActivity().overridePendingTransition(0, 0);
    }
}
