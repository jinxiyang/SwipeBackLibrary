package com.yang.swipeback.library;

import com.yang.swipeback.SwipeBackDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2018/3/24.
 */

public class SwipeBackStack {

    public static List<SwipeBackDelegate> swipeBackList = new ArrayList<>();

    public static void addSwipeBackDelegate(SwipeBackDelegate swipeBackDelegate){
        if (swipeBackDelegate != null){
            swipeBackList.add(swipeBackDelegate);
        }
    }

    public static void removeSwipeBackDelegate(SwipeBackDelegate swipeBackDelegate){
        if (swipeBackDelegate != null){
            int index = swipeBackList.lastIndexOf(swipeBackDelegate);
            if (index > -1){
                swipeBackList.remove(index);
            }
        }
    }


}
