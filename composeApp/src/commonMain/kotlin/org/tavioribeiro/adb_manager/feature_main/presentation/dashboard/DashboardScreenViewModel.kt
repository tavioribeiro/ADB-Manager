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
import org.tavioribeiro.adb_manager.feature_main.domain.model.*





data class DashboardUiState(
    val isLoading: Boolean = false,
    val devices: List<AdbDeviceUi> = emptyList(),
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

    // Define o diretório de execução (ponto = diretório atual)
    private val defaultPath = "."

    /**
     * Passo 1: Lista os dispositivos conectados via ADB.
     * Se encontrar um dispositivo pronto ("device"), seleciona automaticamente e carrega os dados.
     */
    fun refreshDevices() {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Executa adb devices -l para ter mais detalhes (modelo, produto)
            val result = executeAndLog("LIST_DEVICES", "adb devices -l")

            val deviceList = mutableListOf<AdbDeviceUi>()

            if (result.isSuccess) {
                val output = result.getOrThrow()

                output.lines().forEach { line ->
                    // Filtra linhas vazias ou o cabeçalho
                    if (line.isNotBlank() && !line.startsWith("List of devices")) {
                        // Regex básico para separar colunas por espaço
                        val parts = line.split("\\s+".toRegex())

                        if (parts.size >= 2) {
                            val serial = parts[0]
                            val state = parts[1] // device, offline, unauthorized

                            // Tenta extrair o modelo amigável (ex: model:Pixel_6)
                            val modelPart = parts.find { it.startsWith("model:") }
                            val cleanModel = modelPart?.substringAfter("model:")?.replace("_", " ")
                                ?: "Dispositivo Genérico ($serial)"

                            deviceList.add(
                                AdbDeviceUi(
                                    serial = serial,
                                    model = cleanModel,
                                    state = state,
                                    isOnline = state == "device"
                                )
                            )
                        }
                    }
                }
            } else {
                toastViewModel.showToast(ToastUiModel("Erro ADB", "Falha ao listar dispositivos", ToastType.ERROR))
            }

            // Atualiza a lista na UI
            _uiState.update { it.copy(isLoading = false, devices = deviceList) }

            // LÓGICA DE AUTO-SELEÇÃO
            // Se já temos um selecionado, mantemos. Se não, pegamos o primeiro online.
            val currentSelected = _uiState.value.selectedDeviceSerial
            if (currentSelected == null) {
                val firstOnline = deviceList.firstOrNull { it.isOnline }
                if (firstOnline != null) {
                    println(">>> [AUTO-SELECT] Dispositivo encontrado: ${firstOnline.model}")
                    loadDashboardData(firstOnline.serial)
                } else {
                    println(">>> [AUTO-SELECT] Nenhum dispositivo online encontrado.")
                }
            }
        }
    }

    /**
     * Passo 2: Carrega as informações detalhadas do dispositivo selecionado.
     * Usa concorrência (async) para buscar tudo ao mesmo tempo.
     */
    fun loadDashboardData(deviceSerial: String) {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, selectedDeviceSerial = deviceSerial, error = null) }

            try {
                // Inicia todas as chamadas em paralelo
                val cpuDeferred = async { fetchCpuInfo(deviceSerial) }
                val memDeferred = async { fetchMemoryInfo(deviceSerial) }
                val storageDeferred = async { fetchStorageInfo(deviceSerial) }
                val sysDeferred = async { fetchSystemInfo(deviceSerial) }

                // Aguarda todas retornarem
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

    // --- Métodos Privados de Coleta de Dados ---

    private suspend fun fetchCpuInfo(serial: String): CpuUiData {
        val result = executeAndLog("CPU_INFO", "adb -s $serial shell dumpsys cpuinfo")

        // TODO: Implementar Parser Real do output.
        // Retornando Mock para validação visual
        return CpuUiData(
            usagePercent = 42,
            activeCores = 4,
            totalCores = 8
        )
    }

    private suspend fun fetchMemoryInfo(serial: String): MemoryUiData {
        val result = executeAndLog("MEM_INFO", "adb -s $serial shell dumpsys meminfo")

        // TODO: Implementar Parser Real do output.
        return MemoryUiData(
            usagePercent = 68,
            usedGb = 5.4,
            totalGb = 8.0
        )
    }

    private suspend fun fetchStorageInfo(serial: String): StorageUiData {
        val result = executeAndLog("STORAGE_INFO", "adb -s $serial shell df -h /data")

        // TODO: Implementar Parser Real do output.
        return StorageUiData(
            usagePercent = 90,
            usedGb = 115.0,
            totalGb = 128.0,
            freeGb = 13.0
        )
    }

    private suspend fun fetchSystemInfo(serial: String): SystemInfoUiData {
        // Coleta dados individuais
        val version = executeAndLog("SYS_VER", "adb -s $serial shell getprop ro.build.version.release")
        val resolution = executeAndLog("SYS_RES", "adb -s $serial shell wm size")
        val dpi = executeAndLog("SYS_DPI", "adb -s $serial shell wm density")
        val rootCheck = executeAndLog("SYS_ROOT", "adb -s $serial shell which su")
        val buildId = executeAndLog("SYS_BUILD", "adb -s $serial shell getprop ro.build.display.id")
        val patch = executeAndLog("SYS_PATCH", "adb -s $serial shell getprop ro.build.version.security_patch")

        // Tratamento básico de Strings
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
     * Executa o comando e imprime o log formatado no console do Desktop.
     * Facilita a depuração sem sujar a UI.
     */
    private suspend fun executeAndLog(tag: String, command: String): Result<String> {
        println("\n---------- [ADB: $tag] ----------")
        println("Executando: $command")

        val result = console.execute(command, defaultPath)

        result.fold(
            onSuccess = { output ->
                // Trunca logs gigantes (ex: dumpsys cpuinfo pode ser enorme)
                val preview = if (output.length > 1000) output.take(1000) + "\n... [CORTADO]" else output
                println(">>> SUCESSO:\n$preview")
            },
            onFailure = { error ->
                println(">>> ERRO:\n${error.message}")
            }
        )
        println("-------------------------------------")

        return result
    }

    // Extensão utilitária para facilitar o Result
    private fun Result<String>.getOrDefault(default: String): Result<String> {
        return if (this.isSuccess) this else Result.success(default)
    }
}