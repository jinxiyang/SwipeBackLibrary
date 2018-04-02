package com.yang.swipeback;

import android.util.Log;

/**
 * Created by yang on 2018/3/24.
 */

public class Logger {

    public static void d(String tag, String info){
        if (SwipeBackManager.debug()){
            Log.d(tag, info);
        }
    }

    public static void d(String info){
        d(SwipeBackManager.TAG, info);
    }

    public static void i(String tag, String info){
        if (SwipeBackManager.debug()){
            Log.i(tag, info);
        }
    }

    public static void i(String info){
        d(SwipeBackManager.TAG, info);
    }

    public static void e(String tag, String info){
        if (SwipeBackManager.debug()){
            Log.e(tag, info);
        }
    }

    public static void e(String info){
        d(SwipeBackManager.TAG, info);
    }
}
