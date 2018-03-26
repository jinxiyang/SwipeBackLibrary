//package com.yang.swipebacklibrary.fragment;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.yang.swipeback.library.SwipeBackFragmentImpl;
//import com.yang.swipebacklibrary.R;
//import com.yang.swipebacklibrary.activity.ThirdActivity;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class FragmentB extends SwipeBackFragmentImpl {
//
//
//    public FragmentB() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_b, container, false);
//        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((ThirdActivity)getActivity()).addNewFragment(FragmentB.this, new FragmentC());
//            }
//        });
//        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
//        toolbar.setTitle("fragment b");
//        return attachToSwipeBack(view);
//    }
//
//}
