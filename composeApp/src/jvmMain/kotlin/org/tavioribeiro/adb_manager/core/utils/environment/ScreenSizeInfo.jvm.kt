package org.tavioribeiro.adb_manager.core.utils.environment

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenWidthDp(): Dp {
    return LocalWindowInfo.current.containerSize.width.dp
}

@Composable
actual fun getScreenHeightDp(): Dp {
    return LocalWindowInfo.current.containerSize.height.dp
}
