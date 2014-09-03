package com.tale.basethings.dialog;

/**
 * Created by TALE on 9/3/2014.
 */
public class LoadingDialog extends ProgressDialogFragment {
    @Override
    public CharSequence getMessage() {
        return "Loading";
    }

    @Override
    public boolean isCancelable(boolean cancelable) {
        return false;
    }
}
