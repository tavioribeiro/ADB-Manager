package org.tavioribeiro.adb_manager.core_ui.components.toast.model


data class ToastUiModel(
    val title: String,
    val message: String,
    val type: ToastType,
    val duration: Long = 4000L
)