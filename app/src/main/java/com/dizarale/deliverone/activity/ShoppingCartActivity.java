package com.dizarale.deliverone.activity;


import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dizarale.deliverone.adapter.SimpleItemTouchHelperCallback;
import com.dizarale.deliverone.R;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.dizarale.deliverone.object.ShoppingCart;
import com.dizarale.deliverone.recyclerview.RecyclerItemClickListener;
import com.dizarale.deliverone.recyclerview.ShoppingCartAdapter;
import com.google.zxing.client.android.Intents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class ShoppingCartActivity extends BaseActivity{
    private final String LOG_TAG = ShoppingCartActivity.class.getSimpleName();
    private List<ShoppingCart> mShoppingCartList;
    private RecyclerView mRecyclerView;
    // Progress dialog
    private ProgressDialog pDialog;
    //Adapter
   // private NotesAdapter mNotesAdapter;
    private ShoppingCartAdapter mShoppingCartAdapter;

    private Button locationButton;
    Intent intent;

    boolean faulty;



    private RecyclerView.LayoutManager mLayoutManager;

    private ItemTouchHelper.Callback callback;

    private ItemTouchHelper itemTouchHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.testPhone = AppSharedPreferences.getPhone(ShoppingCartActivity.this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        setContentView(R.layout.activity_shopping_cart);
        mShoppingCartList = new ArrayList<ShoppingCart>();
        actAsShoppingCart();
        Intent intentFromMapFault = getIntent();
        faulty = intentFromMapFault.getBooleanExtra(AppConstant.MISS_LOCATION,false);


        activateToolbarWithHomeEnabled();

        //setUpRecyclerView();
        mRecyclerView = (RecyclerView) findViewById(R.id.shopping_cart_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mShoppingCartAdapter = new ShoppingCartAdapter(ShoppingCartActivity.this, new ArrayList<ShoppingCart>());
        callback = new SimpleItemTouchHelperCallback(mShoppingCartAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mShoppingCartAdapter);

        locationButton= (Button) findViewById(R.id.location);
        intent = new Intent(this, MapsActivity.class);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if(faulty){
            Toast.makeText(ShoppingCartActivity.this,"This location is not My Service Area",Toast.LENGTH_LONG).show();
        }
        //getListShoppingCart();


    }
    private void getListShoppingCart(){
        Call<List<ShoppingCart>> callShoppingCart = service.loadListShoppingCart(testPhone);
        callShoppingCart.enqueue(new Callback<List<ShoppingCart>>() {
            @Override
            public void onResponse(Response<List<ShoppingCart>> response) {
                String str;
                try{
                    if(response.errorBody()!=null){
                        str = response.errorBody().string();
                        Log.v(LOG_TAG, str);
                    }

                }catch(IOException e){

                }
                mShoppingCartAdapter.loadNewData(response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void initData() {

        Intent intent = getIntent();
        String urlQrCode = intent.getStringExtra(Intents.Scan.RESULT);
        if (null != intent && urlQrCode != null) {
            showpDialog();
            if (urlQrCode.contains("um-project.com/projectx/")) {

                sendOrderOverQRCodeResult(intent.getStringExtra(Intents.Scan.RESULT));
            } else {
                Toast.makeText(ShoppingCartActivity.this, "URL from QR Code is wrong: "+urlQrCode, Toast.LENGTH_LONG).show();
                getListShoppingCart();
            }
        }
            /*tvResult.setText(intent.getStringExtra(Intents.Scan.RESULT));
            tvResultFormat.setText(intent.getStringExtra(Intents.Scan.RESULT_FORMAT));
            tvUri.setText(intent.toUri(intent.getFlags()));*/

        else getListShoppingCart();
    }


    private void sendOrderOverQRCodeResult(String result){
        RequestBody formBody = new FormEncodingBuilder()
                .add("cus_tel", testPhone).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        //result should contained URL and order detail
        Request request = builder.url(result).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(ShoppingCartActivity.this,"Fail QRCode request no response",Toast.LENGTH_LONG).show();
                getListShoppingCart();
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                if (response.code() == 200) {

                    hidepDialog();
                    getListShoppingCart();
                }
                Log.v(LOG_TAG, response.toString());
            }
        });
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void setUpRecyclerView() {

        mShoppingCartAdapter = new ShoppingCartAdapter(ShoppingCartActivity.this, new ArrayList<ShoppingCart>());

        mRecyclerView = (RecyclerView) findViewById(R.id.shopping_cart_view);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mShoppingCartAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // edit(view);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
               /* PopupMenu popupMenu = new PopupMenu(NotesActivity.this, view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu_actions_notes, popupMenu.getMenu());
                popupMenu.show();
                final View v = view;
                final int pos = position;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_delete) {
                            moveToTrash();
                            delete(v, pos);
                        } else if (item.getItemId() == R.id.action_archive) {
                            moveToArchive(v, pos);
                        } else if (item.getItemId() == R.id.action_edit) {
                            edit(v);
                        }

                        return false;
                    }
                });*/
            }
        }));
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
