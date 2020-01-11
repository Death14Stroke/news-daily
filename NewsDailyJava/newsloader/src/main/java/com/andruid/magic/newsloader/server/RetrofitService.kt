package com.andruid.magic.newsloader.server

import com.andruid.magic.newsloader.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("/headlines")
    fun getHeadlines(@Query("country") country: String, @Query("category") category: String,
                     @Query("page") page: Int, @Query("page_size") pageSize: Int
    ): Call<ApiResponse>

    @GET("/articles")
    fun getArticles(@Query("language") language: String, @Query("query") query: String,
                    @Query("page") page: Int, @Query("page_size") pageSize: Int
    ): Call<ApiResponse>
}