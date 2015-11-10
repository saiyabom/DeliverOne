package com.dizarale.deliverone.object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("sub_cost")
    @Expose
    private int subCost;
    @SerializedName("totle_cost")
    @Expose
    private int totleCost;

    /**
     * No args constructor for use in serialization
     *
     */
    public Order() {
    }

    /**
     *
     * @param totleCost
     * @param subCost
     */
    public Order(int subCost, int totleCost) {
        this.subCost = subCost;
        this.totleCost = totleCost;
    }

    /**
     *
     * @return
     * The subCost
     */
    public int getSubCost() {
        return subCost;
    }

    /**
     *
     * @param subCost
     * The sub_cost
     */
    public void setSubCost(int subCost) {
        this.subCost = subCost;
    }

    /**
     *
     * @return
     * The totleCost
     */
    public int getTotleCost() {
        return totleCost;
    }

    /**
     *
     * @param totleCost
     * The totle_cost
     */
    public void setTotleCost(int totleCost) {
        this.totleCost = totleCost;
    }

}