package com.poly.myapplication.data.retrofit;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.models.Table;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAPI {

    @GET("table/all/{floor}")
    Observable<List<Table>> getTableByFloor(
            @Path("floor") int floor
    );

    @GET("products/all/{category}")
    Observable<List<Product>> getProductByCategory(
        @Path("category") int category
    );

    @POST("bill/create")
    Observable<List<Bill>> createBill(
        @Body Bill bill
    );

    @POST("/bill/{idTable}")
    Observable<Bill> getBillByTable(
        @Path("idTable") String idTable
    );


}
