package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.android.shoppinglist.presentation.utils.LoadingState
import me.kofesst.android.shoppinglist.presentation.utils.UiText

@Composable
fun <T : Any> LoadingStateHandler(
    state: LoadingState<T>,
    content: @Composable BoxScope.(T) -> Unit,
    modifier: Modifier = Modifier,
    loadingPanel: @Composable BoxScope.() -> Unit = {
        LoadingPanel()
    },
    errorMessageProducer: (Exception) -> UiText = { exception ->
        UiText.Static(exception.toString())
    },
    errorPanel: @Composable BoxScope.(UiText) -> Unit = { errorMessage ->
        ErrorPanel(
            text = errorMessage()
        )
    }
) {
    Box(modifier = modifier) {
        when (state) {
            is LoadingState.Idle, is LoadingState.Loading -> {
                loadingPanel()
            }
            is LoadingState.Failed -> {
                errorPanel(
                    errorMessageProducer(
                        state.exception
                    )
                )
            }
            is LoadingState.Loaded -> {
                val value = state.value
                if (value == null) {
                    errorPanel(
                        errorMessageProducer(
                            NullPointerException("Resource value is null")
                        )
                    )
                } else {
                    content(value)
                }
            }
        }
    }
}