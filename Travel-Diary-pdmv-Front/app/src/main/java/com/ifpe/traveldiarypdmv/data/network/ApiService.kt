package com.ifpe.traveldiarypdmv.data.network

import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.network.ResetPasswordRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("diaries/feed/{userId}")
    suspend fun getUserDiaries(@Path("userId") userId: String): List<Diary>

    @POST("reset_password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<Unit>

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
