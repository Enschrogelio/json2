package com.ruby.x.json2.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Views.MainActivity;

public class TaskListAsyncController extends AsyncTask<Void, Void, String> {
    private Activity activity;
    private GlobalHelper globalHelper;
    private Integer page;
    private AlertDialog dialog;

    public TaskListAsyncController(Activity act, Integer pg) {
        super();
        activity = act;
        globalHelper = new GlobalHelper(activity);
        page = pg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = globalHelper.getUIHelper().getSpotsDialog(this.activity);
        dialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return globalHelper.getServerDataAccess().getTaskList(page);
    }

    @Override
    protected void onPostExecute(String result) {
        ((MainActivity) activity).parseJsonResponse(result);
        dialog.dismiss();
        Log.i(DataConstant.APPS_TAG, result);
    }

}
