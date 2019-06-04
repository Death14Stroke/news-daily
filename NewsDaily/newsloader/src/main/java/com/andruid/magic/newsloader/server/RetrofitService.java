package com.andruid.magic.newsloader.server;

import com.andruid.magic.newsloader.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/headlines")
    Call<ApiResponse> getHeadlines(@Query("country") String country, @Query("page") int page,
                                   @Query("page_size") int pageSize);
}