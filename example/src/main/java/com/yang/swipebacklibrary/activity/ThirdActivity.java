package com.yang.swipebacklibrary.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yang.swipebacklibrary.R;
import com.yang.swipebacklibrary.base.BaseActivity;
import com.yang.swipebacklibrary.fragment.FragmentA;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        loadNewFragment(new FragmentA());
    }

    public void loadNewFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, fragment, tag)
                .commit();
    }

    public void addNewFragment(Fragment currFragment, Fragment nextFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit, R.anim.activity_or_fragment_pop_enter, R.anim.activity_or_fragment_pop_exit)
                .add(R.id.contentFrame, nextFragment, nextFragment.getClass().getSimpleName())
                .hide(currFragment)
                .addToBackStack(currFragment.getClass().getSimpleName())
                .commit();
    }
}
