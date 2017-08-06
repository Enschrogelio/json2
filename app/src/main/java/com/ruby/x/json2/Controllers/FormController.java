package com.ruby.x.json2.Controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

import com.ruby.x.json2.DataAccess.ServerDataAccessListener;
import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.Views.FormActivity;
import com.ruby.x.json2.Views.MainActivity;
import okhttp3.Request;

public class FormController {
    private FormActivity activity;
    private GlobalHelper globalHelper;

    public FormController(FormActivity act) {
        activity = act;
        this.globalHelper = new GlobalHelper(activity);
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.activity.startActivityForResult(Intent.createChooser(intent, "Select File"), DataConstant.SELECT_FILE);
    }

    private void toActivity(Class<?> actCls) {
        Intent intent = new Intent(activity, actCls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public void doSave() {
        String state = activity.state;
        DataTask data = new DataTask();
        data.setId(activity.task_id);
        data.setTitle(activity.txtTitle.getText().toString());
        data.setDescription(activity.txtDescription.getText().toString());
        data.setCreatedDate(activity.txtCreatedDate.getText().toString());
        data.setFileDocumentation(activity.file_documentation);

        final AlertDialog dialog = activity.getGlobalHelper().getUIHelper().getSpotsDialog(this.activity);
        dialog.show();

        globalHelper.getServerDataAccess().doSave(state, data, new ServerDataAccessListener() {
            @Override
            public void onFailure(Request request, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Connection Error", "Connection error please try again.", "OK");
                    }
                });
            }

            @Override
            public void onResponseSuccess(final String response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isEmpty() && response != null) {
                            Toast.makeText(activity, "Process successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            toActivity(MainActivity.class);
                        }
                        else {
                            globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponseError(final String response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                    }
                });
            }
        });
    }

    public void doDelete() {
        String task_id = activity.task_id;

        final AlertDialog dialog = activity.getGlobalHelper().getUIHelper().getSpotsDialog(this.activity);
        dialog.show();

        globalHelper.getServerDataAccess().doDelete(task_id, new ServerDataAccessListener() {
            @Override
            public void onFailure(Request request, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Connection Error", "Connection error please try again.", "OK");
                    }
                });
            }

            @Override
            public void onResponseSuccess(final String response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.isEmpty() && response != null) {
                            Toast.makeText(activity, "Process successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            toActivity(MainActivity.class);
                        }
                        else {
                            globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponseError(final String response) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        globalHelper.getUIHelper().ShowAlertDialogNotification("Failed", "Process Failed", "OK");
                    }
                });
            }
        });
    }
}
