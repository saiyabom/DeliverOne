package com.dizarale.deliverone.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.dizarale.deliverone.R;

import com.dizarale.deliverone.adapter.ItemTouchHelperAdapter;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.dizarale.deliverone.object.ShoppingCart;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by s84021369 on 8/29/2015.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartHolder> implements ItemTouchHelperAdapter {
    private final String LOG_TAG = ShoppingCartAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private String urlDelete = "http://um-project.com/projectx/index.php/example_api/preorderdel";


    private List<ShoppingCart> mShoppingCartList;
    private Context mContext;

    public ShoppingCartAdapter(Context mContext, List<ShoppingCart> mShoppingCartList) {
        mInflater = LayoutInflater.from(mContext);
        this.mShoppingCartList = mShoppingCartList;
        this.mContext = mContext;
    }
    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        postToDelete(mShoppingCartList.get(position));
        mShoppingCartList.remove(position);
        notifyItemRemoved(position);

    }
   public boolean postToDelete(final ShoppingCart item){
        boolean result = true;
        String tag = "deletePreorder";
       RequestBody formBody = new FormEncodingBuilder()
               .add("cus_tel", AppSharedPreferences.getPhone(mContext))
               .add("menu_id", item.getMenuId())
               .build();
       OkHttpClient okHttpClient = new OkHttpClient();
       Request.Builder builder = new Request.Builder();
       Request request = builder.url(urlDelete).post(formBody).build();
       okHttpClient.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Request request, IOException e) {
           }

           @Override
           public void onResponse(Response response) throws IOException {
               if(response.code()==200){
               }
               Log.v(LOG_TAG,response.toString());
           }
       });
        return result;

    }

    // Called when the RecyclerView needs a new RecyclerView.ViewHolder (NoteHolder)
    // to represent an item.  We inflate the XML layout and return our view (NoteHolder)
    @Override
    public ShoppingCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        ShoppingCartHolder shoppingCartHolder= new ShoppingCartHolder(view);
        return shoppingCartHolder;
    }

    // Called by RecyclerView to display the data (a note) at the specified position.
    // This method needs to update the contents of the view to reflect the item at the
    // given position e.g. we are updating the view here with the data
    @Override
    public void onBindViewHolder(ShoppingCartHolder holder, int position) {
        ShoppingCart shoppingCart = mShoppingCartList.get(position);
        Log.d(LOG_TAG,"Processing: "+shoppingCart.getMenuName() + "------>" +Integer.toString(position));
        holder.mName.setText(mShoppingCartList.get(position).getMenuName());
        // We have to deal with a note or a note list here, so
        // check for both types and process accordingly
        holder.mNum.setText(mShoppingCartList.get(position).getMenuNum());
        holder.mTotalCost.setText(getCost(mShoppingCartList.get(position)));
        //holder.mCost.setText(mShoppingCartList.get(position).getMenuCost());


        Picasso.with(mContext).load(mShoppingCartList.get(position).getMenuPic())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.mImage);


    }


    @Override
    public int getItemCount() {
        return (null != mShoppingCartList ? mShoppingCartList.size() : 0);
    }
    public String getCost(ShoppingCart item){
        return Integer.toString(Integer.parseInt(item.getMenuNum()) * Integer.parseInt(item.getMenuCost()));
    }

    public String getTotalCost(){
        int cost, total,number;
        total = 0;
        for(ShoppingCart shoppingCart:mShoppingCartList){
            cost=Integer.parseInt(shoppingCart.getMenuCost());
            number = Integer.parseInt((shoppingCart.getMenuNum()));
            total=total+(cost*number);
        }
        return Integer.toString(total);
    }

    public void setData(List<ShoppingCart> notes) {
        this.mShoppingCartList = notes;
    }

    public void delete(int position) {
        //mFoodItems.remove(position);
        //notifyItemRemoved(position);
    }

    // This will be called when an image is obtained from
    // Google Drive or Dropbox, as it will happen at a timeframe
    // after the non image data has been changed.  So we need to
    // notify the recyclerview of a change of data
    /*public void notifyImageObtained() {
        notifyDataSetChanged();
    }*/

    // Simple nested class that holds the various view components for the adapter
    // and as specified in custom_notes_adapter_layout.xml which we just created
    public class ShoppingCartHolder extends RecyclerView.ViewHolder {

        TextView mName, mCost, mTotalCost,mNum;
        ImageView mImage;
        //LinearLayout mLinearLayout;

        public ShoppingCartHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.browse_name);
            //mCost = (TextView) itemView.findViewById(R.id.browse_cost);
            mImage = (ImageView) itemView.findViewById(R.id.thumbnail);
            mNum = (TextView) itemView.findViewById(R.id.browse_number);
            mTotalCost = (TextView) itemView.findViewById(R.id.browse_total_cost);
        }
    }
    public void loadNewData(List<ShoppingCart> mShoppingCartList) {
        this.mShoppingCartList = mShoppingCartList;
        notifyDataSetChanged();
    }
    public ShoppingCart getFoodItem(int position) {
        return (null != mShoppingCartList ? mShoppingCartList.get(position) : null);
    }
}
