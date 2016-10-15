package com.yang.swipebacklibrary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.swipeback.library.SwipeBackFragmentImpl;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentC extends SwipeBackFragmentImpl {


    public FragmentC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ThirdActivity.class));
            }
        });
        return attachToSwipeBack(view);
    }

}
