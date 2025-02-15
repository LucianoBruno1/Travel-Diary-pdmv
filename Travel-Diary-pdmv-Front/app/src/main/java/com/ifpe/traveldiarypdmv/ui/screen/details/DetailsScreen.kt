package com.ifpe.traveldiarypdmv.ui.screen.details

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ifpe.traveldiarypdmv.ui.component.dropdown_menu.TravelDiaryDropdownMenu
import com.ifpe.traveldiarypdmv.ui.theme.Gray200
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = viewModel(),
    diaryId: String,
    token: String,
    userId: String,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()

    var menuExpanded by remember { mutableStateOf(false) } // Controle do menu
    var currentImageIndex by remember { mutableStateOf(0) } // Índice da imagem atual

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    var showPhotoDialog by remember { mutableStateOf(false) }
    var selectedPhotos by remember { mutableStateOf<List<Uri>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailsUiEvent.LoadDiary(diaryId, token))
    }

    val context = LocalContext.current

    LaunchedEffect(selectedPhotos) {
        if (selectedPhotos.isNotEmpty()) {
            viewModel.onEvent(DetailsUiEvent.UploadPhotos(userId, diaryId, selectedPhotos, context))
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .fillMaxSize()
                .padding(start = 10.dp, bottom = 12.dp, end = 12.dp, top = 25.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Distribui os elementos igualmente
            ) {
                // Ícone de voltar (esquerda)
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }

                // Título centralizado
                Text(
                    text = "Detalhe do Diário",
                    style = Typography.bodyLarge.copy(fontSize = 20.sp),
                    color = Color.Black
                )

                // Ícone de menu (três pontos)
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu de opções",
                            tint = Color.Black
                        )
                    }

                    // O DropdownMenu está dentro do Box, alinhando-se ao lado direito
                    TravelDiaryDropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        onEditClick = {
                            menuExpanded = false
                            println("Editar Diário Selecionado")
                        },
                        onDeleteClick = {
                            menuExpanded = false
                            println("Excluir Diário Selecionado")
                        },
                        onAddPhotosClick = {
                            menuExpanded = false
                            showPhotoDialog = true
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    if (showPhotoDialog) {
                        PhotoUploadDialog(
                            onDismiss = { showPhotoDialog = false },
                            onPhotosSelected = { uris ->
                                selectedPhotos = uris
                                showPhotoDialog = false
                            }
                        )
                    }

                }
            }

            Box {
                val images = uiState.images

                Log.d("DetailsScreen", "Lista de imagens recebida: ${uiState.images}")

                if (images.isNotEmpty()) {
//                    Image(
//                        painter = rememberAsyncImagePainter(
//                            ImageRequest.Builder(LocalContext.current)
//                                .data(images[currentImageIndex])
//                                .crossfade(true) // Efeito de transição suave entre imagens
//                                .build()
//                        ), // Substituir por Coil ou Glide para imagens remotas
//                        contentDescription = "Imagem do diário",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(max = 300.dp)
//                            .padding(start = 10.dp, end = 10.dp, bottom = 25.dp, top = 25.dp),
//                        contentScale = ContentScale.Crop
//                    )
//
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.images) { imageUrl ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(8.dp),
                                modifier = Modifier
                                    .width(180.dp)
                                    .aspectRatio(3f / 4f)
                                    .clickable {
                                        selectedImageUrl = imageUrl
                                    } // Abre a imagem ao clicar
                            ) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Imagem do diário",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (selectedImageUrl != null) {
                        Dialog(
                            onDismissRequest = { selectedImageUrl = null } // Fecha ao tocar fora
                        ) {
                            FullscreenImageViewer(
                                imageUrl = selectedImageUrl!!,
                                onClose = { selectedImageUrl = null }
                            )
                        }
                    }


//                    IconButton(
//                        modifier = Modifier.align(Alignment.CenterEnd),
//                        onClick = {
//                            currentImageIndex =
//                                (currentImageIndex + 1) % images.size // Avança para a próxima imagem
//                        },
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(55.dp),
//                            imageVector = Icons.Default.KeyboardArrowRight,
//                            contentDescription = "Passar imagens",
//                            tint = Color.Gray
//                        )
//                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(start = 10.dp, end = 10.dp, top = 16.dp)
                    .shadow(
                        8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color.Black.copy(alpha = 0.3f)
                    )  // Aplica sombra
                    .background(Gray200)
                    .height(100.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                val locationName = ""
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = uiState.city,
                            style = Typography.bodyLarge.copy(fontSize = 20.sp),
                            color = Color.White,
                        )
                        Text(
                            text = ",",
                            style = Typography.bodyLarge.copy(fontSize = 20.sp),
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = uiState.state,
                            style = Typography.bodyMedium.copy(fontSize = 15.sp),
                            color = GreenBase
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ícone de localização",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = uiState.state,
                            style = Typography.bodyMedium.copy(fontSize = 17.sp),
                            color = Color.White
                        )
                        Text(
                            text = ",",
                            style = Typography.bodyLarge.copy(fontSize = 20.sp),
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = uiState.city,
                            style = Typography.bodyMedium.copy(fontSize = 16.sp),
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(
                modifier = Modifier
                    .height(2.dp)
                    .background(Color.Black)
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )

            Column(
                modifier = Modifier
                    .padding(15.dp)
            ) {

                Text(
                    text = "Descrição",
                    style = Typography.labelLarge.copy(fontSize = 20.sp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.description,
                    style = Typography.bodyMedium.copy(
                        textAlign = TextAlign.Justify,
                        fontSize = 15.sp
                    ),
                    color = Color.DarkGray,

                    )
            }
        }
    }
}

@Composable
fun FullscreenImageViewer(imageUrl: String, onClose: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotation by remember { mutableStateOf(0f) }

    val transformationState = rememberTransformableState { zoomChange, panChange, rotationChange ->
        scale *= zoomChange
        offset = Offset(offset.x + panChange.x, offset.y + panChange.y)
        rotation += rotationChange
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) { detectTapGestures { onClose() } }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Imagem ampliada",
            contentScale = if (isLandscape) ContentScale.Crop else ContentScale.Fit, // Ajusta dinamicamente
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                    rotationZ = rotation
                )
                .transformable(transformationState)
        )
    }
}

@Composable
fun PhotoUploadDialog(
    onDismiss: () -> Unit,
    onPhotosSelected: (List<Uri>) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            onPhotosSelected(uris)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Adicionar Fotos", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { launcher.launch("image/*") }) {
                    Text("Selecionar Fotos")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Color.Red)) {
                    Text("Cancelar")
                }
            }
        }
    }
}