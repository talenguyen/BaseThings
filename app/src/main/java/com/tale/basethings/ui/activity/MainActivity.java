package com.tale.basethings.ui.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.View;

import com.google.common.collect.Lists;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.Params;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.tale.basethings.DemoApp;
import com.tale.basethings.R;
import com.tale.basethings.dialog.AlertDialogFragment;
import com.tale.basethings.dialog.ProgressDialogFragment;
import com.tale.basethings.task.Task;
import com.tale.basethings.task.TaskManager;
import com.tale.basethings.util.Timber;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity {

    private final TaskManager taskManager;
    private int taskId;
    @Inject Bus bus;
    @Inject
    JobManager jobManager;

    public MainActivity() {
        taskManager = TaskManager.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btNewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDummyTaskWithJobQueue();
            }
        });
        bus = ((DemoApp) getApplication()).getBus();
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = Lists.newArrayList();
        modules.add(new MainActivityModule());
        return modules;
    }

    private void newDummyTask() {
        Task<Boolean> dummyTask = new Task<Boolean>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress(true);
            }

            @Override
            protected Boolean doInBackground(Object... params) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Boolean.TRUE;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                bus.post(result);
            }

            @Override
            protected void onFinished() {
                super.onFinished();
                showProgress(false);
            }
        };
        taskId = taskManager.enqueue(dummyTask);
    }

    private void newDummyTaskWithJobQueue() {
        Job job = new Job(new Params(1).requireNetwork().persist()) {
            @Override
            public void onAdded() {
                Timber.d("onAdded");
            }

            @Override
            public void onRun() throws Throwable {
                Timber.d("onRun");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onCancel() {
                Timber.d("onCancel");
            }

            @Override
            protected boolean shouldReRunOnThrowable(Throwable throwable) {
                return true;
            }
        };
        jobManager.addJob(job);
    }

    @Subscribe
    public void onDummyTaskResult(Boolean result) {
        showProgress(false);
        showAlert("Task execute success: " + result);
    }

    private void showAlert(final String message) {
        new AlertDialogFragment(){
            @Override
            public String getMessage() {
                return message;
            }
        }.show(getSupportFragmentManager(), "alert");
    }

    void showProgress(boolean show) {
        DialogFragment progressDialog = (DialogFragment) getSupportFragmentManager().findFragmentByTag("progress");
        if (show) {
            if (progressDialog == null) {
                progressDialog = ProgressDialogFragment.newInstance("Doing on background", false);
            }
            progressDialog.show(getSupportFragmentManager(), "progress");
        } else {
            Timber.d("Prepare for dismiss: " + progressDialog);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
