package com.ifpe.traveldiarypdmv.ui.screen.home

import HomeViewModelFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.data.repository.DiaryRepository

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userId: String,
    repository: DiaryRepository,
    onLogout: () -> Unit,
    onNavigateToDetails: () -> Unit
) {

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val diaries by viewModel.diaries.collectAsState()

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
        Text(
            text = "Meus Diários",
            style = Typography.headlineLarge.copy(fontSize = 26.sp),
            modifier = Modifier.padding(16.dp)
        )

        // Lista de diários
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(diaries) { diary ->
                DiaryCard(diary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onLogout() }) {
            Text(text = "Logout", fontSize = 16.sp)
        }

        Button(
            onClick = { onNavigateToDetails() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Ir para Detalhes")
        }
    }
}

@Composable
fun DiaryCard(diary: Diary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = diary.name, style = Typography.headlineMedium)
            Text(text = "Local: ${diary.city}, ${diary.state}")
            Text(text = "Data: ${diary.travel_date}")
        }
    }
}
