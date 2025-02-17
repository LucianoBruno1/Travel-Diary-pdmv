package com.ifpe.traveldiarypdmv.core.network


import com.ifpe.traveldiarypdmv.core.network.KtorHttpClient.httpClientAndroid
import com.ifpe.traveldiarypdmv.data.model.DiaryDetailsResponse
import com.ifpe.traveldiarypdmv.data.model.LoginRequest
import com.ifpe.traveldiarypdmv.data.model.LoginResponse
import com.ifpe.traveldiarypdmv.data.model.MapPointResponse
import com.ifpe.traveldiarypdmv.data.model.ProfileResponse
import com.ifpe.traveldiarypdmv.data.model.RegisterRequest
import com.ifpe.traveldiarypdmv.data.model.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.json.JSONObject
import io.ktor.utils.io.*
import android.net.Uri
import android.content.Context
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.*
import io.ktor.http.content.PartData
import java.io.File
import java.io.InputStream

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

    suspend fun getProfile(userId: String, token: String): Result<ProfileResponse> {
        return try {
            val response = httpClientAndroid.get("$BASE_URL/v1/api/profile/$userId") {
                contentType(io.ktor.http.ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $token")
                }
            }

            if (response.status.value in 200..299) {
                val body: ProfileResponse = response.body()
                Result.success(body)
            } else {
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro desconhecido")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao buscar perfil: ${e.message}"))
        }
    }

    suspend fun getDiaryDetails(diaryId: String, token: String): Result<DiaryDetailsResponse> {
        return try {
            val response = httpClientAndroid.get("$BASE_URL/v1/api/diaries/$diaryId") {
                contentType(io.ktor.http.ContentType.Application.Json)
                headers {
                    append("Authorization", "Bearer $token")
                }
            }

            if (response.status.value in 200..299) {
                val body: DiaryDetailsResponse = response.body()
                Result.success(body)
            } else {
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro desconhecido")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao buscar detalhes do diário: ${e.message}"))
        }
    }

    suspend fun uploadPhotos(userId: String, diaryId: String, photos: List<Uri>, context: Context): Result<Unit> {
        return try {
            val url = "$BASE_URL/v1/api/photos/uploadPhotoDiary/$userId"

            val response: HttpResponse = httpClientAndroid.submitFormWithBinaryData(
                url = url,
                formData = formData {
                    append("diary", diaryId)
                    photos.forEach { uri ->
                        val contentResolver = context.contentResolver
                        val mimeType = contentResolver.getType(uri) ?: "application/octet-stream" // Tipo padrão caso não seja encontrado

                        val inputStream: InputStream? = contentResolver.openInputStream(uri)
                        val bytes = inputStream?.readBytes() ?: byteArrayOf()
                        inputStream?.close()

                        append("photos", bytes, Headers.build {
                            append(HttpHeaders.ContentType, mimeType)
                            append(HttpHeaders.ContentDisposition, "filename=\"${uri.lastPathSegment}\"")
                        })
                    }
                }
            )

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao enviar fotos: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao enviar fotos: ${e.message}"))
        }
    }

    suspend fun deleteDiary(diaryId: String, token: String): Result<Unit> {
        return try {
            val response = httpClientAndroid.delete("$BASE_URL/v1/api/diaries/$diaryId") {
                headers {
                    append("Authorization", "Bearer $token")
                }
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBodyText = response.bodyAsText()
                val jsonObject = JSONObject(errorBodyText)
                val errorMessage = jsonObject.optString("message", "Erro ao deletar diário")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Erro ao deletar diário}"))
        }
    }

    suspend fun uploadPhoto(
        uri: Uri, userId: String, latitude: Double, longitude: Double, token: String, context: Context
    ): Result<Unit> {
        return try {
            val file = uriToFile(uri, context)// Converter URI para arquivo

            val response: HttpResponse = httpClientAndroid.submitFormWithBinaryData(
                url = "$BASE_URL/v1/api/photos/uploadPhoto/$userId",
                formData = formData {
                    append("photo", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=\"photo.jpg\"")
                    })
                    append("latitude", latitude.toString())
                    append("longitude", longitude.toString())
                }
            )
//            {
//                headers {
//                    append("Authorization", "Bearer $token") // Adiciona o token no cabeçalho
//                }
//            }
            if (response.status == HttpStatusCode.Created) {
                file.delete() // Exclui o arquivo temporário após o envio bem-sucedido
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao enviar foto: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw IllegalStateException("Erro ao abrir URI")
        val file = File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg") // Nome único para evitar conflitos

        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return file
    }

    suspend fun updateDiary(diaryId: String, token: String, name: String, description: String): Result<Unit> {
        return try {
            val response = httpClientAndroid.patch("$BASE_URL/v1/api/diaries/$diaryId") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "name" to name,
                        "description" to description
                    )
                )
            }

            if (response.status == HttpStatusCode.OK) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao atualizar diário"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
