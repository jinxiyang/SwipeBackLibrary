package com.yang.swipebacklibrary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.swipeback.library.SwipeBackFragmentImpl;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends SwipeBackFragmentImpl {


    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ThirdActivity)getActivity()).addNewFragment(FragmentA.this, new FragmentB());
            }
        });
        setSwipeBackEnable(false);
        return attachToSwipeBack(view);
    }

}
