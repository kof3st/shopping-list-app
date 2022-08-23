package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DividerWithText(
    text: String,
    modifier: Modifier = Modifier,
    inUppercase: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textPadding: Dp = 10.dp,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(textPadding),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.weight(1.0f)
        )
        Text(
            text = text.run {
                if (inUppercase) this.uppercase()
                else this
            },
            style = textStyle,
            fontWeight = FontWeight.Light
        )
        Divider(
            modifier = Modifier.weight(1.0f)
        )
    }
}