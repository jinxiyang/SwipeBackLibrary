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

    /**
     * 标志是否处于滑动返回状态，处于一下区间内：
     *
     * 1、手指拖动左边缘，滑动开始，置为true----> 界面移动 ----->  超过阈值 ----> 界面移动  ----->  手指松开   ----->  界面自动移动到右侧，上层界面销毁，下层显示  ----->  滑动结束，置为false
     * 2、手指拖动左边缘，滑动开始，置为true----> 界面移动 ----->  没超过阈值，手指松开  ----->  界面自动移动到左侧侧，状态还原，底层界面不可见  ----->  滑动结束，置为false
     *
     * 注：
     * a、当底层activity可见时，底层activity会回调onResume, 这时可SwipeBackManager.isSwippingBack()判断是否处于滑动返回状态，避免界面未完全展示出来时的数据更新、loading框等
     * b、对于1情况结束结束时，若底层activity实现了OnSwipeBackCompletedListener，会回调OnSwipeBackCompletedListener.onBack()，可做界面数据更新
     * c、fragment的可见是调用fragment.getView().setVisibilty(VISIBLE), 退出栈是popBackStack（），未在滑动返回状态下引起生命周期调用，不用实现OnSwipeBackCompletedListener
     */
    private static boolean swippingBack = false;

    /**
     * 滑动返回时背景联动的动画
     */
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

    /**
     * 是否处于滑动返回状态
     * @return
     */
    public static boolean isSwippingBack() {
        return swippingBack;
    }

    public static void setSwippingBack(boolean swippingBack) {
        SwipeBackManager.swippingBack = swippingBack;
    }

    /**
     * 滑动返回时背景联动的动画
     *
     * @param previousViewAnim
     */
    public static void setPreviousViewAnim(IPreviousViewAnim previousViewAnim) {
        SwipeBackManager.previousViewAnim = previousViewAnim;
    }

    public static IPreviousViewAnim getPreviousViewAnim() {
        return previousViewAnim;
    }
}
