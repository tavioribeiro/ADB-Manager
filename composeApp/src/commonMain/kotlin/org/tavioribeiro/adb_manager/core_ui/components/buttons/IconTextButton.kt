package org.tavioribeiro.adb_manager.core_ui.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme


@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    icon: Painter,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = {
            if(!isLoading) onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.color7,
            contentColor = AppTheme.colors.onColor7,
            disabledContainerColor = AppTheme.colors.color5,
            disabledContentColor = AppTheme.colors.onColor5
        ),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                val exit = fadeOut(animationSpec = tween(300))

                val enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        delayMillis = 300
                    )
                )

                enter togetherWith exit
            },
            label = "TabContentAnimation"
        ) { loadingComponent ->
            if(loadingComponent){
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp),
                        color = AppTheme.colors.onColor7,
                        strokeWidth = 2.dp
                    )
                }
            }
            else {
                Row(
                    horizontalArrangement  = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = AppTheme.colors.onColor7,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = text,
                        color = AppTheme.colors.onColor7,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}