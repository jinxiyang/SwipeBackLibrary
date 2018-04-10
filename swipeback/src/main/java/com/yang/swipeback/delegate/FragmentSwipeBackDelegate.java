package com.yang.swipeback.delegate;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yang.swipeback.R;
import com.yang.swipeback.SwipeBackManager;
import com.yang.swipeback.layout.SwipeBackLayout;
import com.yang.swipeback.listener.OnSwipeBackListenerFragmentDefault;


public class FragmentSwipeBackDelegate extends SwipeBackDelegate {

    private Fragment fragment;
    private View preView;

    public FragmentSwipeBackDelegate(Fragment fragment) {
        this.fragment = fragment;
        setOnSwipeBackListener(new OnSwipeBackListenerFragmentDefault());
    }

    @Override
    public View getPreView() {
        if (preView == null) {
            preView = onSwipeBackListener.findPreviousView();
        }
        return preView;
    }

    public View onCreateView(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout = new SwipeBackLayout(view.getContext());
        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.addView(view);
        mSwipeBackLayout.setContentView(view);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEnableGesture(true);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (state == SwipeBackLayout.STATE_IDLE){
                    if (scrollPercent >= 1){
                        onSwipeBackListener.finish();
                    }else if (scrollPercent <= 0){
                        View view = getPreView();
                        onSwipeBackListener.onBackToOriginPosition();
                        if (view != null && SwipeBackManager.getPreviousViewAnim() != null){
                            SwipeBackManager.getPreviousViewAnim().backToOriginalPositionWithoutAnim(view);
                        }
                    }
                    SwipeBackManager.setSwippingBack(false);
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
                if (getPreView() != null && SwipeBackManager.getPreviousViewAnim() != null){
                    SwipeBackManager.getPreviousViewAnim().onScroll(getPreView(), left, top, dx, dy, scrollPercent);
                }
            }
        });
        return mSwipeBackLayout;
    }


    public Animation onCreateAnimation() {
        if (SwipeBackManager.isSwippingBack() && fragment.getActivity() != null){
            return AnimationUtils.loadAnimation(fragment.getActivity(), R.anim.swipeback_no_anim);
        }
        return null;
    }


    public void onDestroy() {
        fragment = null;
        preView = null;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
