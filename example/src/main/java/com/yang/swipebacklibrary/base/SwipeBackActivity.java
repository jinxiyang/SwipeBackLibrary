package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.yang.swipeback.util.ActivityStack;
import com.yang.swipeback.delegate.ActivitySwipeBackDelegate;

/**
 * Created by yang on 2018/3/26.
 */

public class SwipeBackActivity extends AppCompatActivity{

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

    @Override
    protected void onDestroy() {
        swipeBackDelegate.onDestroy();
        super.onDestroy();
    }

    public ActivitySwipeBackDelegate getActivitySwipeBackDelegate() {
        return swipeBackDelegate;
    }

    public void setSwipeBackEnable(boolean enable) {
        if (swipeBackDelegate != null){
            swipeBackDelegate.setSwipeBackEnable(enable);
        }
    }



    public void loadFragment(Fragment fragment){
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListenerDefault);
        //第一个fragment没有入栈，当只有一个fragment时按返回键不会显示空白，直接activity.finishi()
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void startFragment(Fragment currFragment, Fragment nextFragment) {
        if (getContextViewId() == 0){
            throw new RuntimeException("contextViewId cannot be null");
        }
        String tagName = nextFragment.getClass().getSimpleName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (nextFragment instanceof SwipeBackFragment){
            SwipeBackFragment.TransitionConfig transitionConfig = ((SwipeBackFragment)nextFragment).transitionConfig();
            transaction.setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout);
        }
        transaction.add(getContextViewId(), nextFragment, tagName)
                .hide(currFragment)
                .addToBackStack(tagName)
                .commit();
    }

    //当添加fragment时，子类必须重写该方法
    public int getContextViewId() {
        return 0;
    }


    private FragmentManager.OnBackStackChangedListener backStackChangedListenerDefault = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {//最多有一个通过loadFragment加载的fragment, 可能要禁止或者放开
                setSwipeBackEnable(ActivityStack.getInstance().count() > 0);
            } else {//返回栈中不只一个fragment，禁止activity的滑动返回功能
                setSwipeBackEnable(false);
            }
        }
    };
}
