package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Buttons private constructor() {
    companion object {
        @Composable
        fun Button(
            text: String,
            modifier: Modifier = Modifier,
            onClick: () -> Unit = {}
        ) {
            androidx.compose.material.Button(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }

        @Composable
        fun TextButton(
            text: String,
            modifier: Modifier = Modifier,
            onClick: () -> Unit = {}
        ) {
            androidx.compose.material.TextButton(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}