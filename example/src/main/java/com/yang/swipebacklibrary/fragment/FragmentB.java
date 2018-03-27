package com.yang.swipebacklibrary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.yang.swipebacklibrary.R;
import com.yang.swipebacklibrary.activity.ThirdActivity;
import com.yang.swipebacklibrary.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends BaseFragment {


    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_b, null, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ThirdActivity)getActivity()).addNewFragment(FragmentB.this, new FragmentC());
            }
        });
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("fragment b");
        return view;
    }
}
