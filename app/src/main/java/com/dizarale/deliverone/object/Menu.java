package com.dizarale.deliverone.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_description")
    @Expose
    private String menuDescription;
    @SerializedName("menu_num")
    @Expose
    private String menuNum;
    @SerializedName("menu_status")
    @Expose
    private String menuStatus;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("menu_cost")
    @Expose
    private String menuCost;

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
     * The menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     *
     * @param menuId
     * The menu_id
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     *
     * @return
     * The menuDescription
     */
    public String getMenuDescription() {
        return menuDescription;
    }

    /**
     *
     * @param menuDescription
     * The menu_description
     */
    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    /**
     *
     * @return
     * The menuNum
     */
    public String getMenuNum() {
        return menuNum;
    }

    /**
     *
     * @param menuNum
     * The menu_num
     */
    public void setMenuNum(String menuNum) {
        this.menuNum = menuNum;
    }

    /**
     *
     * @return
     * The menuStatus
     */
    public String getMenuStatus() {
        return menuStatus;
    }

    /**
     *
     * @param menuStatus
     * The menu_status
     */
    public void setMenuStatus(String menuStatus) {
        this.menuStatus = menuStatus;
    }

    /**
     *
     * @return
     * The menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     *
     * @param menuName
     * The menu_name
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     *
     * @return
     * The menuCost
     */
    public String getMenuCost() {
        return menuCost;
    }

    /**
     *
     * @param menuCost
     * The menu_cost
     */
    public void setMenuCost(String menuCost) {
        this.menuCost = menuCost;
    }

}