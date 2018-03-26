package com.yang.swipeback.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yang on 2018/3/24.
 */

public class ActivitySwipeBackDelegate extends SwipeBackDelegate {

    private AppCompatActivity activity;

    public ActivitySwipeBackDelegate(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View findViewById(int id) {
        View view = activity.findViewById(id);
        if (view == null && mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return view;
    }

    @Override
    public SwipeBackLayout findPreSwipeBackLayout() {
        return null;
    }

    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout = new SwipeBackLayout(activity);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.attachToActivity(activity);
    }



}
