package com.yang.swipeback.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

/**
 * Created by yangjinxi on 2016/10/13.
 */

public class SwipeBackFragment extends Fragment{
    boolean mLocking = false;

    public Fragment getPrefragment(){
        Fragment preFragment = null;
//        List<Fragment> fragments = getFragmentManager().getFragments();
//        if (fragments != null && fragments.size() > 1) {
//            int index = fragments.indexOf(this);
//            for (int i = index - 1; i >= 0; i--) {
//                Fragment fragment = fragments.get(i);
//                if (fragment != null && fragment.getView() != null) {
//                    preFragment = fragment;
//                    break;
//                }
//            }
//        }
        return preFragment;
    }
}
