package com.ruby.x.json2.Views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.GoogleMap;
import com.ruby.x.json2.BaseActivity;
;
import com.ruby.x.json2.Controllers.DetailController;
import com.ruby.x.json2.Controllers.FormController;
import com.ruby.x.json2.Libraries.ImageLoader;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;


/**
 * Created by x on 18/07/2017.
 */

public class pdf extends BaseActivity{
    private DetailController controller;
    public String lat;
    public String lng;
    private GoogleMap mMap;
    //private PdfController controller;
    private TextView txtTitle,txtApellido,txtEstado,txtMunicipio, txtDescription, txtCreatedDate, txtLat, txtLng;
    private ImageView imgDocumentation;
    private DataTask dataTask;
    private Bundle data;
    public String title, apellido,estado,municipio, description, created_date, task_id, file_documentation, file_documentation1;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        initValue();
        initUI();
        //Intent i = getIntent();
        //String title = i.getStringExtra("title");
        //String file_documentation = i.getStringExtra("file_documentation");
        String htmlDocument =
                "<html><body><h1>"+title+" Print Test</h1><p>" +description+ ".</p> <tr> <td><img src=\"file:///file_documentation\" style=\"float:left;\"></td> </tr></body></html>";

        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/HTML", "UTF-8", null);

        myWebView = webView;
    }
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

    }


    private void initUI() {
        //this.setContentView(R.layout.activity_html_print);
        //txtTitle = (TextView) findViewById(R.id.txtTitle);
        // txtDescription = (TextView) findViewById(R.id.txtDescription);
        //txtCreatedDate = (TextView) findViewById(R.id.txtCreatedDate);
        //txtLat = (TextView) findViewById(R.id.txtLat);
        //txtLng = (TextView) findViewById(R.id.txtLng);
        //imgDocumentation = (ImageView) findViewById(R.id.imgDocumentation);
    }

    private void initValue() {
        data = getIntent().getExtras();
        title = data.getString("title");
        description = data.getString("description");
        lat = data.getString("lat");
        lng = data.getString("lng");
        apellido = data.getString("apellido");
        estado = data.getString("estado");
        municipio = data.getString("municipio");
        created_date = data.getString("created_date");
        file_documentation = data.getString("file_documentation");
        file_documentation1 = data.getString("file_documentation1");
        task_id = data.getString("task_id");
        //txtTitle.setText(title);
        //txtDescription.setText(description);
        // txtLat.setText(lat);
        //txtLng.setText(lng);
        //txtCreatedDate.setText(created_date);
        //new ImageLoader(this).execute(DataConstant.ASSET_URL + file_documentation);
    }

    private void initAttribute() {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        /*
        else if (id == R.id.action_do) {
            controller.toActivity(FormActivity.class);
        }
*/
        return super.onOptionsItemSelected(item);
    }

}
