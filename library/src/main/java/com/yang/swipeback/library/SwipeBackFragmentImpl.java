package com.yang.swipeback.library;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/**
 * Created by yangjinxi on 2016/10/13.
 */

public class SwipeBackFragmentImpl extends Fragment implements ISwipeBackFragment{

    private SwipeBackLayout mSwipeBackLayout;
    private Animation mNoAnim;
    boolean mLocking = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNoAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim);
        onFragmentCreate();
    }

    private void onFragmentCreate() {
        mSwipeBackLayout = new SwipeBackLayout(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    protected View attachToSwipeBack(View view) {
        mSwipeBackLayout.attachToFragment(this, view);
        return mSwipeBackLayout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Fragment preFragment = getPreFragment();
            if (preFragment != null && preFragment.getView() != null){
                preFragment.getView().setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        initFragmentBackground(view);
        if (view != null) {
            view.setClickable(true);
        }
    }

    private void initFragmentBackground(View view) {
        if (view instanceof SwipeBackLayout) {
            View childView = ((SwipeBackLayout) view).getChildAt(0);
            setBackground(childView);
        } else {
            setBackground(view);
        }
    }

    private void setBackground(View view) {
        if (view != null && view.getBackground() == null) {
                int background = getWindowBackground();
                view.setBackgroundResource(background);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (mLocking) {
            return mNoAnim;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    protected int getWindowBackground() {
        TypedArray a = getActivity().getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();
        return background;
    }



    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @Override
    public Fragment getPreFragment() {
        Fragment mPreFragment = null;
        List<Fragment> fragments = getFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 1) {
            int index = fragments.indexOf(this);
            for (int i = index - 1; i >= 0; i--) {
                Fragment fragment = fragments.get(i);
                if (fragment != null && fragment.getView() != null) {
                    mPreFragment = fragment;
                    break;
                }
            }
        }
        return mPreFragment;
    }

    @Override
    public boolean isLocking() {
        return mLocking;
    }

    @Override
    public void setLockable(boolean lockable) {
        mLocking = lockable;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }
}
