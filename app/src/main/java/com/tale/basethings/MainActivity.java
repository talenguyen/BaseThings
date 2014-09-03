package com.tale.basethings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tale.basethings.activity.LifeCycleFragmentActivity;
import com.tale.basethings.dialog.AlertDialogFragment;
import com.tale.basethings.dialog.LoadingDialog;
import com.tale.basethings.dialog.ProgressDialogFragment;
import com.tale.basethings.task.Task;
import com.tale.basethings.task.TaskManager;
import com.tale.basethings.util.Timber;


public class MainActivity extends LifeCycleFragmentActivity {

    private int taskId;
    ProgressDialogFragment progressDialogFragment = new LoadingDialog();

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
        Task task = new Task() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress(true);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                Timber.d("Start a task");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                showAlert("Task execute success");
            }

            @Override
            protected void onFinished() {
                super.onFinished();
                showProgress(false);
            }
        };
        taskId = TaskManager.getInstance().enqueue(task);
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
        ProgressDialogFragment progressDialog = (ProgressDialogFragment) getSupportFragmentManager().findFragmentByTag("progress");
        if (show) {
            if (progressDialog == null) {
                progressDialog = new LoadingDialog();
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
    protected void onPause() {
        super.onPause();
//        TaskManager.getInstance().cancelAll();
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
