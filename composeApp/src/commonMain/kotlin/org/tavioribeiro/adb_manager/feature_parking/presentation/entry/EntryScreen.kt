package org.tavioribeiro.adb_manager.feature_parking.presentation.entry

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.tavioribeiro.adb_manager.core_ui.components.buttons.IconTextButton
import org.tavioribeiro.adb_manager.core_ui.components.inputs.FullInput
import org.tavioribeiro.adb_manager.core_ui.components.select.SelectInput
import org.tavioribeiro.adb_manager.core_ui.components.toast.CustomToastView
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme

class EntryScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<EntryViewModel>()
        val state by viewModel.uiState.collectAsState()

        // Estados do formulário
        var plate by remember { mutableStateOf("") }
        var model by remember { mutableStateOf("") }
        var color by remember { mutableStateOf("") }
        var selectedTableId by remember { mutableStateOf("") }

        // Fecha a tela ao salvar com sucesso
        LaunchedEffect(state.isSaved) {
            if (state.isSaved) {
                delay(1000) // Pequeno delay para o usuário ver o Toast de sucesso
                navigator.pop()
            }
        }

        Scaffold(
            containerColor = AppTheme.colors.color1,
            topBar = {
                // TopBar simples com botão de voltar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = AppTheme.colors.onColor1
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nova Entrada",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppTheme.colors.onColor1
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 450.dp)
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Formulário
                    FullInput(
                        title = "Placa",
                        placeholder = "ABC-1234",
                        initialValue = plate,
                        onValueChange = { plate = it.uppercase() }, // Força Maiúscula
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                        isBackgroudColorDark = true
                    )

                    FullInput(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Modelo",
                        placeholder = "Ex: Gol, Civic...",
                        initialValue = model,
                        onValueChange = { model = it },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        isBackgroudColorDark = true
                    )

                    FullInput(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Cor",
                        placeholder = "Ex: Prata, Preto...",
                        initialValue = color,
                        onValueChange = { color = it },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        isBackgroudColorDark = true
                    )

                    // Dropdown de Tabelas de Preço
                    SelectInput(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Tabela de Preço",
                        placeholder = "Selecione uma tabela",
                        options = state.priceTables,
                        onValueChange = { selectedTableId = it },
                        isBackgroudColorDark = true,
                        emptyStateText = "Nenhuma tabela sincronizada. Faça o Sync na Dashboard."
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    IconTextButton(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        text = "CONFIRMAR ENTRADA",
                        icon = rememberVectorPainter(Icons.Default.Check),
                        isLoading = state.isLoading,
                        enabled = !state.isLoading,
                        onClick = {
                            viewModel.saveVehicle(plate, model, color, selectedTableId)
                        }
                    )
                }

                // Toast
                if (state.toast != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp)
                    ) {
                        CustomToastView(state.toast!!)
                        LaunchedEffect(state.toast) {
                            delay(3000)
                            viewModel.clearToast()
                        }
                    }
                }
            }
        }
    }
}