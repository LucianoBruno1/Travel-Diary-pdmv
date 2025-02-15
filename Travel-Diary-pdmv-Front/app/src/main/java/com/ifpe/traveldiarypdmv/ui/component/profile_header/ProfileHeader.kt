package com.ifpe.traveldiarypdmv.ui.component.profile_header

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun ProfileHeader(profilePicture: String?, name: String, birthDate: String?) {
    Row(
        modifier = Modifier
            .background(Color.White)
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(150.dp))
                    .width(100.dp)
                    .heightIn(max = 100.dp)
                    .clip(RoundedCornerShape(50.dp))
            ) {
                val imagePainter = if (profilePicture.isNullOrEmpty()) {
                    painterResource(id = R.drawable.map_with_bags)
                } else {
                    painterResource(id = R.drawable.map_with_bags)
                }

                Image(
                    painter = imagePainter,
                    contentDescription = "Imagem de Perfil do Usuário",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .heightIn(max = 100.dp)
                        .clip(RoundedCornerShape(150.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            TravelDiaryButton(
                modifier = Modifier.width(100.dp).height(30.dp),
                text = "Carregar",
                containerColor = GreenBase,
                contentColor = Color.White,
                onClick = {},
                fontSize = 12f
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.padding(15.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        Log.d("IconButton", "Botão de configurações clicado!")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ícone de configuração do APP"
                    )
                }
            }
            Text(
                text = name,
                style = Typography.headlineLarge.copy(fontSize = 16.sp, color = Color.Black)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = "Nasc.:",
                    style = Typography.bodyMedium.copy(fontSize = 12.sp, color = Color.Black),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = birthDate ?: "Data de nascimento não informada",
                    style = Typography.bodySmall.copy(fontSize = 12.sp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileHeaderPreview() {
    ProfileHeader(null , "Eduardo", "22/01/1998")
}
