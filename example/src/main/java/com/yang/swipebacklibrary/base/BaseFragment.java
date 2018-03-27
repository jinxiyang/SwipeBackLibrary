package com.yang.swipebacklibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.swipeback.FragmentSwipeBackDelegate;

/**
 * Author: 杨进玺
 * Time: 2018/3/27  13:43
 */

public abstract class BaseFragment extends LogFragment {

    private FragmentSwipeBackDelegate swipeBackDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeBackDelegate = new FragmentSwipeBackDelegate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = onCreateView(inflater, savedInstanceState);
        return swipeBackDelegate.onCreateView(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeBackDelegate.setSwipeBackEnable(true);
    }

    public abstract View onCreateView(LayoutInflater inflater, @Nullable Bundle savedInstanceState);

}
