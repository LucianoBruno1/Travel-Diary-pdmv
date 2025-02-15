package com.ifpe.traveldiarypdmv.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Headers

data class ForgotPasswordRequest(val email: String)
data class ForgotPasswordResponse(val message: String)

data class ResetPasswordRequest(val token: String, val newPassword: String)
data class ResetPasswordResponse(val message: String)

interface AuthService {
    @POST("v1/api/forgot_password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @Headers("Content-Type: application/json")
    @POST("v1/api/reset_password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>
}
