package com.ifpe.traveldiarypdmv.ui.screen.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ifpe.traveldiarypdmv.R
import com.ifpe.traveldiarypdmv.ui.component.dropdown_menu.TravelDiaryDropdownMenu
import com.ifpe.traveldiarypdmv.ui.theme.Gray200
import com.ifpe.traveldiarypdmv.ui.theme.GreenBase
import com.ifpe.traveldiarypdmv.ui.theme.Typography

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    var menuExpanded by remember { mutableStateOf(false) } // Controle do menu

    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc molestie at quam ultrices efficitur. Praesent luctus bibendum porta. Vivamus efficitur vehicula ante, scelerisque mollis lectus ultricies ut. Ut tortor magna, semper ac porta vel, lobortis nec ligula. Sed in libero ornare, sollicitudin orci nec, fermentum lectus. Duis id urna et velit consequat venenatis. Aenean dapibus turpis risus, eu laoreet turpis consectetur at."

    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .fillMaxSize()
        .padding(start = 10.dp, bottom = 12.dp, end = 12.dp, top = 25.dp),
    ){

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
                    modifier = Modifier.align(Alignment.TopEnd) // Alinhando ao lado direito
                )
            }
        }

        Box{
            Image(
                painter = painterResource(id = R.drawable.rome),
                contentDescription = "Mapa ou imagem do topo",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 25.dp, top = 25.dp),
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = {

                },
            ) {
                Icon(
                    modifier = Modifier.size(55.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Ícone de voltar para os Diários",
                    tint = Color.Gray
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(horizontal = 10.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Black.copy(alpha = 0.3f))  // Aplica sombra
                .background(Gray200)
                .height(100.dp)
                .horizontalScroll(rememberScrollState())
        ){
            val locationName = "Taumatawhakatangihangakoauauotamateaturipukakapikimaungahoronukupokaiwhenuakitanatahu"
            val truncatedName = if (locationName.length > 20) {
                locationName.take(10) + "..."
            } else {
                locationName
            }
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = locationName,
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
                        text = "Rome",
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
                        text = "Rome,",
                        style = Typography.bodyMedium.copy(fontSize = 17.sp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Italy",
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
            text = description,
            style = Typography.bodyMedium.copy(textAlign = TextAlign.Justify, fontSize = 15.sp),
            color = Color.DarkGray,

        )
        }
    }
}
