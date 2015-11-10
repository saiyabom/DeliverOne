package com.dizarale.deliverone.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodItem implements Serializable{
    private static final long serialVersionUID = 1L;
    @SerializedName("sub_res_id")
    @Expose
    private String subResId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_type")
    @Expose
    private String menuType;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("menu_name_thai")
    @Expose
    private String menuNameThai;
    @SerializedName("menu_detail")
    @Expose
    private String menuDetail;
    @SerializedName("menu_cost")
    @Expose
    private String menuCost;
    @SerializedName("menu_pic")
    @Expose
    private String menuPic;
    @SerializedName("menu_QR")
    @Expose
    private String menuQR;
    @SerializedName("menu_time")
    @Expose
    private String menuTime;

    /**
     * No args constructor for use in serialization
     *
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public FoodItem() {
    }

    /**
     *
     * @param menuQR
     * @param menuPic
     * @param menuNameThai
     * @param menuType
     * @param menuId
     * @param menuDetail
     * @param menuTime
     * @param menuCost
     * @param menuName
     * @param subResId
     */
    public FoodItem(String subResId, String menuId, String menuType, String menuName, String menuNameThai, String menuDetail, String menuCost, String menuPic, String menuQR, String menuTime) {
        this.subResId = subResId;
        this.menuId = menuId;
        this.menuType = menuType;
        this.menuName = menuName;
        this.menuNameThai = menuNameThai;
        this.menuDetail = menuDetail;
        this.menuCost = menuCost;
        this.menuPic = menuPic;
        this.menuQR = menuQR;
        this.menuTime = menuTime;
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
     * The menuType
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     *
     * @param menuType
     * The menu_type
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
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
     * The menuNameThai
     */
    public String getMenuNameThai() {
        return menuNameThai;
    }

    /**
     *
     * @param menuNameThai
     * The menu_name_thai
     */
    public void setMenuNameThai(String menuNameThai) {
        this.menuNameThai = menuNameThai;
    }

    /**
     *
     * @return
     * The menuDetail
     */
    public String getMenuDetail() {
        return menuDetail;
    }

    /**
     *
     * @param menuDetail
     * The menu_detail
     */
    public void setMenuDetail(String menuDetail) {
        this.menuDetail = menuDetail;
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

    /**
     *
     * @return
     * The menuPic
     */
    public String getMenuPic() {
        return menuPic;
    }

    /**
     *
     * @param menuPic
     * The menu_pic
     */
    public void setMenuPic(String menuPic) {
        this.menuPic = menuPic;
    }

    /**
     *
     * @return
     * The menuQR
     */
    public String getMenuQR() {
        return menuQR;
    }

    /**
     *
     * @param menuQR
     * The menu_QR
     */
    public void setMenuQR(String menuQR) {
        this.menuQR = menuQR;
    }

    /**
     *
     * @return
     * The menuTime
     */
    public String getMenuTime() {
        return menuTime;
    }

    /**
     *
     * @param menuTime
     * The menu_time
     */
    public void setMenuTime(String menuTime) {
        this.menuTime = menuTime;
    }

}
