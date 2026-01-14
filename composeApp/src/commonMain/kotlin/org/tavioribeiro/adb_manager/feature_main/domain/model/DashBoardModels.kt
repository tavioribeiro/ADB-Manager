package org.tavioribeiro.adb_manager.feature_main.domain.model


data class AdbDeviceUi(
    val serial: String,
    val model: String,
    val state: String,
    val isOnline: Boolean
)

data class CpuUiData(
    val usagePercent: Int,
    val activeCores: Int,
    val totalCores: Int
)

data class MemoryUiData(
    val usagePercent: Int,
    val usedGb: Double,
    val totalGb: Double
)

data class StorageUiData(
    val usagePercent: Int,
    val usedGb: Double,
    val totalGb: Double,
    val freeGb: Double
)

data class SystemInfoUiData(
    val androidVersion: String,
    val resolution: String,
    val dpi: Int,
    val isRooted: Boolean,
    val buildNumber: String,
    val securityPatch: String
)
