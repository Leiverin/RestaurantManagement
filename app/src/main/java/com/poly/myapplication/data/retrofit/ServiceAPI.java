package com.poly.myapplication.data.retrofit;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.MyResponse;
import com.poly.myapplication.data.models.NotificationSender;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.models.BodyDate;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @GET("bill/all")
    Call<List<Bill>> getBill();

    @PUT("bill/update/{id}")
    Call<List<Bill>> doneBill(
            @Path("id") String id,
            @Query("_method") String method,
            @Body Bill bill
    );

    @GET("bill/all/{status}")
    Call<List<Bill>> getTypeBill(@Path("status") int status);

    @POST("bill/filter/{idTable}")
    Call<List<Bill>> getBillByDate(
            @Path("idTable") String idTable,
            @Body BodyDate bodyDate
    );
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=xxxxxxxxxxxxxxxxxxx" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
