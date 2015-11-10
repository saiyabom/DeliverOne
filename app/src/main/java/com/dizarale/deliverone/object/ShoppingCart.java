package com.dizarale.deliverone.object;

/**
 * Created by s84021369 on 9/23/2015.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ShoppingCart {

    @SerializedName("cus_id")
    @Expose
    private String cusId;
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("menu_num")
    @Expose
    private String menuNum;
    @SerializedName("menu_description")
    @Expose
    private String menuDescription;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("menu_type")
    @Expose
    private String menuType;
    @SerializedName("menu_name_thai")
    @Expose
    private String menuNameThai;
    @SerializedName("menu_cost")
    @Expose
    private String menuCost;
    @SerializedName("menu_pic")
    @Expose
    private String menuPic;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShoppingCart() {
    }

    /**
     *
     * @param menuNum
     * @param menuPic
     * @param menuNameThai
     * @param menuType
     * @param menuId
     * @param menuDescription
     * @param cusId
     * @param menuCost
     * @param menuName
     */
    public ShoppingCart(String cusId, String menuId, String menuNum, String menuDescription, String menuName, String menuType, String menuNameThai, String menuCost, String menuPic) {
        this.cusId = cusId;
        this.menuId = menuId;
        this.menuNum = menuNum;
        this.menuDescription = menuDescription;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuNameThai = menuNameThai;
        this.menuCost = menuCost;
        this.menuPic = menuPic;
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

}