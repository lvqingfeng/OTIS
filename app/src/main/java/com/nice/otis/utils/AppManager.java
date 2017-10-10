package com.nice.otis.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.util.SparseArray;

import com.nice.otis.MainActivity;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 作者:吕清锋
 * 时间：2017/7/7
 * 版本：V1.0.0
 * 类描述：
 */

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    //保存所有Activity
    private LinkedList<Activity> activities = new LinkedList<Activity>();
    //保存所有Service
    private LinkedList<Service> services = new LinkedList<Service>();
    private AppManager() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
    }

    /**
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void finishOtherActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (!activity.getClass().equals(MainActivity.class)) {
                finishActivity(activity);
            }
        }
    }

    /**
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     */
    public void finishAllActivity() {
        for (Activity anActivityStack : activityStack) {
            if (null != anActivityStack) {
                anActivityStack.finish();
            }
        }
        activityStack.clear();
    }

    /**
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    private SparseArray<LinkedList<Activity>> actions = new SparseArray<LinkedList<Activity>>();

    public void addAction(int taskTag, Activity activity) {
        LinkedList<Activity> as = actions.get(taskTag);
        if (as == null) {
            as = new LinkedList<Activity>();
            actions.put(taskTag, as);
        }
        as.add(activity);
    }

    // 一系列的任务标志 1很多的1 activity直接干掉
    public void finishTask(int taskTag) {
        LinkedList<Activity> as = actions.get(taskTag);
        if (as != null) {
            for (Activity a : as) {
                a.finish();
            }
        }
        actions.remove(taskTag);
    }
    /**
     * 退出软件
     */
    public void exitApp() {
        finishAllActivity();
        clearActivitiesAndServices();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);//normal exit application
    }
    /**
     * 当内存不足时，需要清除已打开的Activity及Service
     *
     * @see android.app.Application#onLowMemory()
     */
    public void clearActivitiesAndServices() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        for (Service service : services) {
            service.stopSelf();
        }
    }

}
