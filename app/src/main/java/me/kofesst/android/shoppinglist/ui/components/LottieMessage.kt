package me.kofesst.android.shoppinglist.ui.components

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun LottieMessage(
    @RawRes lottieRes: Int,
    message: String,
    lottieSize: Dp = 256.dp,
    lottiePadding: Dp = 10.dp,
    messageStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    shouldFillBackground: Boolean = false
) {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieRes)
    )
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (shouldFillBackground) {
                    Modifier.background(MaterialTheme.colorScheme.background)
                } else {
                    Modifier
                }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = lottiePadding,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier.align(Alignment.Center)
        ) {
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
                modifier = Modifier.size(lottieSize)
            )
            Text(
                text = message,
                style = messageStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}