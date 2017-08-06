package com.ruby.x.json2.Helpers;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;

import com.ruby.x.json2.R;

public class UIHelper extends GlobalHelper {

    public UIHelper(Context context) {
        super(context);
    }

    public void ShowAlertDialogNotification(String title, String message, String positiveButton) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this.context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveButton, null);
        builder.show();
    }

    public SpotsDialog getSpotsDialog(Context context) {
        final SpotsDialog dialog = new SpotsDialog(context, R.style.SpotsDialogCustom);
        return dialog;
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message , Toast.LENGTH_SHORT).show();
    }

    public void setHintOnEditText(final EditText editText, final String hintText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHint("");
                } else {
                    editText.setHint(hintText);
                }
            }
        });
    }

}
