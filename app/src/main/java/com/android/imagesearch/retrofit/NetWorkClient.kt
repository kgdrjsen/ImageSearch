package com.android.imagesearch.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.NetworkInterface

object NetWorkClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    private fun createOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }
    var gson = GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    private val imgRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(
            createOkHttpClient()).build()

    val imgNetWork : NetWorkInterface = imgRetrofit.create(NetWorkInterface::class.java)
}