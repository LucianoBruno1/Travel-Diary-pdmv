package com.ifpe.traveldiarypdmv.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
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
                    FavoriteDiaryCard(
                        diary = favoriteDiary,
                        onClick = { onNavigateToDetails(favoriteDiary.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteDiaryCard(
    diary: Diary,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = diary.name,
                style = Typography.headlineMedium,
                color = Color(0xFF37474F) // Azul acinzentado
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "📍 ${diary.city}, ${diary.state}", color = Color.DarkGray)
            Text(text = "📅 ${diary.travel_date}", color = Color.Gray)
        }
    }
}
