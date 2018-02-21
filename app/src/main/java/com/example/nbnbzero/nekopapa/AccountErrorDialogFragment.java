package com.example.nbnbzero.nekopapa;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by NbnbZero on 2/20/2018.
 */

public class AccountErrorDialogFragment extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.error))
                .setMessage(getResources().getString(R.string.passwords_match_error_text))
                .setPositiveButton(getResources().getString(R.string.try_again_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
    }
}

