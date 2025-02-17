package com.ifpe.traveldiarypdmv.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.ui.component.profile_header.ProfileHeader
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    userId: String,
    token: String
) {
    val uiState by viewModel.uiState.collectAsState()
    var biography by remember(uiState.bio) { mutableStateOf(uiState.bio ?: "Conte-nos sobre você...") }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileUiEvent.LoadProfile(userId, token))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Cabeçalho com título e botão de configurações
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Perfil de Usuário", style = MaterialTheme.typography.headlineMedium)
            IconButton(
                onClick = { navController.navigate("settings") } // Garante a navegação correta
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Configurações")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(20.dp)
        ) {
            ProfileHeader(
                profilePicture = uiState.profilePicture,
                name = uiState.name,
                birthDate = uiState.birthDate
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(2.dp)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp)
        ) {
            OutlinedTextField(
                value = biography,
                onValueChange = { biography = it },
                label = { Text("Biografia") },
                placeholder = { Text("Escreva sua biografia aqui...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp)
                    .padding(top = 16.dp),
                maxLines = 10,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Salvar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {}
            )
        }
    }
}
