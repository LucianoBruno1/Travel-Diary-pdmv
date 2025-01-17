package com.ifpe.traveldiarypdmv.ui.component.dropdown_menu

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TravelDiaryDropdownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,                     // Recebe o estado de visibilidade
    onDismissRequest: () -> Unit,         // Controla o fechamento do menu
    onEditClick: () -> Unit,              // Ação para editar
    onDeleteClick: () -> Unit             // Ação para excluir
    ) {

    // DropdownMenu para exibir as opções
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier, // Mantém o modificador para controle externo
    ) {
        // Opção Editar Diário
        DropdownMenuItem(
            text = { Text("Editar Diário") },
            onClick = {
                onDismissRequest()
                onEditClick()
            }
        )

        // Opção Excluir Diário
        DropdownMenuItem(
            text = { Text("Excluir Diário") },
            onClick = {
                onDismissRequest()
                onDeleteClick()
            }
        )
    }
}