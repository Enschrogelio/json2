package com.ruby.x.json2.Controllers;

import android.content.Intent;

import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Views.DetailActivity;

public class DetailController {
    private DetailActivity activity;
    private GlobalHelper globalHelper;

    public DetailController(DetailActivity act) {
        activity = act;
        globalHelper = new GlobalHelper(activity);
    }

    public void toActivity(Class<?> actCls) {
        Intent intent = new Intent(activity, actCls);
        intent.putExtra("state", "edit");
        intent.putExtra("title", activity.title);
        intent.putExtra("description", activity.description);
        intent.putExtra("created_date", activity.created_date);
        intent.putExtra("task_id", activity.task_id);
        activity.startActivity(intent);
    }
}
