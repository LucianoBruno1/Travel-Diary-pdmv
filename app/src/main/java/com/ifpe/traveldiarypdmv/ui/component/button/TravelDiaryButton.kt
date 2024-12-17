package com.ifpe.traveldiarypdmv.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.theme.Gray100
import com.ifpe.traveldiarypdmv.ui.theme.Gray600

@Composable
fun TravelDiaryButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit,
    containerColor: Color = GreenBase, // Adiciona um parâmetro para a cor do botão
    contentColor: Color = Color.White, // Adiciona um parâmetro para a cor do conteúdo (texto/ícone)
    enabled: Boolean = true // Parâmetro que controla se o botão está habilitado
) {
    Button(
        modifier = modifier.heightIn(min = 46.dp),
        shape = RoundedCornerShape(40.dp),
        contentPadding = if(text == null && iconRes != null) PaddingValues(0.dp) else ButtonDefaults.ContentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = onClick,
        enabled = enabled // Controle de habilitação do botão
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconRes?.let {
                Icon(painter = painterResource(id = iconRes), contentDescription = "Ícone do Botão")
            }
            text?.let { Text(text = text.replaceFirstChar { it.uppercase() }, style = Typography.labelLarge) }
        }
    }
}

@Preview
@Composable
private fun TravelDiaryButtonPreview() {
    TravelDiaryButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Acessar",
        containerColor = GreenBase,
        contentColor = Color.White,
        onClick = {}
    )
}

@Preview
@Composable
private fun TravelDiaryButtonGooglePreview() {
    TravelDiaryButton(
        modifier = Modifier.fillMaxWidth(),
        text = "Entrar com Google",
        iconRes = R.drawable.ic_google,
        containerColor = Gray100,
        contentColor = Color.Black,
        onClick = {}
    )
}