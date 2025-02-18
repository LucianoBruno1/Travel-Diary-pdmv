package com.ifpe.traveldiarypdmv.ui.screen.home

import HomeViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.component.diary_card.DiaryCard
import com.ifpe.traveldiarypdmv.ui.component.screen_header.ScreenHeader

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId: String,
    repository: DiaryRepository,
    onLogout: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onCreateDiaryClick: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val diaries by viewModel.diaries.collectAsState()
    val favoriteDiaries by viewModel.favoriteDiaries.collectAsState()


    //carregar os diários quando a tela for iniciada
    LaunchedEffect(userId) {
        viewModel.loadDiaries(userId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader("Meus Diários")

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(diaries) { diary ->
                    val context = LocalContext.current
                    DiaryCard(
                        diary = diary,
                        isFavorited = favoriteDiaries.contains(diary.id),
                        showFavorite = true,
                        onClick = {
                            onNavigateToDetails(diary.id)
                        },
                        onFavoriteToggle = { isFavorited ->
                            viewModel.toggleFavorite(diary.id, userId, isFavorited)
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("createDiary/$userId")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Text("+", color = Color.White, fontSize = 24.sp)
        }
    }
}

