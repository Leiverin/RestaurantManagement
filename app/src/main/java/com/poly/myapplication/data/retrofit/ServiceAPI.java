package com.poly.myapplication.data.retrofit;

import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.models.Table;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceAPI {

    @GET("table/all/{floor}")
    Call<List<Table>> getTableByFloor(
            @Path("floor") int floor
    );

//    Call<List<Product>> getProductByCategory(
//
//    );
    @GET("product/all/1")
    Observable<List<Product>> getProductByCategory(

    );



}
