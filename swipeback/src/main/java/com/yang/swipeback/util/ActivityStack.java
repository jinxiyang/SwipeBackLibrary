package com.yang.swipeback.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.yang.swipeback.SwipeBackManager;

import java.util.Vector;


/**
 * 默认的activity栈，用于查找底层activity
 */
public class ActivityStack implements Application.ActivityLifecycleCallbacks {
    private static ActivityStack instance;

    private Vector<Activity> activities = new Vector<>();

    public static ActivityStack getInstance() {
        if (instance == null){
            instance = new ActivityStack();
        }
        return instance;
    }

    private ActivityStack() {

    }

    public int count(){
        return activities.size();
    }


    /**
     * 获取底层界面activity
     * @return
     */
    public Activity getPreviousActivity() {
        logActivityStack();
        if (activities.size() > 1) {
            return activities.get(activities.size() - 2);
        } else {
            return null;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        int index = activities.indexOf(activity);
        if (index == -1) {
            activities.add(activity);
        }
        logActivityStack();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        int index = activities.lastIndexOf(activity);
        if (index > -1){
            activities.remove(activity);
        }
        logActivityStack();
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }


    private void logActivityStack(){
        String log = "-------------------ActivityStack-----------------------";
        for (int i = activities.size() - 1; i >= 0; i--){
            Activity activity = activities.get(i);
            log += "\n" + activity.toString();
        }
        log +=       "-------------------------------------------------------";
        Log.i(SwipeBackManager.TAG, log);
    }
}
