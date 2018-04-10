package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.yang.swipeback.delegate.FragmentSwipeBackDelegate;
import com.yang.swipebacklibrary.R;

/**
 * Author: 杨进玺
 * Time: 2018/3/27  13:43
 */

public abstract class SwipeBackFragment extends Fragment {

    private FragmentSwipeBackDelegate swipeBackDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackDelegate = new FragmentSwipeBackDelegate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater, savedInstanceState);
        return swipeBackDelegate.onCreateView(view);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return swipeBackDelegate.onCreateAnimation();
    }

    @Override
    public void onDestroy() {
        swipeBackDelegate.onDestroy();
        super.onDestroy();
    }

    public abstract View onCreateView(LayoutInflater inflater, @Nullable Bundle savedInstanceState);





    public void startFragment(Fragment currFragment, Fragment nextFragment){
        if (getActivity() instanceof SwipeBackActivity) {
            ((SwipeBackActivity) getActivity()).startFragment(currFragment, nextFragment);
        }else {
            Log.d(getClass().getSimpleName(), "startFragment:   getActivity() is not SwipeBackActivity");
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
            R.anim.swipeback_slide_in_right, R.anim.swipeback_slide_out_left,
            R.anim.swipeback_slide_in_left, R.anim.swipeback_slide_out_right
    );

    public TransitionConfig transitionConfig(){
        return SLIDE_TRANSITION_CONFIG;
    }
}
