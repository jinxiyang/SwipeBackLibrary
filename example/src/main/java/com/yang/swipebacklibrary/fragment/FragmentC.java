package com.yang.swipebacklibrary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.yang.swipebacklibrary.R;
import com.yang.swipebacklibrary.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentC extends BaseFragment {


    public FragmentC() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, null, false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("fragment c");
        return view;
    }

}
