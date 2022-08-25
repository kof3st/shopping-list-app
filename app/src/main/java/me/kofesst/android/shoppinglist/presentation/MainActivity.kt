package me.kofesst.android.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModelOwner = compositionLocalOf<ViewModelStoreOwner> { this }
                    val viewModel = hiltViewModel<MainViewModel>(
                        viewModelStoreOwner = viewModelOwner.current
                    )
                    val appState = rememberAppState()
                    CompositionLocalProvider(
                        LocalAppState provides appState,
                        LocalViewModelStoreOwner provides viewModelOwner.current
                    ) {
                        ShoppingListApp(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}