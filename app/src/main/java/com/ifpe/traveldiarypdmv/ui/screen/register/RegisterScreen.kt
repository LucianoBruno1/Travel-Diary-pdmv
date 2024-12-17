package com.ifpe.traveldiarypdmv.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.text_input.TravelDiaryTextField
import com.ifpe.traveldiarypdmv.ui.theme.Gray100
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        // Imagem no topo
        Image(
            painter = painterResource(id = R.drawable.img_register_map),
            contentDescription = "Mapa ou imagem do topo",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
                .padding(bottom = 25.dp),
            contentScale = ContentScale.Crop
        )

        // Conteúdo principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "CADASTRE-SE",
                style = Typography.headlineLarge.copy(fontSize = 26.sp),
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .align(Alignment.Start)
            )

            TravelDiaryTextField(
                value = nome,
                onValueChange = { nome = it },
                labelText = "Digite seu nome completo",
                leadingIconPainter = painterResource(id = R.drawable.ic_person),
                leadingIconDescription = "Ícone de nome"
            )

            TravelDiaryTextField(
                value = email,
                onValueChange = { email = it },
                labelText = "Digite seu email",
                leadingIconPainter = painterResource(id = R.drawable.ic_email),
                leadingIconDescription = "Ícone de Email"
            )

            TravelDiaryTextField(
                value = password,
                onValueChange = { password = it },
                labelText = "Digite sua senha",
                leadingIconPainter = painterResource(id = R.drawable.ic_lock),
                leadingIconDescription = "Ícone de Senha"
            )

            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Cadastrar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {
                    //viewModel.onEvent(LoginUiEvent.OnLoginClicked(email, password))
                },
                //enabled = !uiState.isLoading
            )

//            if (uiState.isLoading) {
//                Spacer(modifier = Modifier.height(16.dp))
//                CircularProgressIndicator()
//            }

//            uiState.errorMessage?.let { error ->
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(text = "Erro: $error", color = Color.Red)
//            }

//            if (uiState.isLoggedIn) {
//                onNavigateToHome()
//            }

            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "ou", style = Typography.bodySmall)

            Spacer(modifier = Modifier.height(25.dp))

            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Entrar com Google",
                iconRes = R.drawable.ic_google,
                containerColor = Gray100,
                contentColor = Color.Black,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(
                    text = "Já possui uma conta?",
                    style = Typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    text = "Faça o login",
                    style = Typography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline,
                    )

                )
            }
        }
    }
}