package com.tale.basethings.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by TALE on 8/29/2014.
 */
public abstract class ProgressDialogFragment extends DialogFragment {

    public ProgressDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        if (getMessage() != null) {
            dialog.setMessage(getMessage());
        } else {
            dialog.setMessage("Loading...");
        }
        return dialog;
    }

    public abstract CharSequence getMessage();

    public abstract boolean isCancelable(boolean cancelable);
}
