package com.dizarale.deliverone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.adapter.ExpandableListAdapter;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.dizarale.deliverone.object.OrderStatusObject;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;

public class HistoryOrderAcitvity extends BaseActivity {
    private ProgressDialog pDialog;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.testPhone = AppSharedPreferences.getPhone(HistoryOrderAcitvity.this);
        setContentView(R.layout.activity_history_order_acitvity);
        actAsHistoryOrder();
        activateToolbarWithHomeEnabled();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showpDialog();

        retrofitTest();
    }
    public void retrofitTest(){
        Call<List<OrderStatusObject>> callUserOrder = service.loadUserOrder(testPhone);
        callUserOrder.enqueue(new Callback<List<OrderStatusObject>>() {
            @Override
            public void onResponse(retrofit.Response<List<OrderStatusObject>> response) {

                String str;
                Log.v("12345", response.body().get(0).toString());
                toExpandable(response.body());

                // setting list adapter
                //expListView.setAdapter(listAdapter);
                try {
                    if (response.errorBody() != null) {
                        str = response.errorBody().string();
                        Log.v("Error", str);
                    }

                } catch (IOException e) {

                }

                //mShoppingCartAdapter.loadNewData(response.body());

                //hidepDialog();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.v("retrofit_failure", t.getMessage());
            }
        });
    }
    private void toExpandable(List<OrderStatusObject> items){
        listAdapter = new ExpandableListAdapter(this, items);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        hidepDialog();
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
        if (id == R.id.action_qr_code){
            callCapture(null);
        }

        return super.onOptionsItemSelected(item);
    }
}
