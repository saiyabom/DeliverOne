package com.dizarale.deliverone.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.dizarale.deliverone.gcm.MyGcmListenerService;
import com.dizarale.deliverone.gcm.MyInstanceIDListenerService;
import com.dizarale.deliverone.gcm.RegistrationIntentService;
import com.dizarale.deliverone.object.FoodItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends BaseActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final String TAG = MainActivity.class.getSimpleName();

    Intent intentGCM;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        intentGCM = new Intent(this, RegistrationIntentService.class);

        if(AppSharedPreferences.hasPhoneNumber(MainActivity.this, AppConstant.PREF_PHONE,false)){
            setContentView(R.layout.activity_first_page);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                    Intent mainIntent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }else {

            setContentView(R.layout.activity_config_phone);
            configLayoutPhone();
        }
       // setContentView(R.layout.activity_first_page);

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/

    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private void configLayoutPhone(){
        final EditText editName = (EditText) findViewById(R.id.edittext_name);
        final EditText editPhone = (EditText) findViewById(R.id.edittext_phone);
        Button confirm = (Button) findViewById(R.id.confirm_phone_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppSharedPreferences.setNamePhone(MainActivity.this, editName.getText().toString(), editPhone.getText().toString());
                if (checkPlayServices()) {
                    // Start IntentService to register this application with GCM.
                    startService(intentGCM);
                }
                postCusTel(editName.getText().toString(),editPhone.getText().toString());



            }
        });
    }
    public void postCusTel(String name, String phone) {

        RequestBody formBody = new FormEncodingBuilder()
                .add("cus_tel", phone).add("cus_name", name).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(BASE_URL + "/cus").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 200) {
                    //Toast.makeText(MainActivity.this, "Customer already register", Toast.LENGTH_LONG).show();
                    startTOMenuActivity();
                }

            }
        });

    }
    private void startTOMenuActivity(){
                /* Create an Intent that will start the Menu-Activity. */

                AppSharedPreferences.setHasPhoneNumber(MainActivity.this, AppConstant.PREF_PHONE, true);
                Intent mainIntent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(mainIntent);

    }
}