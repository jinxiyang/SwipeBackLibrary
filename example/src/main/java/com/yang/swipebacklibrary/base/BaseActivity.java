package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yang.swipeback.ActivitySwipeBackDelegate;

/**
 * Created by yang on 2018/3/26.
 */

public class BaseActivity extends AppCompatActivity{

    private ActivitySwipeBackDelegate swipeBackDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackDelegate = new ActivitySwipeBackDelegate(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        swipeBackDelegate.onPostCreate();
    }

    public ActivitySwipeBackDelegate getActivitySwipeBackDelegate() {
        return swipeBackDelegate;
    }



    public void loadFragment(BaseFragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void startFragment(BaseFragment currFragment, BaseFragment nextFragment) {
        if (getContextViewId() == 0){
            throw new RuntimeException("contextViewId cannot be null");
        }
        BaseFragment.TransitionConfig transitionConfig = nextFragment.transitionConfig();
        String tagName = nextFragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .add(getContextViewId(), nextFragment, nextFragment.getClass().getSimpleName())
                .hide(currFragment)
                .addToBackStack(tagName)
                .commit();
    }


    /**
     * 获取当前的 Fragment。
     */
    public BaseFragment getCurrentFragment() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
    }

    //当添加fragment时，子类必须重写该方法
    public int getContextViewId() {
        return 0;
    }
}
