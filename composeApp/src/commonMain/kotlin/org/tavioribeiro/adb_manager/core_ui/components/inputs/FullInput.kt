package org.tavioribeiro.adb_manager.core_ui.components.inputs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullInput(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    warning: String = "",
    initialValue: String = "",
    suffixText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    isBackgroudColorDark: Boolean = false
) {
    var text by remember { mutableStateOf(initialValue) }

    val interactionSource = remember { MutableInteractionSource() }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
        unfocusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
        unfocusedBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
        focusedBorderColor = AppTheme.colors.color7,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = title,
            color = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            visualTransformation = visualTransformation,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2
            ),
            cursorBrush = SolidColor(if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2),
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = text,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = if (isBackgroudColorDark) AppTheme.colors.color6 else AppTheme.colors.color6,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    suffix = if (!suffixText.isNullOrEmpty()) {
                        {
                            Text(
                                text = suffixText,
                                color = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else null,
                    singleLine = true,
                    enabled = true,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 12.dp),
                    colors = colors,
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            enabled = true,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = RoundedCornerShape(8.dp),
                            focusedBorderThickness = 1.dp,
                            unfocusedBorderThickness = 1.dp
                        )
                    }
                )
            }
        )

        if (warning.isNotEmpty()) {
            Text(
                text = warning,
                color = Color(0xFFF44336),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}