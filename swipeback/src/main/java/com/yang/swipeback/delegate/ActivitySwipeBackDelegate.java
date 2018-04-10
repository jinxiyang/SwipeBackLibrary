package com.yang.swipeback.delegate;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yang.swipeback.SwipeBackManager;
import com.yang.swipeback.layout.SwipeBackLayout;
import com.yang.swipeback.listener.OnSwipeBackListenerActivityDefault;


public class ActivitySwipeBackDelegate extends SwipeBackDelegate {

    private Activity activity;
    private View preView = null;

    public ActivitySwipeBackDelegate(Activity activity) {
        this.activity = activity;
        setOnSwipeBackListener(new OnSwipeBackListenerActivityDefault());
    }

    @Override
    public View getPreView() {
        if (preView == null) {
            preView = onSwipeBackListener.findPreviousView();
        }
        return preView;
    }

    public void onPostCreate() {
        Window window = activity.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setBackgroundDrawable(null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout = new SwipeBackLayout(activity);
        mSwipeBackLayout.setLayoutParams(params);

        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) window.getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        mSwipeBackLayout.addView(decorChild);
        mSwipeBackLayout.setContentView(decorChild);
        decor.addView(mSwipeBackLayout);

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEnableGesture(true);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (state == SwipeBackLayout.STATE_IDLE){
                    SwipeBackManager.setSwippingBack(false);
                    if (scrollPercent >= 1){
                        onSwipeBackListener.finish();
                    }else if (scrollPercent <= 0){
                        if (SwipeBackManager.getPreviousViewAnim() != null && getPreView() != null){
                            SwipeBackManager.getPreviousViewAnim().backToOriginalPositionWithoutAnim(getPreView());
                        }
                        onSwipeBackListener.onBackToOriginPosition();
                    }
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                SwipeBackManager.setSwippingBack(true);
                onSwipeBackListener.onStart();
            }

            @Override
            public void onScrollOverThreshold() {
            }

            @Override
            public void onScroll(int edgeFlag, int left, int top, int dx, int dy, float scrollPercent) {
                if (SwipeBackManager.getPreviousViewAnim() != null && getPreView() != null){
                    SwipeBackManager.getPreviousViewAnim().onScroll(getPreView(), left, top, dx, dy, scrollPercent);
                }
            }
        });
    }

    public void onDestroy() {
        activity = null;
        preView = null;
    }

    public Activity getActivity() {
        return activity;
    }
}
