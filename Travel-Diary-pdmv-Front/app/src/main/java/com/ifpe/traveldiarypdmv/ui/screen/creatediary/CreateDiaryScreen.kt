import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ifpe.traveldiarypdmv.data.model.DiaryManual
import com.ifpe.traveldiarypdmv.data.network.ApiService
import com.ifpe.traveldiarypdmv.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateDiaryScreen(
    navController: NavController,
    userId: String,
    onSaveClick: (String, String, String, String) -> Unit
) {
    val context = LocalContext.current
    val apiService = remember { ApiService.create() } // Instância do ApiService

    // Variáveis de entrada
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Voltar
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Voltar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Criar Novo Diário",
            style = Typography.headlineLarge.copy(fontSize = 24.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //Nome
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do Diário") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        //Descrição
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))

        // Latitude
        OutlinedTextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Latitude") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        //  Longitude
        OutlinedTextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Longitude") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de salvar
        Button(
            onClick = {
                if (name.isNotEmpty()) {
                    // Converte latitude e longitude para Double
                    val latitudeDouble = latitude.toDoubleOrNull()
                    val longitudeDouble = longitude.toDoubleOrNull()

                    // Verifica se a conversão foi bem-sucedida
                    if (latitudeDouble == null || longitudeDouble == null) {
                        Toast.makeText(
                            context,
                            "Latitude e Longitude devem ser números válidos!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // valores convertidos
                    val newDiary = DiaryManual(
                        name = name,
                        description = description,
                        latitude = latitudeDouble,
                        longitude = longitudeDouble,
                        id = userId
                    )

                    // Faz a chamada à API
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = apiService.createDiary(newDiary)
                            if (response.isSuccessful) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Diário salvo com sucesso!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Erro ao salvar o diário",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Erro de conexão: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Preencha todos os campos obrigatórios!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Diário")
        }
    }
}
