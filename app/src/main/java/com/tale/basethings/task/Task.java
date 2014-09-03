package com.tale.basethings.task;

import android.os.AsyncTask;

import com.tale.basethings.util.Timber;

public abstract class Task<Result> extends AsyncTask<Object, Object, Result> {

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        Timber.d("onPostExecute");
        onFinished();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Timber.d("onCancelled");
        onFinished();
    }

    /**
     * Called when task is completed or cancelled.
     */
    protected void onFinished() {
        Timber.d("onFinished");
        boolean removed = TaskManager.getInstance().remove(this);
        Timber.d("Task is removed: " + removed);
    }
}