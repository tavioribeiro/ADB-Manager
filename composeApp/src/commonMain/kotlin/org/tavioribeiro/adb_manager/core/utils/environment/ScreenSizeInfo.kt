@file:JvmName("ScreenSizeInfoJvm")

package org.tavioribeiro.adb_manager.core.utils.environment

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmName

data class WindowSize(
    val width: WindowType,
    val height: WindowType
)

enum class WindowType {
    Compact, Medium, Expanded
}

@Composable
expect fun getScreenWidthDp(): Dp

@Composable
expect fun getScreenHeightDp(): Dp

@Composable
fun getWindowSize(): WindowSize {
    val widthDp = getScreenWidthDp()
    val heightDp = getScreenHeightDp()

    val windowWidthType = when {
        widthDp < 600.dp -> WindowType.Compact
        widthDp < 900.dp -> WindowType.Medium
        else -> WindowType.Expanded
    }

    val windowHeightType = when {
        heightDp < 480.dp -> WindowType.Compact
        heightDp < 900.dp -> WindowType.Medium
        else -> WindowType.Expanded
    }

    println("Window size: $widthDp x $heightDp")

    return WindowSize(windowWidthType, windowHeightType)
}