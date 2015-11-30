package com.dizarale.deliverone.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.object.Menu;
import com.dizarale.deliverone.object.OrderStatusObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by s84021369 on 11/19/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private List<OrderStatusObject> userOrder;



    public ExpandableListAdapter(Context context, List<OrderStatusObject> userOrder) {
        this._context = context;
        this.userOrder = userOrder;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.userOrder.get(groupPosition).getMenu().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Menu menu = this.userOrder.get(groupPosition).getMenu().get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        TextView costMenu = (TextView) convertView
                .findViewById(R.id.cost_menu);

        txtListChild.setText(menu.getMenuName());
        if(menu.getMenuName().contains("delivery")){
            costMenu.setText(menu.getMenuCost()+"B");
        }else {
            costMenu.setText(menu.getMenuCost()+"B x "+menu.getMenuNum());
            costMenu.setTextColor(_context.getResources().getColor(R.color.material_blue_grey_950));
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.userOrder.get(groupPosition).getMenu().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.userOrder.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.userOrder.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        OrderStatusObject item = this.userOrder.get(groupPosition);
        String headerTitle = this.userOrder.get(groupPosition).getOrderDate();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        TextView time = (TextView) convertView
                .findViewById(R.id.date_time);
        TextView costOrder = (TextView) convertView
                .findViewById(R.id.cost_order);
        TextView status = (TextView) convertView
                .findViewById(R.id.status_order);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(item.getOrderDate());
        time.setText(item.getOrderTime());
        costOrder.setText(item.getOrderCost() + "B");
        item.initStatus();
        status.setText(item.getOrderStatus());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
