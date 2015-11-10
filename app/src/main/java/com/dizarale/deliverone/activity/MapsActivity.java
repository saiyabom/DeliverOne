package com.dizarale.deliverone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.dizarale.deliverone.R;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback{
    private String LOG_TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private SupportMapFragment mapFragment;
    private MarkerOptions markerOptions;
    OkHttpClient okHttpClient;

    private Button confirmButton;
    private LatLng latLng;
    private boolean checkButtonClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actAsMaps();
        activateToolbarWithHomeEnabled();
        markerOptions = new MarkerOptions();

        setContentView(R.layout.activity_maps);
        okHttpClient = new OkHttpClient();


        confirmButton = (Button) findViewById(R.id.confirm_button123);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLocation();
            }
        });
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        if(mapFragment ==null){
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getView().setClickable(true);

            mapFragment.getMapAsync(this);

        }

    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LatLng PERTH = new LatLng((double) 0, (double) 0);

        mMap.addMarker(new MarkerOptions().position(PERTH).draggable(true));

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                mMap.setMyLocationEnabled(true);

                if (mMap.getMyLocation() != null) {
                    mMap.clear();
                    Log.v("Onclick", "Cando");

                    latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                    if (latLng != null) {
                        mMap.addMarker(markerOptions.position(latLng).draggable(true));

                    }

                } else {
                    Toast.makeText(MapsActivity.this, "Waiting", Toast.LENGTH_SHORT).show();
                    // mMap.getMyLocation().
                }
                return false;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

                latLng = marker.getPosition();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

                latLng = marker.getPosition();
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                latLng = marker.getPosition();


            }
        });

    }
    public void confirmLocation(){

        Intent intent = new Intent(MapsActivity.this, SummaryActivity.class);
        intent.putExtra("latitude",Double.toString(latLng.latitude));
        intent.putExtra("longitude", Double.toString(latLng.longitude));
        startActivity(intent);

    }




}




