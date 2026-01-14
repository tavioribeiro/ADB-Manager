package org.tavioribeiro.adb_manager.feature_main.presentation.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.adb_manager.core.utils.console.ConsoleProvider
import org.tavioribeiro.adb_manager.core_ui.components.toast.ToastViewModel
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel
import org.tavioribeiro.adb_manager.feature_main.domain.model.CpuUiData
import org.tavioribeiro.adb_manager.feature_main.domain.model.MemoryUiData
import org.tavioribeiro.adb_manager.feature_main.domain.model.StorageUiData
import org.tavioribeiro.adb_manager.feature_main.domain.model.SystemInfoUiData

data class DashboardUiState(
    val isLoading: Boolean = false,
    val selectedDeviceSerial: String? = null,
    val cpu: CpuUiData? = null,
    val memory: MemoryUiData? = null,
    val storage: StorageUiData? = null,
    val systemInfo: SystemInfoUiData? = null,
    val error: String? = null
)

class DashboardScreenViewModel(
    private val toastViewModel: ToastViewModel,
    private val console: ConsoleProvider,
) : ScreenModel {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    private val defaultPath = "."

    fun loadDashboardData(deviceSerial: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, selectedDeviceSerial = deviceSerial, error = null) }

            try {
                val cpuDeferred = async { fetchCpuInfo(deviceSerial) }
                val memDeferred = async { fetchMemoryInfo(deviceSerial) }
                val storageDeferred = async { fetchStorageInfo(deviceSerial) }
                val sysDeferred = async { fetchSystemInfo(deviceSerial) }

                val cpuData = cpuDeferred.await()
                val memData = memDeferred.await()
                val storageData = storageDeferred.await()
                val sysData = sysDeferred.await()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cpu = cpuData,
                        memory = memData,
                        storage = storageData,
                        systemInfo = sysData
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false, error = e.message) }
                toastViewModel.showToast(
                    ToastUiModel("Erro ao carregar dados", e.message ?: "Falha desconhecida", ToastType.ERROR)
                )
            }
        }
    }

    // --- Métodos de Busca (Fetching) ---

    private suspend fun fetchCpuInfo(serial: String): CpuUiData {
        val result = executeAndLog(
            tag = "CPU_INFO",
            command = "adb -s $serial shell dumpsys cpuinfo"
        )

        // Aqui você usaria o 'result.getOrNull()' para fazer o regex real.
        // Simulando dados por enquanto:
        return CpuUiData(
            usagePercent = 42,
            activeCores = 4,
            totalCores = 8
        )
    }

    private suspend fun fetchMemoryInfo(serial: String): MemoryUiData {
        val result = executeAndLog(
            tag = "MEM_INFO",
            command = "adb -s $serial shell dumpsys meminfo"
        )

        return MemoryUiData(
            usagePercent = 68,
            usedGb = 5.4,
            totalGb = 8.0
        )
    }

    private suspend fun fetchStorageInfo(serial: String): StorageUiData {
        val result = executeAndLog(
            tag = "STORAGE_INFO",
            command = "adb -s $serial shell df -h /data"
        )

        return StorageUiData(
            usagePercent = 90,
            usedGb = 115.0,
            totalGb = 128.0,
            freeGb = 13.0
        )
    }

    private suspend fun fetchSystemInfo(serial: String): SystemInfoUiData {
        // Executamos e logamos cada sub-comando individualmente
        val version = executeAndLog("SYS_VER", "adb -s $serial shell getprop ro.build.version.release")
        val resolution = executeAndLog("SYS_RES", "adb -s $serial shell wm size")
        val dpi = executeAndLog("SYS_DPI", "adb -s $serial shell wm density")
        val rootCheck = executeAndLog("SYS_ROOT", "adb -s $serial shell which su")
        val buildId = executeAndLog("SYS_BUILD", "adb -s $serial shell getprop ro.build.display.id")
        val patch = executeAndLog("SYS_PATCH", "adb -s $serial shell getprop ro.build.version.security_patch")

        // Processamento básico das strings
        val cleanResolution = resolution.getOrNull()?.substringAfter("Physical size: ")?.trim() ?: "Unknown"
        val cleanDpi = dpi.getOrNull()?.substringAfter("Physical density: ")?.toIntOrNull() ?: 0
        val isRooted = rootCheck.isSuccess && !rootCheck.getOrNull().isNullOrBlank()

        return SystemInfoUiData(
            androidVersion = version.getOrNull()?.trim() ?: "-",
            resolution = cleanResolution,
            dpi = cleanDpi,
            isRooted = isRooted,
            buildNumber = buildId.getOrNull()?.trim() ?: "-",
            securityPatch = patch.getOrNull()?.trim() ?: "-"
        )
    }

    /**
     * Helper centralizado para executar comandos e imprimir o Log no console do Desktop.
     * Isso ajuda a depurar o que o ADB está retornando antes de tentarmos processar os dados.
     */
    private suspend fun executeAndLog(tag: String, command: String): Result<String> {
        println("---------- [DEBUG: $tag] ----------")
        println("CMD: $command")

        val result = console.execute(command, defaultPath)

        result.fold(
            onSuccess = { output ->
                // Corta se for muito longo para não poluir demais, mas mostra o início
                val preview = if (output.length > 500) output.take(500) + "... [TRUNCATED]" else output
                println("OUTPUT SUCCESS:\n$preview")
            },
            onFailure = { error ->
                println("OUTPUT ERROR:\n${error.message}")
            }
        )
        println("-------------------------------------\n")

        return result
    }
}