package com.yang.swipeback;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by yang on 2018/3/24.
 */

public class ActivitySwipeBackDelegate extends SwipeBackDelegate {

    private Activity activity;
    private View preView = null;

    public ActivitySwipeBackDelegate(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View getPreView() {
        if (preView == null) {
            Activity backActivity = ActivityStack.getInstance().getBackActivity();
            if (backActivity != null){
                preView = backActivity.getWindow().getDecorView();
            }
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
                    SwipeBackManager.setSwippingBack(false);
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                SwipeBackManager.setSwippingBack(true);
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


    private FragmentManager.OnBackStackChangedListener backStackChangedListenerDefault = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (((AppCompatActivity)ActivitySwipeBackDelegate.this.activity).getSupportFragmentManager().getBackStackEntryCount() == 0) {//最多有一个通过loadFragment加载的fragment, 可能要禁止或者放开
                setSwipeBackEnable(ActivityStack.getInstance().count() > 0);
            } else {//返回栈中不只一个fragment，禁止activity的滑动返回功能
                setSwipeBackEnable(false);
            }
        }
    };


    public FragmentManager.OnBackStackChangedListener getBackStackChangedListenerDefault() {
        return backStackChangedListenerDefault;
    }
}
