package com.dizarale.deliverone.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.object.FoodItem;
import com.dizarale.deliverone.recyclerview.FoodItemRecyclerViewAdapter;
import com.dizarale.deliverone.R;
import com.dizarale.deliverone.recyclerview.RecyclerItemClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by s84021369 on 8/28/2015.
 */
public class MenuActivity extends BaseActivity {
    private static String LOG_TAG = MenuActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FoodItemRecyclerViewAdapter mRecyclerViewAdapter;
    private String mType;
    private static final int REQUEST_CODE = 200;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mType = String.valueOf(BaseActivity.mType);
        activateToolbar();
        setUpNavigationDrawer();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewAdapter = new FoodItemRecyclerViewAdapter(new ArrayList<FoodItem>(), MenuActivity.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.v(LOG_TAG, "Normal Tab");
                        Toast.makeText(MenuActivity.this, "Normal TAP", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MenuActivity.this, FoodDetailActivity.class);
                        intent.putExtra(AppConstant.FOODITEM_TRANSFER, mRecyclerViewAdapter.getFoodItem(position));
                        Log.v(LOG_TAG, "putExtra:" + mRecyclerViewAdapter.getFoodItem(position).toString());
                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Log.v(LOG_TAG, "Long Tab");
                        //Toast.makeText(MenuActivity.this, "Long TAP",Toast.LENGTH_SHORT).show();

                    }
                }
        ));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Call<List<FoodItem>> call = service.loadListFoodItem("1", mType);
        Log.v(LOG_TAG, service.toString());
        call.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Response<List<FoodItem>> response) {
                String str = "";
                try {
                    if (response.errorBody() != null) {
                        str = response.errorBody().string();
                        Log.v(LOG_TAG, str);
                    }

                } catch (IOException e) {

                }
                mRecyclerViewAdapter.loadNewData(response.body());

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
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
            showDialog(this);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null != data && requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    data.setClass(this, ShoppingCartActivity.class);
                    startActivity(data);
                    break;
                default:
                    break;
            }
        }
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

