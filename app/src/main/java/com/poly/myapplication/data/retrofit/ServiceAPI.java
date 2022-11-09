package com.poly.myapplication.data.retrofit;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAPI {

    @GET("table/all/{floor}")
    Call<List<Table>> getTableByFloor(
            @Path("floor") int floor
    );

    @GET("bill/all")
    Call<List<Bill>> getBill();

    @PUT("bill/update/{id}")
    Call<List<Bill>> doneBill(
            @Path("id") String id,
            @Query("_method") String method
    );

    @GET("bill/all/{status}")
    Call<List<Bill>> getTypeBill(@Path("status") int status);
}
