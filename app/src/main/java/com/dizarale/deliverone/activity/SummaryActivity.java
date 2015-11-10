package com.dizarale.deliverone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SummaryActivity extends BaseActivity{
    private final String LOG_TAG = SummaryActivity.class.getSimpleName();
    TextView costTotalText;
    TextView costDeliver;

    //GetSummaryJsonData getSummaryJsonData;
    Toast toastsuccess,toastfail;

    private EditText commentOrder;
    private Button confirmButton;

    private final String CUS_TEL="cus_tel", ORDER_LAT="order_lat", ORDER_LONG="order_long", ORDER_DETAIL="order_detail";

    //"http://um-project.com/projectx/index.php/example_api/preorderdetaildistance"
    private final String URL_CONFIRM = BASE_URL+"preorderdetaildistance";

    private String latitude,longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        super.testPhone = AppSharedPreferences.getPhone(SummaryActivity.this);

        confirmButton = (Button) findViewById(R.id.send_order_button);
        costTotalText = (TextView) findViewById(R.id.total_cost);

        costDeliver = (TextView) findViewById(R.id.deliver_cost);


        actAsSummary();
        activateToolbarWithHomeEnabled();
        commentOrder = (EditText) findViewById(R.id.comment_order);

        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");


        toastsuccess = Toast.makeText(this,"Confirm OK", Toast.LENGTH_LONG );
        toastfail = Toast.makeText(this, "not find", Toast.LENGTH_LONG);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmAll();
            }
        });
        //getTotle();
       //




    }
    public void postLatLong(String lat, String lon) {

        RequestBody formBody = new FormEncodingBuilder()
                .add("cus_tel", super.testPhone).add("order_lat", lat)
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
                Log.v("Response Message", response.body().string());
                toCost(response.body().string());


            }
        });

    }
    public void toCost(String datas)  {
        String food_cost;
        String delivery_cost;
        String total;

        Log.v("TEST",datas);
        try {
            JSONObject jsonObject= new JSONObject(datas);
            food_cost = jsonObject.getString("food_cost");
            delivery_cost = jsonObject.getString("delivery_cost");
            total = food_cost + delivery_cost;

            Log.v("JSON", jsonObject.toString());
            //costTotalText.setText(Integer.toString(total));
            costDeliver.setText(delivery_cost);


           /* if(total == 0){
                //Toast.makeText(SummaryActivity.this,"Not found Shop",Toast.LENGTH_SHORT);
                Intent intent = new Intent(SummaryActivity.this,ShoppingCartActivity.class);
                startActivity(intent);
            }else {

            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.v(LOG_TAG,"senst: "+food_cost+ "& delivery_cost:" +delivery_cost);



    }
    public void sendConfirmAll(){
        RequestBody formBody = new FormEncodingBuilder()
                .add(CUS_TEL, super.testPhone)
                .add(ORDER_DETAIL, commentOrder.getText().toString()).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(BASE_URL + "/confirmorder").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Intent intent = new Intent(SummaryActivity.this, MenuActivity.class);
                startActivity(intent);
                if (response != null) {
                    String res = response.body().string();
                    if (res.contains("confirm OK")) {
                        toastsuccess.show();


                    } else if (res.contains("not find")) {
                        toastfail.show();
                        Log.v(LOG_TAG, res);

                    } else {
                        Log.v(LOG_TAG, res);
                    }
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        postLatLong(latitude, longitude);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_shopping_cart){
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }
        /*if (id == R.id.action_maps){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


}
