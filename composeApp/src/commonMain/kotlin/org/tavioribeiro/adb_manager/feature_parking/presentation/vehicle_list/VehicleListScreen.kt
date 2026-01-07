package org.tavioribeiro.adb_manager.feature_parking.presentation.vehicle_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.tavioribeiro.adb_manager.core_ui.components.toast.CustomToastView
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme
import org.tavioribeiro.adb_manager.feature_parking.domain.model.Vehicle

class VehicleListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<VehicleListViewModel>()

        val state by viewModel.uiState.collectAsState()
        val vehicles by viewModel.vehicles.collectAsState()

        Scaffold(
            containerColor = AppTheme.colors.color1,
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = AppTheme.colors.onColor1)
                    }
                    Text(
                        text = "Veículos no Pátio",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppTheme.colors.onColor1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

                if (vehicles.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenhum veículo estacionado.", color = AppTheme.colors.color6)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(vehicles) { vehicle ->
                            VehicleItem(
                                vehicle = vehicle,
                                onClick = { viewModel.onVehicleSelected(vehicle) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }


                if (state.checkout.isVisible) {
                    CheckoutDialog(
                        state = state.checkout,
                        onConfirm = { viewModel.confirmCheckout() },
                        onDismiss = { viewModel.dismissCheckout() },
                        isLoading = state.isLoading
                    )
                }


                if (state.toast != null) {
                    Box(modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp)) {
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

@Composable
fun VehicleItem(vehicle: Vehicle, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.color2)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = vehicle.plate,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.color7
                )
                Text(
                    text = "${vehicle.model} - ${vehicle.color}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.colors.onColor2
                )
            }
            Icon(Icons.Default.Info, contentDescription = null, tint = AppTheme.colors.color6)
        }
    }
}

@Composable
fun CheckoutDialog(
    state: CheckoutState,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppTheme.colors.color2,
        title = { Text("Confirmar Saída", color = AppTheme.colors.onColor1) },
        text = {
            Column {
                Text("Deseja dar saída no veículo?", color = AppTheme.colors.onColor2)
                Spacer(modifier = Modifier.height(16.dp))

                InfoRow("Placa:", state.vehicle?.plate ?: "")
                InfoRow("Entrada:", state.vehicle?.checkInTime.toString().replace("T", " ").take(16))
                InfoRow("Saída:", state.exitTime.toString().replace("T", " ").take(16))

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = AppTheme.colors.color4)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total a Pagar:", style = MaterialTheme.typography.titleMedium, color = AppTheme.colors.onColor1)
                    Text(
                        text = "R$ ${state.totalAmount}0", // Formatação simples de moeda
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = AppTheme.colors.color7
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colors.color7)
            ) {
                Text(if (isLoading) "Processando..." else "Confirmar Pagamento", color = AppTheme.colors.onColor7)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = AppTheme.colors.color6)
            }
        }
    )
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = AppTheme.colors.color6)
        Text(value, color = AppTheme.colors.onColor2, fontWeight = FontWeight.SemiBold)
    }
}