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

    private val _favoriteDiaries = MutableStateFlow<Set<String>>(emptySet())
    val favoriteDiaries: StateFlow<Set<String>> = _favoriteDiaries

    fun loadDiaries(userId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDiariesForUser(userId)
                println("Diários recebidos: ${result.map { it.id }}")
                _diaries.value = result

                // Carregar favoritos junto com os diários
                loadFavoriteDiaries(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadFavoriteDiaries(userId: String) {
        viewModelScope.launch {
            try {
                val favorites = repository.getFavoriteDiaries(userId)

                println("Favoritos recebidos: $favorites")

                // Extraindo corretamente os IDs dos diários favoritos
                _favoriteDiaries.value = favorites
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(diaryId: String, userId: String, isFavorited: Boolean) {
        viewModelScope.launch {
            try {
                if (isFavorited) {
                    repository.toggleFavorite(diaryId, userId)
                    _favoriteDiaries.value += diaryId // Adiciona o favorito
                } else {
                    repository.deleteFavorite(diaryId, userId)
                    _favoriteDiaries.value -= diaryId // Remove dos favoritos
                }

                // Atualiza o estado dos diários para refletir a mudança
                _diaries.value = _diaries.value.map { diary ->
                    if (diary.id == diaryId) diary.copy(isFavorited = !diary.isFavorited) else diary
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
