package org.tavioribeiro.adb_manager.core_ui.components.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel

@Composable
internal fun CustomToastView(toast: ToastUiModel) {

    val backgroundColor = when (toast.type) {
        ToastType.SUCCESS -> Color(0xFF4CAF50)
        ToastType.INFO -> Color(0xFF2196F3)
        ToastType.WARNING -> Color(0xFFFF9800)
        ToastType.ERROR -> Color(0xFFF44336)
    }

    val vector = when (toast.type) {
        ToastType.SUCCESS -> Icons.Default.Check
        ToastType.INFO -> Icons.Default.Info
        ToastType.WARNING -> Icons.Default.Warning
        ToastType.ERROR -> Icons.Default.Close
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .widthIn(max = 400.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = vector,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        /*Icon(
            painter = icon,
            contentDescription = "Toast Icon",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )*/

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = toast.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = toast.message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

