package com.softgates.instadoctor.demoo.api;


import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("create_zoom.php")
    Call<ZoomResponse> getZoom();

         /*    @GET("index.php")
            Call<Response>(
            @Query("apiname") String searchText
           );*/





}
