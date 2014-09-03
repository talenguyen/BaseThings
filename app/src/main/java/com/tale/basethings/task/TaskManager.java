package com.tale.basethings.task;

import android.os.Build;
import android.util.SparseArray;

import com.squareup.otto.Bus;
import com.tale.basethings.util.Timber;

/**
 * Created by TALE on 8/22/2014.
 */
public class TaskManager {
    private static TaskManager sInstance;
    private final Bus mBus;
    private final SparseArray<Task> mTasks;
    private int mIndex;

    public static TaskManager getInstance() {
        if (sInstance == null) {
            sInstance = new TaskManager();
        }
        return sInstance;
    }

    private TaskManager() {
        mIndex = 0;
        mTasks = new SparseArray<Task>();
        mBus = new Bus();
    }

    public void registerCallback(Object object) {
        mBus.register(object);
    }

    public void unregisterCallback(Object object) {
        mBus.unregister(object);
    }

    public void post(Object object) {
        mBus.post(object);
    }

    public synchronized int enqueue(Task task) {
        if (task != null) {
            mIndex++;
            mTasks.put(mIndex, task);
            if (Build.VERSION.SDK_INT >= 11) {
                //--post GB use serial executor by default --
                task.executeOnExecutor(Task.THREAD_POOL_EXECUTOR);
            } else {
                //--GB uses ThreadPoolExecutor by default--
                task.execute();
            }
            return mIndex;
        }
        return -1;
    }

    public synchronized boolean cancel(int key) {
        Task task = mTasks.get(key);
        if (task != null) {
            task.cancel(true);
            Timber.d("Canceled task for key: " + key);
            return true;
        }
        return false;
    }

    public synchronized void cancelAll() {
        int size = mTasks.size();
        Timber.d("Task's size: " + size);
        for (int i = 0; i < size; i++) {
            int key = mTasks.keyAt(i);
            Task task = mTasks.get(key);
            if (task != null) {
                task.cancel(true);
            }
        }
    }

    public synchronized boolean remove(Task task) {
        int indexOfValue = mTasks.indexOfValue(task);
        if (indexOfValue >= 0) {
            Timber.d("Before remove => size " + mTasks.size() + " index " + indexOfValue);
            mTasks.removeAt(indexOfValue);
            Timber.d("After removed => size " + mTasks.size());
            return true;
        }
        return false;
    }

}
