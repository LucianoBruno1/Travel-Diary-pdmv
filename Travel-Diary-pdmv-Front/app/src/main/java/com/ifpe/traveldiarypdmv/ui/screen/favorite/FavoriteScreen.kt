package com.ifpe.traveldiarypdmv.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.component.diary_card.DiaryCard
import com.ifpe.traveldiarypdmv.ui.component.screen_header.ScreenHeader
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    userId: String,
    repository: DiaryRepository,
    onNavigateToDetails: (String) -> Unit,
) {
    val viewModel: FavoriteViewModel = viewModel(factory = FavoriteViewModelFactory(repository))
    val favoriteDiaries by viewModel.favoriteDiaries.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadFavoriteDiaries(userId)
    }

    Column(
        modifier = modifier.fillMaxSize().background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ScreenHeader("Diários Favoritos")

        if (favoriteDiaries.isEmpty()) {
            Text(
                text = "Nenhum diário favorito encontrado.",
                style = Typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(favoriteDiaries) { favoriteDiary ->
                    DiaryCard(
                        diary = favoriteDiary,
                        showFavorite = false,
                        onClick = { onNavigateToDetails(favoriteDiary.id) }
                    )
                }
            }
        }
    }
}


