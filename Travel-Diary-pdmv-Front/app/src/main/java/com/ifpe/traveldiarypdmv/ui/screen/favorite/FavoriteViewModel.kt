package com.ifpe.traveldiarypdmv.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository

class FavoriteViewModel(private val repository: DiaryRepository) : ViewModel() {

    private val _favoriteDiaries = MutableStateFlow<List<Diary>>(emptyList())
    val favoriteDiaries: StateFlow<List<Diary>> = _favoriteDiaries

    fun loadFavoriteDiaries(userId: String) {
        viewModelScope.launch {
            try {
                val favorites = repository.getFavoriteDiaries(userId)

                println("IDS dos diários recebidos: $favorites")

                val favoriteDiaries = favorites.mapNotNull { diaryId ->
                    repository.getDiaryById(diaryId)
                }

                _favoriteDiaries.value = favoriteDiaries
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}