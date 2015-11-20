package com.dizarale.deliverone.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusObject {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("order_time")
    @Expose
    private String orderTime;
    @SerializedName("order_lat")
    @Expose
    private String orderLat;
    @SerializedName("order_long")
    @Expose
    private String orderLong;
    @SerializedName("order_local_detail")
    @Expose
    private String orderLocalDetail;
    @SerializedName("order_cost")
    @Expose
    private String orderCost;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("cus_id")
    @Expose
    private String cusId;
    @SerializedName("sub_res_id")
    @Expose
    private String subResId;
    @SerializedName("sub_res_user")
    @Expose
    private String subResUser;
    @SerializedName("cus_tel")
    @Expose
    private String cusTel;
    @SerializedName("cus_name")
    @Expose
    private String cusName;
    @SerializedName("sub_cost")
    @Expose
    private String subCost;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = new ArrayList<Menu>();

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @param orderDate
     * The order_date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     *
     * @return
     * The orderTime
     */
    public String getOrderTime() {
        return orderTime;
    }

    /**
     *
     * @param orderTime
     * The order_time
     */
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /**
     *
     * @return
     * The orderLat
     */
    public String getOrderLat() {
        return orderLat;
    }

    /**
     *
     * @param orderLat
     * The order_lat
     */
    public void setOrderLat(String orderLat) {
        this.orderLat = orderLat;
    }

    /**
     *
     * @return
     * The orderLong
     */
    public String getOrderLong() {
        return orderLong;
    }

    /**
     *
     * @param orderLong
     * The order_long
     */
    public void setOrderLong(String orderLong) {
        this.orderLong = orderLong;
    }

    /**
     *
     * @return
     * The orderLocalDetail
     */
    public String getOrderLocalDetail() {
        return orderLocalDetail;
    }

    /**
     *
     * @param orderLocalDetail
     * The order_local_detail
     */
    public void setOrderLocalDetail(String orderLocalDetail) {
        this.orderLocalDetail = orderLocalDetail;
    }

    /**
     *
     * @return
     * The orderCost
     */
    public String getOrderCost() {
        return orderCost;
    }

    /**
     *
     * @param orderCost
     * The order_cost
     */
    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    /**
     *
     * @return
     * The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     * The order_status
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return
     * The cusId
     */
    public String getCusId() {
        return cusId;
    }

    /**
     *
     * @param cusId
     * The cus_id
     */
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    /**
     *
     * @return
     * The subResId
     */
    public String getSubResId() {
        return subResId;
    }

    /**
     *
     * @param subResId
     * The sub_res_id
     */
    public void setSubResId(String subResId) {
        this.subResId = subResId;
    }

    /**
     *
     * @return
     * The subResUser
     */
    public String getSubResUser() {
        return subResUser;
    }

    /**
     *
     * @param subResUser
     * The sub_res_user
     */
    public void setSubResUser(String subResUser) {
        this.subResUser = subResUser;
    }

    /**
     *
     * @return
     * The cusTel
     */
    public String getCusTel() {
        return cusTel;
    }

    /**
     *
     * @param cusTel
     * The cus_tel
     */
    public void setCusTel(String cusTel) {
        this.cusTel = cusTel;
    }

    /**
     *
     * @return
     * The cusName
     */
    public String getCusName() {
        return cusName;
    }

    /**
     *
     * @param cusName
     * The cus_name
     */
    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    /**
     *
     * @return
     * The subCost
     */
    public String getSubCost() {
        return subCost;
    }

    /**
     *
     * @param subCost
     * The sub_cost
     */
    public void setSubCost(String subCost) {
        this.subCost = subCost;
    }

    /**
     *
     * @return
     * The menu
     */
    public List<Menu> getMenu() {
        return menu;
    }

    /**
     *
     * @param menu
     * The menu
     */
    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }


    public void initStatus(){
        switch (orderStatus){
            case "1": orderStatus="ยังไม่รับ"; break;
            case "2": orderStatus="กำลังปรุง"; break;
            case "3": orderStatus="กำลังส่ง"; break;
            case "5": orderStatus="ส่งเรียบร้อย"; break;
            case "8": orderStatus="ปฏิเสธ"; break;
            case "9": orderStatus="ไม่มีผู้รับ"; break;

        }
    }

    @Override
    public String toString() {
        return "OrderStatusObject{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderLat='" + orderLat + '\'' +
                ", orderLong='" + orderLong + '\'' +
                ", orderLocalDetail='" + orderLocalDetail + '\'' +
                ", orderCost='" + orderCost + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", cusId='" + cusId + '\'' +
                ", subResId='" + subResId + '\'' +
                ", subResUser='" + subResUser + '\'' +
                ", cusTel='" + cusTel + '\'' +
                ", cusName='" + cusName + '\'' +
                '}';
    }
}