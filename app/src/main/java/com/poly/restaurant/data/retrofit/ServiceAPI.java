package com.poly.restaurant.data.retrofit;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.BodyDate;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.models.Table;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAPI {

    // Get table by floor
    @GET("table/all/{floor}")
    Observable<List<Table>> getTableByFloor(
            @Path("floor") int floor
    );

    // Get list product by category
    @GET("products/all/{category}")
    Observable<List<Product>> getProductByCategory(
            @Path("category") int category
    );

    // Create a bill
    @POST("bill/create")
    Observable<Bill> createBill(
            @Body Bill bill
    );

    // Get list bill by table
    @GET("bill/{idTable}")
    Observable<Bill> getBillByTable(
            @Path("idTable") String idTable
    );

    // Get list product in bill
    @GET("bill/{idTable}/product")
    Observable<List<Product>> getListProductInBill(
            @Path("idTable") String idTable
    );

    // Check bill exist
    @GET("bill/{idTable}/check")
    Observable<List<Bill>> getBillIfExists(
            @Path("idTable") String idTable
    );

    // Check bill exist
    @PUT("bill/update/{id}")
    Observable<Bill> updateBillById(
            @Path("id") String id,
            @Body Bill bill,
            @Query("_method") String method
    );

    // login
    @FormUrlEncoded
    @POST("staff/login")
    Observable<Staff> login(
            @Field("account") String account,
            @Field("password") String password
    );

    // update status in bill
    @PUT("bill/update/{id}")
    Call<List<Bill>> doneBill(
            @Path("id") String id,
            @Query("_method") String method,
            @Body Bill bill
    );

    // get List Bill in status
    @GET("bill/all/{status}")
    Call<List<Bill>> getTypeBill(@Path("status") int status);

    // filter history in firstDate and secondDate
    @POST("bill/filter/{idTable}")
    Call<List<Bill>> getBillByDate(
            @Path("idTable") String idTable,
            @Body BodyDate bodyDate
    );


    // change Password staff
    @PUT("staff/update/{id}")
    Call<List<Staff>> changePassword(
            @Path("id") String id,
            @Query("_method") String method,
            @Body Staff staff
    );

    // push notification
}
