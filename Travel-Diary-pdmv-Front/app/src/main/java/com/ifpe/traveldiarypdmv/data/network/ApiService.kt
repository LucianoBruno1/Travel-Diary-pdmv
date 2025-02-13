package com.ifpe.traveldiarypdmv.data.network

import com.ifpe.traveldiarypdmv.data.model.Diary
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("diaries/feed/{userId}")
    suspend fun getUserDiaries(@Path("userId") userId: String): List<Diary>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001/v1/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
