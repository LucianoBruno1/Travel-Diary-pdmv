package com.ifpe.traveldiarypdmv.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: DiaryRepository) : ViewModel() {

    private val _diaries = MutableStateFlow<List<Diary>>(emptyList())
    val diaries: StateFlow<List<Diary>> = _diaries

    fun loadDiaries(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDiariesForUser(userId)
                _diaries.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
