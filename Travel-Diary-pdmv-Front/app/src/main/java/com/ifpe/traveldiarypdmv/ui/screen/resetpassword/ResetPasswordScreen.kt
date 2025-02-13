package com.ifpe.traveldiarypdmv.ui.screen.resetpassword

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.component.text_input.TravelDiaryTextField
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton

@Composable
fun ResetPasswordScreen(){
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.recover_password_img), contentDescription = "Imagem de recuperação de senha",
            modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp).padding(bottom = 25.dp),
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
                text = "REDEFINIR",
                style = Typography.headlineLarge.copy(fontSize = 26.sp),
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .align(Alignment.Start),
                fontWeight = FontWeight.Bold
            )
            TravelDiaryTextField(
                value = password.value,
                onValueChange = { password.value = it },
                labelText = "Nova senha",
                leadingIconPainter = painterResource(id = R.drawable.ic_lock),
                leadingIconDescription = "Ícone de Email"
            )
            TravelDiaryTextField(
                value = password.value,
                onValueChange = { password.value = it },
                labelText = "Confirmar senha",
                leadingIconPainter = painterResource(id = R.drawable.ic_lock),
                leadingIconDescription = "Ícone de senha"
            )
            TravelDiaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Recuperar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Retornar",
                textDecoration = TextDecoration.Underline
            )
        }
    }}
