package com.ruby.x.json2.Libraries;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruby.x.json2.Controllers.MainController;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;
import com.ruby.x.json2.Views.DetailActivity;

import java.util.List;

public class AdapterListViewTask1 extends ArrayAdapter<DataTask> {
    private Activity activity;
    private int layout;
    private MainController controller;

    public AdapterListViewTask1(Activity activity, MainController controller, int resource, List<DataTask> taskList) {
        super(activity, resource, taskList);
        this.activity = activity;
        this.layout = resource;
        this.controller = controller;
    }

    private static class ViewHolder {
        private TextView txtTitle, txtDescription, txtCreatedDate;
        private LinearLayout lsTask;
        public ViewHolder(View v) {
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtDescription = (TextView) v.findViewById(R.id.txtDescription);
            txtCreatedDate = (TextView) v.findViewById(R.id.txtCreatedDate);
            lsTask = (LinearLayout) v.findViewById(R.id.lsTask);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DataTask data = getItem(position);
        holder.txtTitle.setText(data.getTitle());
        holder.txtDescription.setText(data.getDescription());
        holder.txtCreatedDate.setText(data.getCreatedDate());

        holder.lsTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.toActivity(DetailActivity.class, data);
            }
        });

        return convertView;
    }
}
