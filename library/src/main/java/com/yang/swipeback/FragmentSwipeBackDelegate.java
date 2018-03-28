package com.yang.swipeback;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class FragmentSwipeBackDelegate extends SwipeBackDelegate {

    private Fragment fragment;
    private View preView;

    private OnFragmentPopBackStackListener onFragmentPopBackStackListener;

    public FragmentSwipeBackDelegate(Fragment fragment) {
        this.fragment = fragment;
        onFragmentPopBackStackListener = new PopBackStackDefault();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @Override
    public View getPreView() {
        if (preView == null) {
            FragmentManager fm = fragment.getFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            if (fragments != null && fragments.size() > 1) {
                int index = fragments.indexOf(fragment);
                for (int i = index - 1; i >= 0; i--) {
                    Fragment f = fragments.get(i);
                    if (f != null && f.getView() != null) {
                        preView = f.getView();
                    }
                }
            }
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
                Logger.d("onScrollStateChange", state + "  ||  " + scrollPercent);
                if (state == SwipeBackLayout.STATE_IDLE){
                    if (scrollPercent >= 1){
                        onFragmentPopBackStackListener.popBackStack();
                    }else if (scrollPercent <= 0){
                        View view = getPreView();
                        if (view != null){
                            view.setTranslationX(0);
                            if (view.getVisibility() != View.INVISIBLE){
                                view.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                Logger.d("onEdgeTouch", edgeFlag+ "");
                View view = getPreView();
                if (view != null){
                    if (view.getVisibility() != View.VISIBLE){
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrollOverThreshold() {
                Logger.d("onScrollOverThreshold", "onScrollOverThreshold");
            }

            @Override
            public void onScroll(int edgeFlag, int left, int top, int dx, int dy, float scrollPercent) {
                Logger.d("onScroll", left + " " + dx);

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
        return mSwipeBackLayout;
    }

    public interface OnFragmentPopBackStackListener{
        void popBackStack();
    }

    public void setOnFragmentPopBackStackListener(@NonNull OnFragmentPopBackStackListener onFragmentPopBackStackListener) {
        this.onFragmentPopBackStackListener = onFragmentPopBackStackListener;
    }


    public class PopBackStackDefault implements OnFragmentPopBackStackListener{

        @Override
        public void popBackStack() {
            fragment.getFragmentManager().popBackStackImmediate();
        }
    }
}
