package com.ifpe.traveldiarypdmv.data.network

import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.model.FavoriteDiaryResponse
import com.ifpe.traveldiarypdmv.data.model.FavoriteIds
import com.ifpe.traveldiarypdmv.data.network.ResetPasswordRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("diaries/feed/{userId}")
    suspend fun getUserDiaries(@Path("userId") userId: String): List<Diary>

    @GET("getfavorites/{diaryId}")
    suspend fun getDiaryById(@Path("diaryId") diaryId: String): Response<Diary>

    @POST("reset_password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<Unit>

    @POST("favorites")
    suspend fun toggleFavorite(@Body ids: FavoriteIds): Response<Unit>

    @GET("favorites/{userId}")
    suspend fun getFavoriteDiaries(@Path("userId") userId: String): List<FavoriteDiaryResponse>

    @DELETE("favorites/{diaryId}/{userId}")
    suspend fun deleteFavorite(@Path("diaryId") diaryId: String, @Path("userId") userId: String): Response<Unit>

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
