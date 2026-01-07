package org.tavioribeiro.adb_manager.feature_parking.presentation.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.tavioribeiro.adb_manager.core_ui.components.buttons.IconTextButton
import org.tavioribeiro.adb_manager.core_ui.components.toast.CustomToastView
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme
import org.tavioribeiro.adb_manager.core_ui.theme.ThemeState
import org.tavioribeiro.adb_manager.feature_auth.presentation.login.LoginScreen
import org.tavioribeiro.adb_manager.feature_parking.presentation.entry.EntryScreen
import org.tavioribeiro.adb_manager.feature_parking.presentation.vehicle_list.VehicleListScreen


class DashboardScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DashboardViewModel>()

        val state by viewModel.uiState.collectAsState()
        val parkedCount by viewModel.parkedCount.collectAsState()

        Scaffold(
            containerColor = AppTheme.colors.color1,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Olá, ${state.userName}",
                            color = AppTheme.colors.onColor1,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Estabelecimento: ${state.establishmentId}",
                            color = AppTheme.colors.color6,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row {
                        IconButton(
                            onClick = {
                                ThemeState.toggleTheme()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.BrightnessMedium,
                                contentDescription = "Alterar Tema",
                                tint = AppTheme.colors.color7
                            )
                        }


                        IconButton(
                            onClick = {
                                viewModel.logout()
                                navigator.replaceAll(LoginScreen())
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Sair",
                                tint = AppTheme.colors.color7
                            )
                        }
                    }
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


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .border(1.dp, AppTheme.colors.color3, RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.color2),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = parkedCount.toString(),
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.colors.color7
                                )
                            )
                            Text(
                                text = "Veículos no Pátio",
                                style = MaterialTheme.typography.titleMedium,
                                color = AppTheme.colors.color6
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))


                    Text(
                        text = "Ações Rápidas",
                        style = MaterialTheme.typography.titleMedium,
                        color = AppTheme.colors.onColor1,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    IconTextButton(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        text = "NOVA ENTRADA",
                        icon = rememberVectorPainter(Icons.Default.Add),
                        onClick = {
                            navigator.push(EntryScreen())
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    IconTextButton(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        text = "LISTA DE VEÍCULOS / SAÍDA",
                        icon = rememberVectorPainter(Icons.Default.List),
                        onClick = {
                            navigator.push(VehicleListScreen())
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Divider(color = AppTheme.colors.color3)

                    Spacer(modifier = Modifier.height(32.dp))


                    IconTextButton(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        text = "SINCRONIZAR DADOS",
                        icon = rememberVectorPainter(Icons.Default.Refresh),
                        isLoading = state.isLoading,
                        enabled = !state.isLoading,
                        onClick = { viewModel.syncData() }
                    )

                    if (state.isLoading) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Baixando tabelas de preço...",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.colors.color6
                        )
                    }
                }


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