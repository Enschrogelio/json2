package com.ruby.x.json2.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruby.x.json2.BaseActivity;
import com.ruby.x.json2.Controllers.DetailController;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;

public class DetailActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public String lat;
    public String lng;
    private GoogleMap mMap;
    private DetailController controller;
    private TextView txtTitle,txtApellido,txtEstado,txtMunicipio, txtDescription, txtCreatedDate, txtLat, txtLng;
    private ImageView imgDocumentation;
    private DataTask dataTask;
    private Bundle data;
    public String title, apellido,estado,municipio, description, created_date, task_id, file_documentation, file_documentation1;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        controller = new DetailController(this);
        initUI();
        initAttribute();
        initValue();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map_mapview);
        mapFragment.getMapAsync(this);


        Button button = (Button) findViewById(R.id.name);
        button.setText(title);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageDialogFragment dFragment = new ImageDialogFragment();
                Intent i = new Intent(DetailActivity.this, ImageDialogFragment.class);


                dFragment.setCancelable(false); //don't close when touch outside
                dFragment.show(getSupportFragmentManager(), "Dialog Fragment");

                //startActivity(i);
            }
        });

        Button button1 = (Button) findViewById(R.id.pdf);
        button1.setText("PDF");


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailActivity.this, pdf.class);
                i.putExtra("title",title);
                i.putExtra("file_documentation",file_documentation);
                i.putExtra("description",description);


                startActivity(i);
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();


        ImageView btnMyLocation = (ImageView) findViewById(R.id.btnMyLocation);
        final CameraUpdate[] cameraUpdate = {null};

        btnMyLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Location loc = mMap.getMyLocation();
                if (loc != null) {
                    LatLng latLang = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    cameraUpdate[0] = CameraUpdateFactory.newLatLngZoom(latLang, 17);
                    mMap.animateCamera(cameraUpdate[0]);
                    Toast.makeText(getApplicationContext(),"Encontrado",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        // Add a marker in colima and move the camera
        LatLng market = new LatLng(lat, -12);
        mMap.addMarker(new MarkerOptions().position(market).title(lng));
        //.setIcon(BitmapDescriptorFactory
        //.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(market));
        //mMap.getUiSettings().setMapToolbarEnabled(false);
       // mMap.setMyLocationEnabled(true);



    }
*/

    private void setUpMap() {
        float maxZoom = 17.0f;
        // Add a marker in Sydney and move the camera
        LatLng colima = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(colima).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(colima));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(maxZoom));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }
    private void initUI() {
        this.setContentView(R.layout.activity_detail);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        else if (id == R.id.action_do) {
            controller.toActivity(FormActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    DetailActivity.this,
                                    REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //...
                        break;
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode)
        {
            case REQUEST_LOCATION:
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                    {
                        // All required changes were successfully made
                        Toast.makeText(DetailActivity.this, "Localización habilitada", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(DetailActivity.this, "Localización cancelada.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
