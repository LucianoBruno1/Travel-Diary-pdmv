package com.ifpe.traveldiarypdmv.ui.screen.recoverpassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.data.network.ForgotPasswordRequest
import com.ifpe.traveldiarypdmv.data.network.RetrofitClient
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.text_input.TravelDiaryTextField
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun RecoverPasswordScreen(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.recover_password_img),
            contentDescription = "Imagem de recuperação de senha",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
                .padding(bottom = 25.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "RECUPERAR SENHA",
                style = Typography.headlineLarge.copy(fontSize = 26.sp),
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .align(Alignment.Start),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Digite seu email e um código será enviado para que você possa redefinir sua senha",
                style = Typography.headlineLarge.copy(fontSize = 14.sp),
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .align(Alignment.Start),
            )
            TravelDiaryTextField(
                value = email.value,
                onValueChange = { email.value = it },
                labelText = "Digite seu email",
                leadingIconPainter = painterResource(id = R.drawable.ic_email),
                leadingIconDescription = "Ícone de Email"
            )
            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Recuperar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {
                    if (email.value.isNotEmpty()) {
                        coroutineScope.launch {
                            try {
                                val response = RetrofitClient.authService.forgotPassword(
                                    ForgotPasswordRequest(email.value)
                                )

                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Email enviado com sucesso!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("resetpassword/${email.value}")
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Erro ao enviar email: ${response.message()}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Erro de conexão: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Digite um email válido!", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Retornar",
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
