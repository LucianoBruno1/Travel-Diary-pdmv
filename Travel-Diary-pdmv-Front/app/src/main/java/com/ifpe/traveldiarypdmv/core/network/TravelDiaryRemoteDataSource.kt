package com.ifpe.traveldiarypdmv.core.network

import com.ifpe.traveldiarypdmv.data.model.LoginRequest
import com.ifpe.traveldiarypdmv.data.model.LoginResponse
import com.ifpe.traveldiarypdmv.core.network.KtorHttpClient.httpClientAndroid
import com.ifpe.traveldiarypdmv.data.model.MapPointResponse
import com.ifpe.traveldiarypdmv.data.model.RegisterRequest
import com.ifpe.traveldiarypdmv.data.model.RegisterResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.*
import org.json.JSONObject

object TravelDiaryRemoteDataSource {
    private const val LOCAL_HOST_EMULATOR_BASE_URL = "http://10.0.2.2:3001"
    private const val LOCAL_HOST_PHYSICAL_BASE_URL =
        "http://192.168.56.1:3001" // Ajuste para o endereço correto da API

    private const val BASE_URL = LOCAL_HOST_EMULATOR_BASE_URL

    /**
     * Envia as credenciais do usuário para a API de login e retorna o token de autenticação.
     */
    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = httpClientAndroid.post("$BASE_URL/v1/api/login") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(request) // Envia o corpo com os dados de login
            }

            if (response.status.value in 200..299) {
                val body = response.body<LoginResponse>()
                Result.success(body)
            } else {
                // Lê a resposta como texto para evitar problemas com serializers
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro desconhecido")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ocorreu um erro inesperado durante o registro: ${e.message}")) // Ajustar mensagem genérica
        }
    }


    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = httpClientAndroid.post("$BASE_URL/v1/api/register") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(request)
            }

            if (response.status.value in 200..299) {
                val body = response.body<RegisterResponse>()
                Result.success(body)
            } else {
                // Lê a resposta como texto para evitar problemas com serializers
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro desconhecido")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ocorreu um erro inesperado durante o registro: ${e.message}")) // Ajustar mensagem genérica
        }
    }

    suspend fun getMapPoints(userId: String): Result<List<MapPointResponse>> {
        return try {
            val response = httpClientAndroid.get("$BASE_URL/v1/api/points/$userId")
            if (response.status.value in 200..299) {
                val body: List<MapPointResponse> = response.body()
                Result.success(body)
            } else {
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro desconhecido")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao buscar pontos do mapa: ${e.message}"))
        }
    }

}