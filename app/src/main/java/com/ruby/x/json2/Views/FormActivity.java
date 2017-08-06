package com.ruby.x.json2.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.ruby.x.json2.BaseActivity;
import com.ruby.x.json2.Controllers.FormController;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.R;

public class FormActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FormController controller;
    //public EditText txtDescription, txtCreatedDate;
    public TextView btnDelete, txtTitle, txtDescription, txtCreatedDate;
    public ImageView imagePreview;
    public File filePhoto;
    public Typeface tf;
    public String file_documentation,file_documentation1;
    public Uri imageUri;
    private android.app.DatePickerDialog DatePickerdateOfBirth;
    private SimpleDateFormat dateFormatter;
    private Bundle passValue;
    public String title, description, created_date, state, task_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initValue();
        setDateTimeField();
        initAttribute();
        controller = new FormController(this);
    }

    private void initUI() {
        this.setContentView(R.layout.activity_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtCreatedDate = (TextView) findViewById(R.id.txtLng);
        imagePreview = (ImageView) findViewById(R.id.imagePreview);
        /*btnDelete = (TextView) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
                builder.setMessage("Are you sure to delete this data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                controller.doDelete();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Delete Data");
                alert.show();
            }
        });
        */
    }

    private void initValue() {
        file_documentation = getGlobalHelper().getImageHelper().getFilename();
        imageUri = Uri.fromFile(new File(getGlobalHelper().getImageHelper().getUriFile() + "/" + file_documentation));
        file_documentation1 = getGlobalHelper().getImageHelper().getFilename();
        imageUri = Uri.fromFile(new File(getGlobalHelper().getImageHelper().getUriFile() + "/" + file_documentation1));
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        passValue = getIntent().getExtras();
        state = passValue.getString("state");
        if(state.equals("add")) {
            title = "";
            description = "";
            created_date = "";
            task_id = "";
            btnDelete.setVisibility(View.GONE);
        } else {
            title = passValue.getString("title");
            description = passValue.getString("description");
            created_date = passValue.getString("created_date");
            task_id = passValue.getString("task_id");
            txtTitle.setText(title);
            txtDescription.setText(description);
            txtCreatedDate.setText(created_date);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setDateTimeField() {
        txtCreatedDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerdateOfBirth = new android.app.DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtCreatedDate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initAttribute() {
        imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });
    }

    public void launchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, DataConstant.REQUEST_CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DataConstant.REQUEST_CAMERA)
                try {
                    imagePreview.setImageBitmap(getGlobalHelper().getImageHelper().compressImage(imageUri.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        else if (id == R.id.action_save) {
            controller.doSave();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == txtCreatedDate) {
            DatePickerdateOfBirth.show();
        }
    }
}
