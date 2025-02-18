package com.ifpe.traveldiarypdmv.data.repository

import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.model.FavoriteDiaryResponse
import com.ifpe.traveldiarypdmv.data.model.FavoriteIds
import com.ifpe.traveldiarypdmv.data.network.ApiService

class DiaryRepository(private val api: ApiService) {

    suspend fun getDiariesForUser(userId: String): List<Diary> {
        return api.getUserDiaries(userId)
    }

    suspend fun getDiaryById(diaryId: String): Diary? {
        val response = api.getDiaryById(diaryId) // API retorna Response<Diary>

        if (response.isSuccessful) {
            return response.body() // Retorna o objeto Diary se a requisição for bem-sucedida
        } else {
            throw Exception("Erro ao buscar diário: ${response.errorBody()?.string()}") // Lança erro se falhar
        }
    }

    suspend fun toggleFavorite(diaryId: String, userId: String) {
        val ids = FavoriteIds(diaryId, userId)
        api.toggleFavorite(ids)
    }

    suspend fun deleteFavorite(diaryId: String, userId: String) {
        api.deleteFavorite(diaryId, userId)
    }

    suspend fun getFavoriteDiaries(userId: String): Set<String> {
        val favoriteDiaries: List<FavoriteDiaryResponse> = api.getFavoriteDiaries(userId)

        return favoriteDiaries.map { it.diary.id }.toSet()
    }

}


