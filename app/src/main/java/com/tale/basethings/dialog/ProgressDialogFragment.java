package com.tale.basethings.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

/**
 * Created by TALE on 8/29/2014.
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final String KEY_MSG = "msg";
    private static final String KEY_CANCELABLE= "cancelable";

    public ProgressDialogFragment() {
    }

    public static ProgressDialogFragment newInstance(String msg, boolean cancelable) {
        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        final Bundle args = new Bundle();
        args.putBoolean(KEY_CANCELABLE, cancelable);
        if (!TextUtils.isEmpty(msg)) {
            args.putString(KEY_MSG, msg);
        }
        progressDialogFragment.setArguments(args);
        return progressDialogFragment;
    }

    public void show(FragmentManager fragmentManager) {
        DialogFragment progressDialog = (DialogFragment) fragmentManager.findFragmentByTag("progress");
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment.newInstance("Doing on background", false);
        }
        progressDialog.show(fragmentManager, "progress");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        final Bundle args = getArguments();
        final String msg = args == null ? null : args.getString(KEY_MSG);
        if (msg != null) {
            dialog.setMessage(msg);
        } else {
            dialog.setMessage("Loading...");
        }
        final boolean cancelable = args == null ? false : args.getBoolean(KEY_CANCELABLE, false);
        dialog.setCancelable(cancelable);
        return dialog;
    }

}
