package me.kofesst.android.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.kofesst.android.shoppinglist.ui.theme.ShoppingListAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val appState = rememberAppState()
                    CompositionLocalProvider(LocalAppState provides appState) {
                        ShoppingListApp()
                    }
                }
            }
        }
    }
}