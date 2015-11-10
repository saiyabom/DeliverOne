package com.dizarale.deliverone.recyclerview;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dizarale.deliverone.R;


/**
 * Created by s84021369 on 7/5/2015.
 */
public class FoodItemImageViewHolder extends RecyclerView.ViewHolder{
    protected ImageView thumbnail;
    protected TextView foodName;
    protected TextView description;
    public FoodItemImageViewHolder(View itemView) {
        super(itemView);
        this.thumbnail = (ImageView) itemView.findViewById(R.id.imageView);
        this.foodName = (TextView) itemView.findViewById(R.id.food_name);
        this.description = (TextView) itemView.findViewById(R.id.description);
    }



}
