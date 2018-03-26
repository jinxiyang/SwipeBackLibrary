package com.yang.swipeback.library;

import android.app.Application;
import android.content.Context;

/**
 *
 * 滑动返回管理者
 *
 * Created by yang on 2018/3/24.
 */
public class SwipeBackManager {
    public static final String TAG = "swipe back";

    private static boolean debug = true;

    public static void init(Context context){
        if (context == null){
            throw new RuntimeException("context is null");
        }
        ((Application)context).registerActivityLifecycleCallbacks(ActivityStack.getInstance());
    }


    public static boolean debug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        SwipeBackManager.debug = debug;
    }
}
