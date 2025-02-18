package com.ifpe.traveldiarypdmv.ui.component.diary_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ifpe.traveldiarypdmv.data.model.Diary
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun DiaryCard(
    diary: Diary,
    isFavorited: Boolean = false,
    showFavorite: Boolean = false, // exibir ou não o favorito
    onClick: () -> Unit,
    onFavoriteToggle: ((Boolean) -> Unit)? = null //  pode ser opcional
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = diary.name,
                style = Typography.headlineMedium,
                color = Color(0xFF37474F) // Azul acinzentado
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "📍 ${diary.city}, ${diary.state}", color = Color.DarkGray)
            Text(text = "📅 ${diary.travel_date}", color = Color.Gray)

            if (showFavorite) { // Só exibe se for necessário
                Spacer(modifier = Modifier.height(8.dp))

                IconButton(
                    onClick = { onFavoriteToggle?.invoke(!isFavorited) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorito",
                        tint = if (isFavorited) GreenBase else Color.Gray
                    )
                }
            }
        }
    }
}
