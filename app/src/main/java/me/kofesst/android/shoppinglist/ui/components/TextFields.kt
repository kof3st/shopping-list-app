package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

class TextFields private constructor() {
    companion object {
        @Composable
        fun OutlinedNumericTextField(
            modifier: Modifier = Modifier,
            value: Int = 0,
            onValueChange: (Int) -> Unit = {},
            isReadOnly: Boolean = false,
            errorMessage: String? = null,
            label: String = "",
            leadingIcon: Painter? = null,
            trailingIcon: Painter? = null,
            onTrailingIconClick: () -> Unit = {},
            singleLine: Boolean = true,
            textStyle: TextStyle = MaterialTheme.typography.body1
        ) {
            Column(modifier = modifier) {
                OutlinedTextField(
                    value = value.toString(),
                    onValueChange = {
                        onValueChange(
                            if (it.isBlank()) {
                                value
                            } else {
                                it.toIntOrNull() ?: value
                            }
                        )
                    },
                    readOnly = isReadOnly,
                    isError = errorMessage != null,
                    label = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.body2
                        )
                    },
                    leadingIcon = if (leadingIcon != null) {
                        {
                            Icon(
                                painter = leadingIcon,
                                contentDescription = null
                            )
                        }
                    } else {
                        null
                    },
                    singleLine = singleLine,
                    trailingIcon = if (trailingIcon != null) {
                        {
                            IconButton(onClick = onTrailingIconClick) {
                                Icon(
                                    painter = trailingIcon,
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        null
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = textStyle,
                    modifier = Modifier.fillMaxWidth()
                )
                TextFieldError(
                    modifier = Modifier.fillMaxWidth(),
                    message = errorMessage
                )
            }
        }

        @Composable
        fun OutlinedTextField(
            modifier: Modifier = Modifier,
            value: String = "",
            onValueChange: (String) -> Unit = {},
            isReadOnly: Boolean = false,
            errorMessage: String? = null,
            label: String = "",
            leadingIcon: Painter? = null,
            trailingIcon: Painter? = null,
            onTrailingIconClick: () -> Unit = {},
            singleLine: Boolean = true,
            textStyle: TextStyle = MaterialTheme.typography.body1
        ) {
            Column(modifier = modifier) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    readOnly = isReadOnly,
                    isError = errorMessage != null,
                    label = {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.body2
                        )
                    },
                    leadingIcon = if (leadingIcon != null) {
                        {
                            Icon(
                                painter = leadingIcon,
                                contentDescription = null
                            )
                        }
                    } else {
                        null
                    },
                    singleLine = singleLine,
                    trailingIcon = if (trailingIcon != null) {
                        {
                            IconButton(onClick = onTrailingIconClick) {
                                Icon(
                                    painter = trailingIcon,
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        null
                    },
                    textStyle = textStyle,
                    modifier = Modifier.fillMaxWidth()
                )
                TextFieldError(
                    modifier = Modifier.fillMaxWidth(),
                    message = errorMessage
                )
            }
        }

        @Composable
        private fun TextFieldError(
            modifier: Modifier = Modifier,
            message: String? = null
        ) {
            AnimatedVisibility(visible = message != null) {
                Text(
                    text = message ?: "",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                )
            }
        }
    }
}