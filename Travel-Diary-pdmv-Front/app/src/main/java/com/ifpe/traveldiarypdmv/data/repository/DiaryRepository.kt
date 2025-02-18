package com.ifpe.traveldiarypdmv.data.repository

import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.model.DiaryManual
import com.ifpe.traveldiarypdmv.data.network.ApiService

class DiaryRepository(private val api: ApiService) {

    suspend fun getDiariesForUser(userId: String): List<Diary> {
        return api.getUserDiaries(userId)
    }

    suspend fun createDiary(diary: DiaryManual): retrofit2.Response<Unit> {
        return api.createDiary(diary)
    }
}



