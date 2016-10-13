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
public class Fragment_2 extends SwipeBackFragmentImpl {


    public Fragment_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ThirdActivity)getActivity()).addNewFragment(Fragment_2.this, new Fragment_3());
            }
        });
        return attachToSwipeBack(view);
    }

}
