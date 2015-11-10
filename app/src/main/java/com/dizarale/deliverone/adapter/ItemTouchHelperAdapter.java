package com.dizarale.deliverone.adapter;

/**
 * Created by s84021369 on 9/23/2015.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
