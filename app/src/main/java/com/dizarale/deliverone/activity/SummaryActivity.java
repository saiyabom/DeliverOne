package com.dizarale.deliverone.activity;

import android.app.ProgressDialog;
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

import com.dizarale.deliverone.config.AppSharedPreferences;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class SummaryActivity extends BaseActivity{
    private final String LOG_TAG = SummaryActivity.class.getSimpleName();
    TextView costTotalText;
    TextView costDeliver;
    Toast toastsuccess,toastfail;

    private EditText commentOrder;
    private Button confirmButton;
    private String responseCode;

    private final String CUS_TEL="cus_tel", ORDER_LAT="order_lat", ORDER_LONG="order_long", ORDER_DETAIL="order_detail";

    //"http://um-project.com/projectx/index.php/example_api/preorderdetaildistance"
    private final String URL_CONFIRM = BASE_URL+"preorderdetaildistance";

    private String latitude,longitude;

    private Intent intent;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        setContentView(R.layout.activity_summary);
        super.testPhone = AppSharedPreferences.getPhone(SummaryActivity.this);

        confirmButton = (Button) findViewById(R.id.send_order_button);

        actAsSummary();
        activateToolbarWithHomeEnabled();
        commentOrder = (EditText) findViewById(R.id.comment_order);

        this.intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        costTotalText = (TextView) findViewById(R.id.total_cost);

        costDeliver = (TextView) findViewById(R.id.deliver_cost);

        toastsuccess = Toast.makeText(this,"Confirm OK", Toast.LENGTH_LONG );
        toastfail = Toast.makeText(this, "not find", Toast.LENGTH_LONG);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmAll();
            }
        });
    }

    public void sendConfirmAll() {
        //showpDialog();
        RequestBody formBody = new FormEncodingBuilder()
                .add(CUS_TEL, testPhone)
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
                responseCode = response.body().string();
                if(responseCode!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            openDialog(responseCode);
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        costDeliver.setText(this.intent.getStringExtra("delivery_cost"));
        costTotalText.setText(this.intent.getStringExtra("total"));
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
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    public void openDialog(String codeConfirmation){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.text_response_code_confirmation+": "+codeConfirmation);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(SummaryActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
