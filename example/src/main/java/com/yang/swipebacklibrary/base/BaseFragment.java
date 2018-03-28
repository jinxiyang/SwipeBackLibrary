package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.yang.swipeback.ActivityStack;
import com.yang.swipeback.FragmentSwipeBackDelegate;
import com.yang.swipebacklibrary.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Author: 杨进玺
 * Time: 2018/3/27  13:43
 */

public abstract class BaseFragment extends Fragment {

    private FragmentSwipeBackDelegate swipeBackDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackDelegate = new FragmentSwipeBackDelegate(this);
        swipeBackDelegate.setOnFragmentPopBackStackListener(new FragmentSwipeBackDelegate.OnFragmentPopBackStackListener() {
            @Override
            public void popBackStack() {
                //当fragment退出返回栈时，注意放开activity的滑动返回功能
                if (ActivityStack.getInstance().count() > 1
                        && getFragmentManager().getBackStackEntryCount() <= 2
                        && getActivity() instanceof BaseActivity) {
                    ((BaseActivity) getActivity()).getActivitySwipeBackDelegate().setSwipeBackEnable(true);
                }
                getFragmentManager().popBackStackImmediate();
            }
        });

        //添加新的fragment时注意禁止activity的滑动返回功能
        if (getActivity() instanceof BaseActivity
                && getFragmentManager().getBackStackEntryCount() > 1){
            ((BaseActivity) getActivity()).getActivitySwipeBackDelegate().setSwipeBackEnable(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater, savedInstanceState);
        return swipeBackDelegate.onCreateView(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeBackDelegate.setSwipeBackEnable(true);
    }

    public abstract View onCreateView(LayoutInflater inflater, @Nullable Bundle savedInstanceState);


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public void startFragment(BaseFragment currFragment, BaseFragment nextFragment){
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).startFragment(currFragment, nextFragment);
        }else {
            Log.d(getClass().getSimpleName(), "startFragment:   getActivity() is not BaseActivity");
        }
    }

    /**
     * 界面跳转动画
     */
    public static final class TransitionConfig {
        public final int enter;
        public final int exit;
        public final int popenter;
        public final int popout;

        public TransitionConfig(int enter, int popout) {
            this(enter, 0, 0, popout);
        }

        public TransitionConfig(int enter, int exit, int popenter, int popout) {
            this.enter = enter;
            this.exit = exit;
            this.popenter = popenter;
            this.popout = popout;
        }
    }

    public static final TransitionConfig SLIDE_TRANSITION_CONFIG = new TransitionConfig(
            R.anim.slide_in_right, R.anim.slide_out_left,
            R.anim.slide_in_left, R.anim.slide_out_right
    );

    public TransitionConfig transitionConfig(){
        return SLIDE_TRANSITION_CONFIG;
    }
}
