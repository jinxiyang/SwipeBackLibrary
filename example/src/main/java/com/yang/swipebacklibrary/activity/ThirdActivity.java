package com.yang.swipebacklibrary.activity;

import android.os.Bundle;

import com.yang.swipebacklibrary.R;
import com.yang.swipebacklibrary.base.BaseActivity;
import com.yang.swipebacklibrary.fragment.FragmentA;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        loadFragment(new FragmentA());
    }

    @Override
    public int getContextViewId() {
        return R.id.contentFrame;
    }
}
