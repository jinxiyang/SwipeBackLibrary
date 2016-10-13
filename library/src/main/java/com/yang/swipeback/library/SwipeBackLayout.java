package com.yang.swipeback.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by yangjinxi on 2016/10/13.
 */

public class SwipeBackLayout extends FrameLayout{

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
