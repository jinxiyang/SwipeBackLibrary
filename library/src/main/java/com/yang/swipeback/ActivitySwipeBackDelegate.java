package com.yang.swipeback;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by yang on 2018/3/24.
 */

public class ActivitySwipeBackDelegate extends SwipeBackDelegate {

    private Activity activity;
    private Activity preActivity;

    public ActivitySwipeBackDelegate(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View findViewById(int id) {
        View view = activity.findViewById(id);
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return view;
    }

    @Override
    public View getPreView() {
        Activity activity = preActivity;
        if (activity == null) {
            activity = preActivity = ActivityStack.getInstance().getBackActivity();
        }
        if (activity != null){
            return activity.getWindow().getDecorView();
        }
        return null;
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
                Logger.d("onScrollStateChange", state + "  ||  " + scrollPercent);

                if (state == SwipeBackLayout.STATE_IDLE){
                    if (scrollPercent >= 1){
                        activity.finish();
                        activity.overridePendingTransition(0, 0);
                    }else if (scrollPercent <= 0){
                        View view = getPreView();
                        if (view != null){
                            view.setTranslationX(0);
                        }
                        Utils.convertActivityFromTranslucent(activity);
                    }
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                Logger.d("onEdgeTouch", edgeFlag+ "");
                Utils.convertActivityToTranslucent(activity);
            }

            @Override
            public void onScrollOverThreshold() {
//                Logger.d("onScrollOverThreshold", "onScrollOverThreshold");
            }

            @Override
            public void onScroll(int edgeFlag, int left, int top, int dx, int dy, float scrollPercent) {
//                Logger.d("onScroll", left + " " + dx);

                View view = getPreView();
                //mScrollPercent 变化时: 0 -> 0.95 -> 1
                //背景translationX的变化: -0.4 -> 0 -> 0
                if (view != null){
                    float translationX = (float) (0.4 / 0.95 * (scrollPercent - 0.95) * view.getWidth());
                    if (translationX > 0){
                        translationX = 0;
                    }
                    view.setTranslationX(translationX);
                }
            }
        });
    }
}
