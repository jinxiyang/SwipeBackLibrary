package com.yang.swipebacklibrary;

import android.app.Application;

import com.yang.swipeback.SwipeBackManager;

public class AppApplication extends Application {
    public static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SwipeBackManager.init(this);
    }


    public static AppApplication getInstance() {
        return instance;
    }
}
