package com.yang.swipeback.library;

/**
 * Created by yang on 2016/10/13.
 */

public interface ISwipeBackFragment {

    /**
     * 获取此Fragment的SwipeBackLayout
     * @return
     */
    SwipeBackLayout getSwipeBackLayout();

    /**
     * 获取上一个fragment
     * @return
     */
    ISwipeBackFragment getPreFragment();

    /**
     *生成动画时会判是否锁住生成动画
     * @return
     */
    boolean isLocking();

    /**
     *设置是否锁住生成动画
     *当fragment画出屏幕时,fragment出栈前,设置为true,fragment出栈时,就不会有动画效果。出栈之后,上一个fragment的要设置为false
     * @param lockable
     */
    void setLockable(boolean lockable);

    /**
     * 设置此SwipeBackLayout是否支持滑动返回,第一个fragment时,设置为false
     * @param enable
     */
    void setSwipeBackEnable(boolean enable);
}
