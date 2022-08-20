package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import me.kofesst.android.shoppinglist.presentation.SuspendViewModel

@Composable
fun LoadingHandler(
    viewModel: SuspendViewModel
) {
    val isLoading by viewModel.loadingState
    if (isLoading) {
        LoadingPanel()
    }
}