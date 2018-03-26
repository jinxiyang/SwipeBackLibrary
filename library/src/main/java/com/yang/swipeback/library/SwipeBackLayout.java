package com.yang.swipeback.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.widget.ViewDragHelper.STATE_DRAGGING;

/**
 * Created by yangjinxi on 2016/10/13.
 */

public class SwipeBackLayout extends FrameLayout {

    /**
     * 默认滑动销毁距离界限,0.5默认滑到屏幕的一半
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.5f;

    /**
     * 默认滑动销毁速度界限
     */
    private static final float DEFAULT_VELOCITY_THRESHOLD = 500f;

    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;

    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;

    private float mVelocityThreshold = DEFAULT_VELOCITY_THRESHOLD;

    /**
     * 拖动时透明区域的颜色
     */
    private int mScrimColor = DEFAULT_SCRIM_COLOR;

    private ViewDragHelper mViewDragHelper;

    /**
     * 阴影
     */
    private Drawable mShadowLeft;

    //是否处于onlayout状态
    private boolean mInLayout = false;

    /**
     *拖动的界面
     */
    private View mContentView;

    /**
     * 记录左边移动的像素值
     */
    private int mContentLeft;

    /**
     * 当前滑动的宽度占屏幕宽度的比例 [0,1)
     */
    private float mScrollPercent;

    /**
     * 记录透明阴影宽度占屏幕宽度的比例 [0,1]
     */
    private float mScrimOpacity;

    /**
     * 是否可以滑动返回
     */
    private boolean mEnable = true;

    /**
     * 拖动当前布局时,上一个布局是否一起滚动,即背景联动
     */
    private boolean mPreLayoutScrollable = true;

    /**
     * 拖动当前布局时,左侧是否有阴影
     */
    private boolean mHasShadow = true;

    private Rect mTmpRect = new Rect();

    private FragmentActivity mActivity;

    private Fragment mFragment;

    /**
     * The set of listeners to be sent events through.
     */
    private List<SwipeListener> mListeners;


    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化
    private void init(Context context) {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        setHasShadow(true);
        setPreLayoutScrollable(true);
    }

    /**
     * 设置拖动时是否有阴影
     * @param hasShadow
     */
    public void setHasShadow(boolean hasShadow) {
        mHasShadow = hasShadow;
        if (hasShadow){
            mShadowLeft = ContextCompat.getDrawable(getContext(), R.drawable.swipeback_shadow_left);
        }
    }

    /**
     * 设置拖动时背景是否联动
     * @param scrollable
     */
    public void setPreLayoutScrollable(boolean scrollable) {
        this.mPreLayoutScrollable = scrollable;
    }

    /**
     * 设置滚动距离占宽度的百分比触发的临界阈值
     * @param threshold
     */
    public void setScrollThreshold(float threshold) {
        this.mScrollThreshold = threshold;
    }

