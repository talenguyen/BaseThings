package com.tale.basethings;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.tale.basethings.activity.LifeCycleFragmentActivity;
import com.tale.basethings.dialog.AlertDialogFragment;
import com.tale.basethings.dialog.ProgressDialogFragment;
import com.tale.basethings.task.Task;
import com.tale.basethings.task.TaskManager;
import com.tale.basethings.util.Timber;


public class MainActivity extends LifeCycleFragmentActivity {

    private final TaskManager taskManager;
    private int taskId;
    AbsProgressDialogFragment progressDialogFragment = new LoadingDialog();

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
                newDummyTask();
            }
        });
    }

    private void newDummyTask() {
//        Task task = new Task() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                showProgress(true);
//            }
//
//            @Override
//            protected Object doInBackground(Object[] params) {
//                Timber.d("Start a task");
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                super.onPostExecute(o);
//                showAlert("Task execute success");
//            }
//
//            @Override
//            protected void onFinished() {
//                super.onFinished();
//                showProgress(false);
//            }
//        };
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
            protected void onFinished() {
                super.onFinished();
                showProgress(false);
            }
        };
        taskId = taskManager.enqueue(dummyTask);
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
        taskManager.registerCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskManager.unregisterCallback(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
