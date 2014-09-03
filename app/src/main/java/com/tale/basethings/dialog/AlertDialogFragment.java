package com.tale.basethings.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by TALE on 8/18/2014.
 */
public class AlertDialogFragment extends DialogFragment {

    public AlertDialogFragment() {
    }

    public String getTitle() {
        return null;
    }

    ;

    public String getMessage() {
        return null;
    }

    ;

    public String getPositiveText() {
        return null;
    }

    ;

    public String getNegativeText() {
        return null;
    }

    ;

    public void onPositiveClick() {
    }

    ;

    public void onNegativeClick() {
    }

    ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String title = getTitle();
        final String message = getMessage();
        final String positiveText = getPositiveText();
        final String negativeText = getNegativeText();
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        if (positiveText != null) {
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onPositiveClick();
                }
            });
        }
        if (negativeText != null) {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onNegativeClick();
                }
            });
        }
        return builder.create();
    }

}
