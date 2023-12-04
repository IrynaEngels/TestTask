package com.ireneengels.myapplication.data

import com.ireneengels.myapplication.domain.response.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("test")
    suspend fun fetchPostsData(@Query("page") page: Int): PostResponse
}