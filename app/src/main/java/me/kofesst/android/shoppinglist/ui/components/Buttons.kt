package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
            androidx.compose.material3.Button(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        @Composable
        fun TextButton(
            text: String,
            modifier: Modifier = Modifier,
            onClick: () -> Unit = {}
        ) {
            androidx.compose.material3.TextButton(
                onClick = onClick,
                modifier = modifier
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}