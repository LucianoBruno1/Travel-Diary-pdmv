package com.ifpe.traveldiarypdmv.data.repository

import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.network.ApiService

class DiaryRepository(private val api: ApiService) {

    suspend fun getDiariesForUser(userId: String): List<Diary> {
        return api.getUserDiaries(userId)
    }
}


