package com.example.plantina;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiClient
{
    String BASE_URL = "https://plantina.herokuapp.com/";

    @POST("predict")
    Call<ResultData> getresult(@Body RequestData requestData);
}
