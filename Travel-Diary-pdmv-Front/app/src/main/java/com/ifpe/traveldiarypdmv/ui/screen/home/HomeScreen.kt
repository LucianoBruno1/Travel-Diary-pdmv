package com.ifpe.traveldiarypdmv.ui.screen.home

import HomeViewModelFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.component.screen_header.ScreenHeader
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userId: String,
    repository: DiaryRepository,
    onLogout: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
) {

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val diaries by viewModel.diaries.collectAsState()
    val favoriteDiaries by viewModel.favoriteDiaries.collectAsState()


    // Carregar os diários quando a tela for iniciada
    LaunchedEffect(userId) {
        viewModel.loadDiaries(userId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ScreenHeader("Meus Diários")
//        Text(
//            text = "Meus Diários",
//            style = Typography.headlineLarge.copy(fontSize = 26.sp),
//            modifier = Modifier.padding(16.dp)
//        )

        // Lista de diários
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(diaries) { diary ->
                val context = LocalContext.current
                DiaryCard(
                    diary = diary,
                    isFavorited = favoriteDiaries.contains(diary.id),
                    onClick = {
                        println("Clicado no diário: ${diary.id}")
                        onNavigateToDetails(diary.id)
                    },
                    onFavoriteToggle = { isFavorited ->
                        println("Favoritando diário: ${diary.id}")
                        viewModel.toggleFavorite(diary.id, userId, isFavorited)
                    }

                )
            }
        }
    }
}

@Composable
fun DiaryCard(
    diary: Diary,
    isFavorited: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFavoriteToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = diary.name, style = Typography.headlineMedium)
            Text(text = "Local: ${diary.city}, ${diary.state}")
            Text(text = "Data: ${diary.travel_date}")

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = {
                    println("Clicado no diário: ${diary.id}")
                    onFavoriteToggle(!isFavorited)
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorito",
                    tint = if (isFavorited) GreenBase else Color.Gray
                )
            }
        }
    }
}