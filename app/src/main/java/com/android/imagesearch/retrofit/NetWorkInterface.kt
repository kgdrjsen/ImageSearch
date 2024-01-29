package com.android.imagesearch.retrofit

import com.android.imagesearch.data.Image
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("/v2/search/image")
    suspend fun getImg(@Header("Authorization")Authorization: String,
                       @Query("query")search : String) : Image
}