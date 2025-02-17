package com.ifpe.traveldiarypdmv.ui.screen.details

import android.content.Context
import android.net.Uri

sealed class DetailsUiEvent {
    data class LoadDiary(val diaryId: String, val token: String) : DetailsUiEvent()
    data class UploadPhotos(val userId: String, val diaryId: String, val photos: List<Uri>, val context: Context) : DetailsUiEvent()
    data class DeleteDiary(val diaryId: String, val token: String) : DetailsUiEvent()
    data class UpdateDiary(val diaryId: String, val token: String, val name: String, val description: String) : DetailsUiEvent()
}
