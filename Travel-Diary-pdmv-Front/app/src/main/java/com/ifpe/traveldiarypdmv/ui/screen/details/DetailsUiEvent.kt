package com.ifpe.traveldiarypdmv.ui.screen.details

sealed class DetailsUiEvent {
    data class LoadDiary(val diaryId: String, val token: String) : DetailsUiEvent()
}
