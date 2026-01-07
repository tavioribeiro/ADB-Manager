package org.tavioribeiro.adb_manager.core_ui.components.select

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.tavioribeiro.adb_manager.core_ui.components.select.model.SelectOptionModel
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectInput(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    options: List<SelectOptionModel>,
    initialOption: SelectOptionModel? = null,
    initialPosition: Int? = null,
    initialValue: String? = null,
    onValueChange: (String) -> Unit,
    isBackgroudColorDark: Boolean = false,
    emptyStateText: String = "Não há opções disponíveis"
) {
    val isEmpty = options.isEmpty()
    var expanded by remember { mutableStateOf(false) }
    var selectedLabel by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(initialValue, initialOption, initialPosition, options) {
        if (!isEmpty) {
            val label = initialOption?.label
                ?: options.getOrNull(initialPosition ?: -1)?.label
                ?: options.find { it.value == initialValue }?.label
            selectedLabel = label ?: ""
        }
    }

    val placeholderColor = if (isBackgroudColorDark) AppTheme.colors.color6 else AppTheme.colors.color6
    val colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
        unfocusedTextColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
        unfocusedBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
        focusedBorderColor = AppTheme.colors.color7,
        focusedTrailingIconColor = AppTheme.colors.color7,
        unfocusedTrailingIconColor = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
        disabledTrailingIconColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
        disabledTextColor = placeholderColor,
        disabledBorderColor = if (isBackgroudColorDark) AppTheme.colors.onColor3 else AppTheme.colors.color3,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text(
            text = title,
            color = if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (!isEmpty) expanded = !expanded }
        ) {
            BasicTextField(
                value = if (isEmpty) emptyStateText else selectedLabel,
                onValueChange = {},
                readOnly = true,
                enabled = !isEmpty,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .height(44.dp),
                singleLine = true,
                interactionSource = interactionSource,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = if (isEmpty) placeholderColor else (if (isBackgroudColorDark) AppTheme.colors.onColor2 else AppTheme.colors.color2)
                ),
                cursorBrush = SolidColor(Color.Transparent),
                decorationBox = { innerTextField ->
                    OutlinedTextFieldDefaults.DecorationBox(
                        value = if (isEmpty) emptyStateText else selectedLabel,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        placeholder = {
                            Text(
                                text = placeholder,
                                color = placeholderColor
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        singleLine = true,
                        enabled = !isEmpty,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(start = 12.dp, top = 0.dp, bottom = 0.dp, end = 0.dp),
                        colors = colors,
                        container = {
                            OutlinedTextFieldDefaults.ContainerBox(
                                enabled = !isEmpty,
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

            if (!isEmpty) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.label) },
                            onClick = {
                                selectedLabel = selectionOption.label
                                onValueChange(selectionOption.value)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}