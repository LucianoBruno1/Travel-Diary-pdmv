package com.ifpe.traveldiarypdmv.ui.screen.home

import HomeViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository
import com.ifpe.traveldiarypdmv.ui.component.screen_header.ScreenHeader
import com.ifpe.traveldiarypdmv.ui.route.CreateDiary

@Composable
fun DiaryCard(diary: Diary, onClick: () -> Unit) {
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
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId: String,
    repository: DiaryRepository,
    onLogout: () -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onCreateDiaryClick: () -> Unit // ✅ Adicionado
) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val diaries by viewModel.diaries.collectAsState()

    // Carregar os diários quando a tela for iniciada
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
                    DiaryCard(diary, onClick = { onNavigateToDetails(diary.id) })
                }
            }
        }
        LaunchedEffect(Unit) {
            viewModel.loadDiaries(userId)
        }


        // Botão flutuante de adicionar diário
        FloatingActionButton(
            onClick = {
                navController.navigate("createDiary/$userId") // ✅ Passando userId na rota
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
