package com.example.thread.API

import com.example.thread.Consts.Companion.BASE_URL
import com.example.thread.Model.Case
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("market/priceoverview")
    suspend fun getCase(
        @Query("market_hash_name") caseName: String ,
        @Query("appid") appid: Int = 730,
        @Query("currency") currency: Int = 1,
        @Query("format") json: String = "json",
        @Query("key") key: String = "4FA72FC92EE7FE357FCB288BF60DB128"
    ): Case


    companion object Factory {
        fun create(): ApiService {
            val gson = GsonBuilder()
                .create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()


            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}