package com.yang.swipeback.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    //默认不锁住动画生成
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
    }

    protected View attachToSwipeBack(View view) {
        mSwipeBackLayout.attachToFragment(this, view);
        return mSwipeBackLayout;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            Fragment preFragment = (Fragment) getPreFragment();
            if (preFragment != null && preFragment.getView() != null){
                preFragment.getView().setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view != null) {
            view.setClickable(true);
        }
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (isLocking()) {
            return mNoAnim;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @Override
    public ISwipeBackFragment getPreFragment() {
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
        return (ISwipeBackFragment) mPreFragment;
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
