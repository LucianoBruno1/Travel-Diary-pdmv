package com.ifpe.traveldiarypdmv.ui.screen.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import com.ifpe.traveldiarypdmv.core.util.LocationProvider
import java.io.File
import java.util.UUID

@Composable
fun CameraScreen(
    navController: NavController, // Adicionado NavController para navegação
    userId: String,
    token: String
) {
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }

    // Instancia o ViewModel manualmente
    val viewModel = UploadPhotoViewModel().apply {
        // Define os atributos manualmente
        this.travelDiaryRemoteDataSource = TravelDiaryRemoteDataSource
        this.locationProvider = LocationProvider(context)
    }

    val uiState by viewModel.uiState.collectAsState()

    // Gera o URI temporário para a foto
    val tempUri = remember {
        generateTempImageUri(context)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            uri = tempUri // Atualiza o URI após a foto ser tirada
        } else {
            Toast.makeText(context, "Erro ao capturar a foto.", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launcher.launch(tempUri)
        } else {
            Toast.makeText(context, "Permissão da câmera negada!", Toast.LENGTH_SHORT).show()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Verifica a permissão da câmera
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                launcher.launch(tempUri)
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        } else {
            Toast.makeText(context, "Permissão de localização negada!", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val cameraPermission = Manifest.permission.CAMERA
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

        if (ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, locationPermission) == PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(tempUri)
        } else {
            // Solicita a permissão de localização primeiro
            locationPermissionLauncher.launch(locationPermission)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uri != null) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Preview da Foto",
                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão "Salvar"
                Button(
                    onClick = {
                        viewModel.onEvent(UploadPhotoUiEvent.UploadPhoto(uri!!, userId, token, context))
                        navController.popBackStack() // Volta para a tela anterior após salvar
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Salvar")
                }

                // Botão "Cancelar"
                Button(
                    onClick = {
                        navController.popBackStack() // Volta para a tela anterior ao cancelar
                    }
                ) {
                    Text("Cancelar")
                }
            }
        }
    }

    if (uiState.errorMessage != null) {
        Text(
            text = uiState.errorMessage!!,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}

fun generateTempImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}