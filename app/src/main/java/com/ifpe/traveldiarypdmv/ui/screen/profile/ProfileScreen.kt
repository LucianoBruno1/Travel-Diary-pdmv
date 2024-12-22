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
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.profile_header.ProfileHeader
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(26.dp),
    ) {
        // Box para o ProfileHeader com sombra
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Black.copy(alpha = 0.3f)) // Sombra
                .background(Color.White) // Fundo branco
                .padding(20.dp) // Adiciona padding dentro da Box
        ) {
            // Cabeçalho do Perfil
            ProfileHeader()

        }

        // Posicionando o IconButton no canto superior direito
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd) // Alinha o botão no canto superior direito
                .padding(16.dp), // Adiciona um pouco de padding ao redor para não ficar tão colado na borda
            onClick = {
                // Ação quando o ícone de configuração for clicado
                Log.d("IconButton", "Botão de configurações clicado!")
            },
        ) {
            Icon(
                imageVector = Icons.Default.Settings, // Ícone de configuração
                contentDescription = "Ícone de configuração do APP" // Descrição acessível
            )
        }

        // Usando Column para empilhar os elementos abaixo do ProfileHeader
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp) // Ajuste o top padding para dar espaçamento suficiente para o conteúdo
        ) {
            // Adiciona mais conteúdo abaixo do cabeçalho (ProfileHeader)
            // Aqui você pode adicionar os outros componentes, como a linha horizontal

            // Exemplo de um botão ou outro conteúdo
            Spacer(modifier = Modifier.height(50.dp))

            // Linha horizontal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)  // Define a espessura da linha
                    .background(Color.Black)  // Define a cor da linha
            )

            Spacer(modifier = Modifier.height(50.dp))
            // Biografia - Usando o componente TextField padrão
            var biography by remember { mutableStateOf("Conte-nos sobre você...") }

            OutlinedTextField(
                value = biography,
                onValueChange = { biography = it },
                label = { Text("Biografia") },
                placeholder = { Text("Escreva sua biografia aqui...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp)  // Define um tamanho mínimo para o campo
                    .padding(top = 16.dp),
                maxLines = 10,  // Permite que o campo tenha até 5 linhas
                singleLine = false,  // Permite múltiplas linhas
            )
            Spacer(modifier = Modifier.height(20.dp))
            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Salvar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {
                    //viewModel.onEvent(LoginUiEvent.OnLoginClicked(email, password))
                }
            )
        }
    }
}


@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}