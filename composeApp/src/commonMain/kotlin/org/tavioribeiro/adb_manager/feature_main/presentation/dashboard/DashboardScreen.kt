package org.tavioribeiro.adb_manager.feature_main.presentation.dashboard

import adbmanager.composeapp.generated.resources.Res
import adbmanager.composeapp.generated.resources.icon_routine
import adbmanager.composeapp.generated.resources.logo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage
import org.tavioribeiro.adb_manager.core_ui.components.buttons.IconTextButton
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme
import org.tavioribeiro.adb_manager.core_ui.theme.ThemeState

class DashboardScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenViewModel = getScreenModel<DashboardScreenViewModel>()
        val state by screenViewModel.uiState.collectAsState()

        val localStorage = koinInject<LocalStorage>()
        ThemeState.initialize(localStorage)

        // =====================================================================
        // 1. GATILHO INICIAL: Buscar dispositivos ao abrir a tela
        // =====================================================================
        LaunchedEffect(Unit) {
            println(">>> [VIEW] Tela Iniciada: Buscando dispositivos...")
            screenViewModel.refreshDevices()
        }

        // =====================================================================
        // 2. LOGGER: Monitora o estado e imprime no console quando os dados chegam
        // =====================================================================
        LaunchedEffect(state) {
            // Log de Erro
            if (state.error != null) {
                println(">>> [VIEW ERROR] Ocorreu um erro: ${state.error}")
            }

            // Log de Dispositivos encontrados
            if (state.devices.isNotEmpty()) {
                println(">>> [VIEW DATA] Dispositivos na lista: ${state.devices.size}")
                state.devices.forEach { device ->
                    println("       -> Modelo: ${device.model} | Serial: ${device.serial} | Status: ${device.state}")
                }
            }

            // Log de Dados do Dashboard (Só imprime se não for nulo)
            state.selectedDeviceSerial?.let { serial ->
                if (!state.isLoading && state.cpu != null) {
                    println("\n========== DADOS RECEBIDOS DO DEVICE ($serial) ==========")
                    println("CPU: ${state.cpu?.usagePercent}% de uso (${state.cpu?.activeCores}/${state.cpu?.totalCores} cores)")
                    println("RAM: ${state.memory?.usagePercent}% de uso (${state.memory?.usedGb}/${state.memory?.totalGb} GB)")
                    println("STORAGE: ${state.storage?.usagePercent}% ocupado (${state.storage?.freeGb} GB livres)")
                    println("SYSTEM: Android ${state.systemInfo?.androidVersion} | Res: ${state.systemInfo?.resolution}")
                    println("=========================================================\n")
                }
            }
        }
        // =====================================================================

        var selectedButton by remember { mutableStateOf("Commits") }

        Column(
            modifier = Modifier
                .background(AppTheme.colors.color1)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Row(
                Modifier.height(73.dp)
                    .fillMaxWidth()
                    .background(AppTheme.colors.color2)
                    .padding(horizontal = 80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "Meu Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(32.dp)
                    )

                    Column (){
                        Text(
                            text = "ADB Manager",
                            color = AppTheme.colors.onColor1,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "V:1.0",
                            color = AppTheme.colors.color5,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier.padding(horizontal = 80.dp),
                        thickness = 1.dp,
                        color = AppTheme.colors.color4
                    )
                }

                IconTextButton(
                    text = "Alterar Tema",
                    onClick = {
                        ThemeState.toggleTheme()
                        // Opcional: Recarregar dispositivos ao clicar aqui para testar manualmente
                        // screenViewModel.refreshDevices()
                    },
                    icon = painterResource(Res.drawable.icon_routine),
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .width(1232.dp)
                    .background(AppTheme.colors.color1)
                    .padding(horizontal = 20.dp),
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botões de aba comentados no seu código original...
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AppTheme.colors.color5)
                )

                Spacer(modifier = Modifier.height(32.dp))

                AnimatedContent(
                    targetState = selectedButton,
                    modifier = Modifier.fillMaxWidth(),
                    transitionSpec = {
                        val exit = fadeOut(animationSpec = tween(300))
                        val enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 300))
                        enter togetherWith exit
                    },
                    label = "TabContentAnimation"
                ){ targetState ->
                    // Conteúdo das abas...
                }
            }
        }
    }
}