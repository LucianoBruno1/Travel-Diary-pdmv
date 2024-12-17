package com.ifpe.traveldiarypdmv.core.network

import com.ifpe.traveldiarypdmv.data.model.LoginRequest
import com.ifpe.traveldiarypdmv.data.model.LoginResponse
import com.ifpe.traveldiarypdmv.core.network.KtorHttpClient.httpClientAndroid
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType

object TravelDiaryRemoteDataSource {
    private const val LOCAL_HOST_EMULATOR_BASE_URL = "http://10.0.2.2:3001"
    private const val LOCAL_HOST_PHYSICAL_BASE_URL = "http://192.168.56.1:3001" // Ajuste para o endereço correto da API

    private const val BASE_URL = LOCAL_HOST_EMULATOR_BASE_URL
    /**
     * Envia as credenciais do usuário para a API de login e retorna o token de autenticação.
     */
    suspend fun login(request: LoginRequest): Result<LoginResponse> = try {
        val response = httpClientAndroid.post("$BASE_URL/user/login") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(request) // Envia o corpo com os dados de login
        }.body<LoginResponse>()

        Result.success(response)
    } catch (e: Exception) {
        Result.failure(e)
    }
}