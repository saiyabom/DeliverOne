package com.dizarale.deliverone.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.object.FoodItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by s84021369 on 7/5/2015.
 */
public class FoodItemRecyclerViewAdapter extends RecyclerView.Adapter<FoodItemImageViewHolder> {
    private List<FoodItem> mFoodItemsList;
    private Context mContext;
    private final String LOG_TAG = FoodItemRecyclerViewAdapter.class.getSimpleName();

    public FoodItemRecyclerViewAdapter(List<FoodItem> mFoodItemsList, Context mContext) {
        this.mFoodItemsList = mFoodItemsList;
        this.mContext = mContext;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public FoodItemImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row, parent, false);
        FoodItemImageViewHolder foodItemImageViewHolder = new FoodItemImageViewHolder(view);

        return foodItemImageViewHolder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(FoodItemImageViewHolder holder, int position) {
        FoodItem foodItem = mFoodItemsList.get(position);
        Log.v(LOG_TAG, foodItem.toString());
        //Log.d(LOG_TAG,"Processing: " + foodItem.getmName() + "--> " + Integer.toString(position));

        holder.foodName.setText(foodItem.getMenuName());
        holder.description.setText("cost: " + foodItem.getMenuCost());



        Picasso.with(mContext).load(foodItem.getMenuPic())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);



    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        Log.v(LOG_TAG,Integer.toString(mFoodItemsList.size()));
        return mFoodItemsList.size();

    }
    public void loadNewData(List<FoodItem> newPhotos) {
        mFoodItemsList = newPhotos;
        notifyDataSetChanged();
    }

    public FoodItem getFoodItem(int position){
        return (null != mFoodItemsList ? mFoodItemsList.get(position) : null);
    }
}
