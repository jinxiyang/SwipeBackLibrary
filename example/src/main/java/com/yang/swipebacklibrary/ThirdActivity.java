package com.yang.swipebacklibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yang.swipeback.library.ISwipeBackActivity;
import com.yang.swipeback.library.SwipeBackActivityImpl;

public class ThirdActivity extends SwipeBackActivityImpl {

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
                .setCustomAnimations(R.anim.swipeback_activity_open_enter, R.anim.swipeback_activity_open_exit, R.anim.swipeback_activity_close_enter, R.anim.swipeback_activity_close_exit)
                .add(R.id.contentFrame, nextFragment, nextFragment.getClass().getSimpleName())
                .hide(currFragment)
                .addToBackStack(currFragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public ISwipeBackActivity getPreActivity() {
        return (ISwipeBackActivity) AppApplication.getInstance().getStack().getBackActivity();
    }

}
