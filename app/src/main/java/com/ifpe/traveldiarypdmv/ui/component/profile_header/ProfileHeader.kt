package com.ifpe.traveldiarypdmv.ui.component.profile_header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
fun ProfileHeader() {
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
                Image(
                    painter = painterResource(id = R.drawable.map_with_bags),
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
                onClick = {
                    //viewModel.onEvent(LoginUiEvent.OnLoginClicked(email, password))
                },
                fontSize = 12f
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.padding(15.dp)) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Adonai Ermínio",
                style = Typography.headlineLarge.copy(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Text(
                    text = "Nasc.:",
                    style = Typography.bodyMedium.copy(fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "XX/XX/XXXX",
                    style = Typography.bodySmall.copy(fontSize = 12.sp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileHeaderPreview() {
    ProfileHeader()
}