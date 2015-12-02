package com.dizarale.deliverone.activity;

import android.app.ProgressDialog;
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
import com.google.android.gms.maps.CameraUpdateFactory;
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
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actAsMaps();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        activateToolbarWithHomeEnabled();
        markerOptions = new MarkerOptions();
        super.testPhone = AppSharedPreferences.getPhone(MapsActivity.this);

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
        latLng = new LatLng((double) 13.7248946, (double) 100.4930262);

        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
       // mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


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
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLngs) {
                mMap.clear();
                Log.v("Onclick", "Cando");
                //latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                if (latLngs != null) {
                    mMap.addMarker(markerOptions.position(latLngs).draggable(true));

                }
                latLng = latLngs;


            }

        });

    }
    public void confirmLocation(){
        postLatLong(Double.toString(latLng.latitude), Double.toString(latLng.longitude));
    }
    public void postLatLong(String lat, String lon) {
        showpDialog();

        RequestBody formBody = new FormEncodingBuilder()
                .add("cus_tel", testPhone).add("order_lat", lat)
                .add("order_long", lon)
                .add("order_detail","").build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(BASE_URL + "/preorderdetaildistance").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(LOG_TAG, e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                int food_cost;
                int delivery_cost;
                int total;
                String result = response.body().string().toString();
                //Log.v("Response Message", response.body().string());
                //toCost(response.body().string());
                JSONObject jsonObject = null;
                try {
                    String test = result;
                    jsonObject = new JSONObject(test);
                    food_cost = jsonObject.getInt("food_cost");
                    delivery_cost = jsonObject.getInt("delivery_cost");
                    total = food_cost + delivery_cost;
                    if (total == 0) {
                        startShoppingActivity();
                    } else {
                        hidepDialog();
                        Intent intent = new Intent(MapsActivity.this, SummaryActivity.class);
                        intent.putExtra("latitude", Double.toString(latLng.latitude));
                        intent.putExtra("longitude", Double.toString(latLng.longitude));
                        intent.putExtra("total", Integer.toString(total));
                        intent.putExtra("delivery_cost", Integer.toString(delivery_cost));
                        startActivity(intent);

                    }
                    Log.v(LOG_TAG, "Json result: food_cost= " + food_cost);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    public void startShoppingActivity()  {
        Intent intent = new Intent(MapsActivity.this,ShoppingCartActivity.class);
        intent.putExtra(AppConstant.MISS_LOCATION,true);
        startActivity(intent);

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




}




