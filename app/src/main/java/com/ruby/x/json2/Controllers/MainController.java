package com.ruby.x.json2.Controllers;

import android.content.Intent;
import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.Views.MainActivity;

public class MainController {
    private MainActivity activity;
    private GlobalHelper globalHelper;

    public MainController(MainActivity act) {
        activity = act;
        globalHelper = new GlobalHelper(act);
    }

    public void toActivity(Class<?> actCls, DataTask data) {
        Intent intent = new Intent(activity, actCls);
        intent.putExtra("state", "add");
        intent.putExtra("title", data.getTitle());
        intent.putExtra("lat", data.getLat());
        intent.putExtra("lng", data.getLng());
        intent.putExtra("apellido", data.getApellido());
        intent.putExtra("estado", data.getEstado());
        intent.putExtra("municipio", data.getMunicipio());
        intent.putExtra("description", data.getDescription());
        intent.putExtra("created_date", data.getCreatedDate());
        intent.putExtra("file_documentation", data.getFileDocumentation());
        intent.putExtra("file_documentation1", data.getFileDocumentation1());
        intent.putExtra("task_id", data.getId());
        activity.startActivity(intent);
    }
}
