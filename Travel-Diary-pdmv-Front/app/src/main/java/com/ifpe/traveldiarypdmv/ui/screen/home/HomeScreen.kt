package com.ifpe.traveldiarypdmv.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit, // Função para simular o logout
    onNavigateToDetails: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bem-vindo à Home!",
            style = Typography.headlineLarge.copy(fontSize = 26.sp),
            modifier = Modifier.padding(bottom = 25.dp)
        )

        Text(
            text = "Você está logado com sucesso!",
            style = Typography.bodyMedium,
            modifier = Modifier.padding(bottom = 25.dp)
        )

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
