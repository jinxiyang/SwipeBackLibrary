package com.yang.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

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

    public ActivitySwipeBackDelegate getActivitySwipeBackDelegate() {
        return swipeBackDelegate;
    }



    public void loadFragment(SwipeBackFragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    public void startFragment(SwipeBackFragment currFragment, SwipeBackFragment nextFragment) {
        if (getContextViewId() == 0){
            throw new RuntimeException("contextViewId cannot be null");
        }
        SwipeBackFragment.TransitionConfig transitionConfig = nextFragment.transitionConfig();
        String tagName = nextFragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .add(getContextViewId(), nextFragment, nextFragment.getClass().getSimpleName())
                .hide(currFragment)
                .addToBackStack(tagName)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //当添加fragment时，子类必须重写该方法
    public int getContextViewId() {
        return 0;
    }
}
