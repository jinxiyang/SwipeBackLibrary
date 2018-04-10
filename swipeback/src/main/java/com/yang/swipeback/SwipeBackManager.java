package com.yang.swipeback;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yang.swipeback.anim.IPreviousViewAnim;
import com.yang.swipeback.anim.PreviousViewAnimDefault;
import com.yang.swipeback.util.ActivityStack;

/**
 *
 * 滑动返回管理者
 *
 * Created by yang on 2018/3/24.
 */
public class SwipeBackManager {
    public static final String TAG = "swipe back";

    private static boolean debug = true;
    private static boolean swippingBack = false;

    private static IPreviousViewAnim previousViewAnim = new PreviousViewAnimDefault();

    public static void init(Context context){
        if (context == null){
            throw new RuntimeException("context is null");
        }
        init(context, true);
    }


    public static void init(@NonNull Context context, boolean useDefaultActivityStack){
        //是否使用默认的activity栈
        if (useDefaultActivityStack){
            ((Application)context.getApplicationContext()).registerActivityLifecycleCallbacks(ActivityStack.getInstance());
        }
    }


    public static boolean debug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        SwipeBackManager.debug = debug;
    }

    public static boolean isSwippingBack() {
        return swippingBack;
    }

    public static void setSwippingBack(boolean swippingBack) {
        SwipeBackManager.swippingBack = swippingBack;
    }

    public static void setPreviousViewAnim(IPreviousViewAnim previousViewAnim) {
        SwipeBackManager.previousViewAnim = previousViewAnim;
    }

    public static IPreviousViewAnim getPreviousViewAnim() {
        return previousViewAnim;
    }
}
