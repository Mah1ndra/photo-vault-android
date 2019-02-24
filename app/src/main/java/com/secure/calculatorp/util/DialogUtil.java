package com.secure.calculatorp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.secure.calculatorp.R;


public class DialogUtil {

    public static void showAlert(String title, String message, Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(R.string.okay, (dialog1, id) -> dialog1.dismiss());
        dialog.create().show();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
