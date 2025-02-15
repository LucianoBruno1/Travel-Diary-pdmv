package com.ifpe.traveldiarypdmv.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.traveldiarypdmv.ui.component.button.TravelDiaryButton
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf<Boolean>(true) }
    var locationEnabled by remember { mutableStateOf<Boolean>(false) }
    var cameraEnabled by remember { mutableStateOf<Boolean>(false) }
    var darkModeEnabled by remember { mutableStateOf<Boolean>(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Configurações", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            SettingItem(
                title = "Notificações",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )

            SettingItem(
                title = "Permitir Geolocalização",
                checked = locationEnabled,
                onCheckedChange = { locationEnabled = it }
            )

            SettingItem(
                title = "Permitir Acesso à Câmera",
                checked = cameraEnabled,
                onCheckedChange = { cameraEnabled = it }
            )

            SettingItem(
                title = "Modo Escuro",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )
        }

        TravelDiaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Logout",
            containerColor = GreenBase,
            contentColor = Color.White,
            onClick = {}
        )
    }
}

@Composable
fun SettingItem(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
