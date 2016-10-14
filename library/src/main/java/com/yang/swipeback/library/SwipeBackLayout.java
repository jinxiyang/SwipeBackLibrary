package com.yang.swipeback.library;

import android.animation.ObjectAnimator;
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

/**
 * Created by yangjinxi on 2016/10/13.
 */

public class SwipeBackLayout extends FrameLayout {
    /**
     * A view is not currently being dragged or animating as a result of a
     * fling/snap.
     */
    public static final int STATE_IDLE = ViewDragHelper.STATE_IDLE;

    /**
     * A view is currently being dragged. The position is currently changing as
     * a result of user input or simulated user input.
     */
    public static final int STATE_DRAGGING = ViewDragHelper.STATE_DRAGGING;

    /**
     * A view is currently settling into place as a result of a fling or
     * predefined non-interactive motion.
     */
    public static final int STATE_SETTLING = ViewDragHelper.STATE_SETTLING;


    /**
     * 滑动销毁距离界限
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.5f;

    /**
     * 滑动销毁速度界限
     */
    private static final float DEFAULT_VELOCITY_THRESHOLD = 500f;

    private static final int FULL_ALPHA = 255;

    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;

    private int mScrimColor = DEFAULT_SCRIM_COLOR;

    private ViewDragHelper mViewDragHelper;

    private Drawable mShadowLeft;

    private boolean mInLayout;

    private View mContentView;

    /**
     * 记录左边移动的像素值
     */
    private int mContentLeft;

    /**
     * 当前滑动范围 [0,1)
     */
    private float mScrollPercent;

    /**
     * 记录阴影透明比例 [0,1]
     */
    private float mScrimOpacity;


    private boolean mEnable = true;

    private Rect mTmpRect = new Rect();

    private FragmentActivity mActivity;

    private Fragment mFragment;


    /**
     * 判断背景Activity是否启动进入动画
     */
    private boolean enterAnimRunning = false;

    /**
     * 进入动画(只在释放手指时使用)
     */
    private ObjectAnimator mEnterAnim;

    /**
     * The set of listeners to be sent events through.
     */
    private List<OnSwipeListener> mListeners;

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

    private void init(Context context) {
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mShadowLeft = ContextCompat.getDrawable(context, R.drawable.swipeback_shadow_left);
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
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

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

    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    private void setContentView(View view) {
        mContentView = view;
    }

    public View getContentView() {
        return mContentView;
    }


    public void attachToActivity(SwipeBackActivityImpl activity) {
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
    public void addSwipeListener(OnSwipeListener listener) {
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
    public void removeSwipeListener(OnSwipeListener listener) {
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
     * 启动进入动画
     * 此方法用于将背景view 完全移到屏幕中
     */
    public void startEnterAnim() {
        if (mContentView != null) {
            ObjectAnimator anim = ObjectAnimator
                    .ofFloat(mContentView, "TranslationX", mContentView.getTranslationX(), 0f);

            anim.setDuration((long) (125 * mScrimOpacity));

            mEnterAnim = anim;
            mEnterAnim.start();
        }
    }

    /**
     * 回复界面的平移到初始位置
     */
    public void recovery() {
        if (mEnterAnim != null && mEnterAnim.isRunning()) {
            mEnterAnim.end();
        } else {
            mContentView.setTranslationX(0);
        }
    }

    /**
     * 背景开始进入动画
     */
    private void startAnimOfBackgroundLayout(SwipeBackLayout layout) {
        if (layout != null){
            enterAnimRunning = true;
            layout.startEnterAnim();
        }
    }

    /**
     * 移动背景
     */
    private void moveBackgroundLayout(SwipeBackLayout layout) {
        if (layout != null){
            int width = layout.getWidth();
            layout.setTranslationX(-width * 0.3f * Math.max(0f, mScrimOpacity - 0.15f));
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean dragEnable = mViewDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, pointerId);
            if (dragEnable) {
                if (mListeners != null && !mListeners.isEmpty()) {
                    for (OnSwipeListener listener : mListeners) {
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
            if (changedView == mContentView) {
                //mContentView.getWidth() 实际是屏幕宽度
                //mShadowLeft.getIntrinsicWidth() 阴影的宽度
                //mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()  需要把阴影也拖拽出屏幕,所以总宽度是阴影的宽度加上屏幕的宽度
                mScrollPercent = Math.abs((float) left
                        / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));

                mContentLeft = left;

                //未执行动画就平移
                if (!enterAnimRunning) {
                    moveBackgroundLayout(getPreSwipeBackLayout());
                }

                invalidate();

                if (mScrollPercent >= 1){
                    if (mFragment != null && !mFragment.isDetached()){
                        ISwipeBackFragment iSwipeBackFragment = (ISwipeBackFragment)mFragment;
                        iSwipeBackFragment.setLockable(true);
                        mFragment.getFragmentManager().popBackStackImmediate();
                        iSwipeBackFragment.setLockable(false);

                        ((ISwipeBackFragment)(iSwipeBackFragment.getPreFragment())).setLockable(false);
                    }else if (mActivity != null && !mActivity.isFinishing()){
                        mActivity.finish();
                    }

                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();
            int left = 0, top = 0;
            if (xvel > DEFAULT_VELOCITY_THRESHOLD || mScrollPercent > DEFAULT_SCROLL_THRESHOLD) {
                left = childWidth + mShadowLeft.getIntrinsicWidth();
                mViewDragHelper.settleCapturedViewAt(left, top);

                if (mScrimOpacity < 0.85f) {
                    startAnimOfBackgroundLayout(getPreSwipeBackLayout());
                }

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
                for (OnSwipeListener listener : mListeners) {
                    listener.onDragStateChange(state);
                }
            }

            if (state == ViewDragHelper.STATE_IDLE && mScrollPercent < 1f) {
                Utils.convertActivotyFromTranslucent(mActivity);
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


    public interface OnSwipeListener {
        /**
         * Invoke when state change
         *
         * @param state flag to describe scroll state
         * @see #STATE_IDLE
         * @see #STATE_DRAGGING
         * @see #STATE_SETTLING
         */
        void onDragStateChange(int state);

        /**
         * Invoke when edge touched
         *
         * @param oritentationEdgeFlag edge flag describing the edge being touched
         */
        void onEdgeTouch(int oritentationEdgeFlag);

        /**
         * Invoke when scroll percent over the threshold for the first time
         *
         * @param scrollPercent scroll percent of this view
         */
        void onDragScrolled(float scrollPercent);
    }
}
