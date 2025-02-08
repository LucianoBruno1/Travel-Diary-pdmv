package com.ifpe.traveldiarypdmv.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.profile_header.ProfileHeader
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    userId: String,
    token: String
) {
    val uiState by viewModel.uiState.collectAsState()
    var biography by remember(uiState.bio) { mutableStateOf(uiState.bio ?: "Conte-nos sobre você...") }


    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileUiEvent.LoadProfile(userId, token))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(26.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Black.copy(alpha = 0.3f))
                .background(Color.White)
                .padding(20.dp)
        ) {
            ProfileHeader(profilePicture = uiState.profilePicture, name = uiState.name, birthDate = uiState.birthDate)
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            onClick = {
                Log.d("IconButton", "Botão de configurações clicado!")
            },
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Ícone de configuração do APP"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.height(50.dp))

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
