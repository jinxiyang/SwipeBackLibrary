package com.yang.swipebacklibrary;

import android.app.Application;

import com.yang.swipeback.library.newversion.ActivityStack;
import com.yang.swipeback.library.newversion.SwipeBackManager;

/**
 * Created by yang on 2016/10/14.
 */

public class AppApplication extends Application {
    public static AppApplication instance;

    //这是库中提供的activity栈,放到Application中,不会被GC
    public ActivityStack stack;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SwipeBackManager.init(this);
        SwipeBackManager.setDebug(true);
    }


    public static AppApplication getInstance() {
        return instance;
    }

    public ActivityStack getStack() {
        return stack;
    }
}