    /**
     * 设置滚动速度触发的临界阈值
     * @param threshold
     */
    public void setVelocityThreshold(float threshold) {
        this.mVelocityThreshold = threshold;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null) {
            mContentView.layout(mContentLeft, top,
                    mContentLeft + mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        }
        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (mScrimOpacity > 0 && drawContent
                && mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            if (mHasShadow) drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    /**
     * 绘制阴影
     * @param canvas
     * @param child
     */
    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    /**
     * 绘制透明区域,拖动的越远越透明
     * @param canvas
     * @param child
     */
    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 设置是否支持滑动返回
     * @param enable
     */
    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    /**
     * 拖动时移动的内容区
     * @param view
     */
    protected void setContentView(View view) {
        mContentView = view;
    }


    public void attachToActivity(FragmentActivity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    public void attachToFragment(Fragment fragment, View view) {
        addView(view);
        mFragment = fragment;
        setContentView(view);
    }


    /**
     * Add a callback to be invoked when a swipe event is sent to this view.
     *
     * @param listener the swipe listener to attach to this view
     */
    public void addSwipeListener(SwipeListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<>();
        }
        mListeners.add(listener);
    }

    /**
     * Removes a listener from the set of listeners
     *
     * @param listener
     */
    public void removeSwipeListener(SwipeListener listener) {
        if (mListeners == null) {
            return;
        }
        mListeners.remove(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        try {
            return mViewDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable) {
            return false;
        }
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 移动背景
     */
    private void moveBackgroundLayout(SwipeBackLayout layout) {
        //mScrollPercent 变化时: 0 -> 0.95 -> 1
        //背景translationX的变化: -0.4 -> 0 -> 0
        if (layout != null){
            float translationX = (float) (0.4 / 0.95 * (mScrollPercent - 0.95) * layout.getWidth());
            if (translationX > 0){
                translationX = 0;
            }
//            Log.i("======", "moveBackgroundLayout:" + translationX);
            layout.setTranslationX(translationX);
        }
    }

    /**
     * 背景回到原位
     */
    private void recovery(SwipeBackLayout layout) {
        if (layout != null){
            layout.setTranslationX(0);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        private boolean mIsScrollOverValid;

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean dragEnable = mViewDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId);
            if (dragEnable) {
                if (mListeners != null && !mListeners.isEmpty()) {
                    for (SwipeListener listener : mListeners) {
                        listener.onEdgeTouch(ViewDragHelper.EDGE_LEFT);
                    }
                }
                if (mFragment != null) {//当前view在fragment中,将上一个fragment设置为透明可见
                    ISwipeBackFragment iSwipeBackFragment = ((ISwipeBackFragment) mFragment).getPreFragment();
                    Fragment preFragment = (Fragment) iSwipeBackFragment;
                    if (preFragment != null && preFragment.getView() != null && preFragment.getView().getVisibility() != VISIBLE){
                        preFragment.getView().setVisibility(VISIBLE);
                    }
                } else if (mActivity != null) {//当前view在activity中,将此activity转为背景透明,则上一个activity将可见
                    Utils.convertActivityToTranslucent(mActivity);
                }
            }
            return dragEnable;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            if (mFragment != null) {
                return 1;
            } else if (mActivity != null) {
                ISwipeBackActivity swipeBackActivity = (ISwipeBackActivity)mActivity;
                if (swipeBackActivity.swipeBackPriority()){
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(child.getWidth(), Math.max(left, 0));
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (mScrollPercent < mScrollThreshold && !mIsScrollOverValid) {
                mIsScrollOverValid = true;
            }
            if (mListeners != null && !mListeners.isEmpty()
                    && mViewDragHelper.getViewDragState() == STATE_DRAGGING
                    && mScrollPercent >= mScrollThreshold && mIsScrollOverValid) {
                mIsScrollOverValid = false;
                for (SwipeListener listener : mListeners) {
                    listener.onScrollOverThreshold();
                }
            }


            if (changedView == mContentView) {
                //mContentView.getWidth() 实际是屏幕宽度
                mScrollPercent = Math.abs((float) left / mContentView.getWidth());
                mScrimOpacity = 1 - mScrollPercent;
                mContentLeft = left;

//                Log.i("======", "onViewPositionChanged:" + mContentLeft);

                if (mPreLayoutScrollable) moveBackgroundLayout(getPreSwipeBackLayout());

                invalidate();
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();
            int left = 0, top = 0;
//            Log.i("======", "onViewReleased:" + xvel + ":" + mScrollPercent);
            if (xvel > mVelocityThreshold || mScrollPercent > mScrollThreshold) {
                left = childWidth + 1;
                mViewDragHelper.settleCapturedViewAt(left, top);

            } else {
                left = 0;
                mViewDragHelper.settleCapturedViewAt(left, top);
            }
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (mListeners != null && !mListeners.isEmpty()) {
                for (SwipeListener listener : mListeners) {
                    listener.onScrollStateChange(state, mScrollPercent);
                }
            }
//            Log.i("======", "onViewDragStateChanged:" + state + ":" + mScrollPercent);

            if (state == ViewDragHelper.STATE_IDLE) {
                if (mScrollPercent >= 1){
                    if (mFragment != null && !mFragment.isDetached()){//结束当前fragment时,取消动画
                        ISwipeBackFragment iSwipeBackFragment = (ISwipeBackFragment)mFragment;
                        iSwipeBackFragment.getPreFragment().setLockable(true);

                        iSwipeBackFragment.setLockable(true);
                        mFragment.getFragmentManager().popBackStackImmediate();
                        iSwipeBackFragment.setLockable(false);

                        iSwipeBackFragment.getPreFragment().setLockable(false);
                    }else if (mActivity != null && !mActivity.isFinishing()){
                        mActivity.finish();
                    }
                }else{
                    recovery(getPreSwipeBackLayout());
                    if (mActivity != null) Utils.convertActivityFromTranslucent(mActivity);
                }
            }
        }
    }

    private SwipeBackLayout getPreSwipeBackLayout() {
        SwipeBackLayout swipeBackLayout = null;
        if (mFragment != null) {
            ISwipeBackFragment preFragment = ((ISwipeBackFragment) mFragment).getPreFragment();
            swipeBackLayout =  preFragment.getSwipeBackLayout();
        }else {
            ISwipeBackActivity preActivity = ((ISwipeBackActivity) mActivity).getPreActivity();
            swipeBackLayout = preActivity.getSwipeBackLayout();
        }
        return swipeBackLayout;
    }


    /**
     * 拖动监听接口
     */
    public static interface SwipeListener {
        /**
         * Invoke when state change
         *
         * @param state         flag to describe scroll state
         * @param scrollPercent scroll percent of this view
         */
        public void onScrollStateChange(int state, float scrollPercent);

        /**
         * Invoke when edge touched
         *
         * @param edgeFlag edge flag describing the edge being touched
         */
        public void onEdgeTouch(int edgeFlag);

        /**
         * Invoke when scroll percent over the threshold for the first time
         */
        public void onScrollOverThreshold();
    }
}
