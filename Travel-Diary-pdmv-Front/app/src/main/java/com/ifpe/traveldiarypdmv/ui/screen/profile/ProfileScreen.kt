package com.ifpe.traveldiarypdmv.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.profile_header.ProfileHeader
import com.ifpe.traveldiarypdmv.ui.component.screen_header.ScreenHeader
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    userId: String,
    token: String,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    var biography by remember { mutableStateOf(uiState.bio ?: "Conte-nos sobre você...") }

    // Carregar perfil quando userId ou token mudar
    LaunchedEffect(userId, token) {
        viewModel.onEvent(ProfileUiEvent.LoadProfile(userId, token))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ScreenHeader("Perfil de Usuário")
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Black.copy(alpha = 0.3f))
                .background(Color.White)
                .padding(20.dp)
        ) {
            ProfileHeader(
                profilePicture = uiState.profilePicture,
                name = uiState.name,
                birthDate = uiState.birthDate,
                onSettingsClick = { navController.navigate("settings") } // Navegar para configurações
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Linha divisória
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
                singleLine = false,
            )

            Spacer(modifier = Modifier.height(20.dp))

            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Salvar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {

                }
            )
        }
    }
}
