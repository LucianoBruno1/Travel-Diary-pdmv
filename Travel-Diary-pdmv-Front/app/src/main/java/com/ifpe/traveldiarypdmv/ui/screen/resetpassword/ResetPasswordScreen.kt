package com.ifpe.traveldiarypdmv.ui.screen.resetpassword

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.data.network.ResetPasswordRequest
import com.ifpe.traveldiarypdmv.data.network.RetrofitClient.authService
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.component.text_input.TravelDiaryTextField
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ResetPasswordScreen(navController: NavController, email: String) {
    var token by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.recover_password_img),
            contentDescription = "Imagem de redefinição de senha",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 250.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "REDEFINIR SENHA",
            style = Typography.headlineLarge.copy(fontSize = 26.sp),
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Digite o código recebido por e-mail e a nova senha.",
            style = Typography.bodyLarge.copy(fontSize = 14.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //campo para inserir o token
        TravelDiaryTextField(
            value = token,
            onValueChange = { token = it },
            labelText = "Código de verificação",
            leadingIconPainter = painterResource(id = R.drawable.ic_lock),
            leadingIconDescription = "Ícone de chave"
        )

        Spacer(modifier = Modifier.height(12.dp))

        //campo para inserir a nova senha
        TravelDiaryTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            labelText = "Nova senha",
            leadingIconPainter = painterResource(id = R.drawable.ic_lock),
            leadingIconDescription = "Ícone de cadeado"
        )

        Spacer(modifier = Modifier.height(20.dp))

        TravelDiaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Alterar Senha",
            containerColor = GreenBase,
            contentColor = Color.White,
            onClick = {
                if (token.isNotEmpty() && newPassword.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = authService.resetPassword(ResetPasswordRequest(token, newPassword))

                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    val message = response.body()?.message ?: "Senha alterada com sucesso!"
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    navController.navigate("login")
                                } else {
                                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
                                    Toast.makeText(context, "Erro ao alterar senha: $errorBody", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Erro: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }} else {
                    Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}
