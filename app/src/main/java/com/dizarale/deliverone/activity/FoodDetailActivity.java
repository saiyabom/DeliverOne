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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dizarale.deliverone.config.AppSharedPreferences;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Callback;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.object.FoodItem;
import com.dizarale.deliverone.R;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



import retrofit.Retrofit;


/**
 * Created by s84021369 on 8/29/2015.
 */
public class FoodDetailActivity extends BaseActivity {
    private static String LOG_TAG = FoodDetailActivity.class.getSimpleName();

    private ImageView imageFood;
    private TextView nameFood;
    private TextView priceFood;
    private TextView detailFood;
    private EditText etc;
    private FoodItem foodItem;
    private Button addtoCartButton;
    private Retrofit retrofit;;
    // Progress dialog
    private ProgressDialog pDialog;

    private EditText numberEditText,commentEditText;
    Intent intent = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail_activity);
        super.testPhone = AppSharedPreferences.getPhone(FoodDetailActivity.this);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        numberEditText = (EditText) findViewById(R.id.number_edittext);
        commentEditText = (EditText) findViewById(R.id.comment_edittext);

        Intent intent = getIntent();
        foodItem = (FoodItem) intent.getSerializableExtra(AppConstant.FOODITEM_TRANSFER);
        //actAsFoodDetail(foodItem.getMenuName());
        actAsFoodDetail(foodItem.getMenuNameThai());
        activateToolbarWithHomeEnabled();

        imageFood = (ImageView) findViewById(R.id.food_image);
        nameFood = (TextView) findViewById(R.id.food_name);
        nameFood.setText(foodItem.getMenuNameThai());
        priceFood = (TextView) findViewById(R.id.food_price);
        priceFood.setText(foodItem.getMenuCost());
        detailFood = (TextView) findViewById(R.id.food_detail);
        detailFood.setText(foodItem.getMenuDetail());
        addtoCartButton = (Button) findViewById(R.id.tocart_button);
        this.intent= new Intent(FoodDetailActivity.this, ShoppingCartActivity.class);
        addtoCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToCart(foodItem,"postToCart");



            }
        });


        Picasso.with(this).load(foodItem.getMenuPic())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageFood);
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


        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_shopping_cart){
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postToCart(FoodItem item, String tag) {
        showpDialog();
       /* Map<String,String> params = new HashMap<String, String>();
        params.put("cus_tel", testPhone);
        params.put("menu_id", item.getMenuId());
        params.put("menu_num", numberEditText.getText().toString());
        params.put("menu_des", commentEditText.getText().toString());*/
        RequestBody formBody = new FormEncodingBuilder()
                .add("cus_tel", testPhone).add("menu_id", item.getMenuId())
                .add("menu_num", numberEditText.getText().toString())
                .add("menu_des", commentEditText.getText().toString()).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(BASE_URL + "/preorder").post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() == 200) {

                    hidepDialog();
                    startActivity(intent);
                    finish();
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


}
