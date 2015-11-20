package com.dizarale.deliverone.config;

import com.dizarale.deliverone.object.FoodItem;
import com.dizarale.deliverone.object.Order;
import com.dizarale.deliverone.object.OrderStatusObject;
import com.dizarale.deliverone.object.ShoppingCart;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by s84021369 on 10/5/2015.
 */
public interface APIService {
    // list menu on app
    @GET("menu")
    Call<List<FoodItem>>
    loadListFoodItem(@Query("sub") String sub, @Query("type") String type);

    // ShoppingCart => Preorder: GET(listShoppingCart), POST(Sendtoorderfood)
    @GET("preorder")
    Call<List<ShoppingCart>>
    loadListShoppingCart(@Query("cus_tel") String phone);
    @POST("preorder")
    Call<String> postPreorder(
        /*@Query("cus_tel") String phone,
        @Query("menu_id") String menu_id,
        @Query("menu_num") String menu_num,
        @Query("menu_des") String menu_des*/
        @Body Map<String,String> params
    );
    @POST("preorder_del")
    Call<String> deletePreorder(
            @Query("cus_tel") String phone,
            @Query("menu_id") String menu_id
    );

    @POST("preorderdetaildistance")
    Call<String> confirmOrder(
            @Query("cus_tel") String phone
    );

    @GET("preorderdetail")
    Call<Order> loadOrderDetail(
            @Query("cus_tel") String phone
    );

    @GET("userorder")
    Call<List<OrderStatusObject>>
    loadUserOrder(@Query("cus_tel") String cus_tel);

}
