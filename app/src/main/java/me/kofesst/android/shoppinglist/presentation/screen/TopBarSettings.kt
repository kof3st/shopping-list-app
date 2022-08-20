package me.kofesst.android.shoppinglist.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import me.kofesst.android.shoppinglist.presentation.utils.UiText

data class TopBarSettings(
    val visible: Boolean = false,
    val hasBackButton: Boolean = false,
    val title: UiText = UiText.Static(""),
    val actions: List<Action> = emptyList()
) {
    data class Action(
        val icon: ImageVector,
        val description: UiText,
        val onClick: @Composable () -> () -> Unit = { {} }
    )
}