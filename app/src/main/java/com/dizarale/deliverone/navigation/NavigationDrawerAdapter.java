package com.dizarale.deliverone.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dizarale.deliverone.R;

import java.util.List;

/**
 * Created by s84021369 on 8/28/2015.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    private List<NavigationDrawerItem> mDrawerItems;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> drawerItems) {
        super();
        mDrawerItems = drawerItems;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        //Not used in Note App
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.custom_navigation_drawer,null);
        NavigationDrawerItem navigationDrawerItem = mDrawerItems.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.navigation_item_title);
        title.setText(navigationDrawerItem.getTitle());

        ImageView icon = (ImageView) convertView.findViewById(R.id.navigation_item_icon);
        icon.setImageResource(navigationDrawerItem.getIconId());
        icon.setColorFilter(mContext.getResources().getColor(R.color.md_white_1000));

        return convertView;
    }
}
