package com.ifpe.traveldiarypdmv.ui.component.text_input

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun TravelDiaryTextInput(value: String,
                         onValueChange: (String) -> Unit,
                         labelText: String,
                         leadingIconPainter: Painter,
                         leadingIconDescription: String,
                         modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp) // Espaçamento adicional
    ) {
        // Campo de texto
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = labelText, style = Typography.headlineSmall) },
            leadingIcon = {
                Icon(
                    painter = leadingIconPainter,
                    contentDescription = leadingIconDescription,
                    modifier = Modifier.size(26.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp), // Ajusta o espaçamento interno para a linha
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .height(1.dp)
        ) {
            drawLine(
                color = Color.Gray, // Cor da linha
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 2.dp.toPx() // Espessura da linha
            )
        }
    }
}