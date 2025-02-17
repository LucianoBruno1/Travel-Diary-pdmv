package com.ifpe.traveldiarypdmv.ui.screen.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    var showPermissionDialog by remember { mutableStateOf(false) }


    val viewModel = UploadPhotoViewModel().apply {
        this.travelDiaryRemoteDataSource = TravelDiaryRemoteDataSource
        this.locationProvider = LocationProvider(context)
    }

    val uiState by viewModel.uiState.collectAsState()

    val tempUri = remember {
        generateTempImageUri(context)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            uri = tempUri
        } else {
            Toast.makeText(context, "Erro ao capturar a foto.", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launcher.launch(tempUri)
        } else {
            Toast.makeText(context, "Permissão da câmera negada!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
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
            showPermissionDialog = true
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

    // AlertDialog para solicitar ao usuário que vá às configurações ativar a permissão
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permissão necessária") },
            text = { Text("O aplicativo precisa da sua permissão de localização e camera para continuar. Vá para as configurações e ative a permissão manualmente.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Ir para Configurações")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDialog = false
                    navController.popBackStack()
                }) {
                    Text("Cancelar")

                }
            }
        )
    }
}

fun generateTempImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}