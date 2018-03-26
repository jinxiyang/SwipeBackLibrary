package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yang.swipeback.ActivitySwipeBackDelegate;

/**
 * Created by yang on 2018/3/26.
 */

public class BaseActivity extends LogActivity{

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
}
