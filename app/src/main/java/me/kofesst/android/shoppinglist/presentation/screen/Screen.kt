package me.kofesst.android.shoppinglist.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.auth.AuthScreen
import me.kofesst.android.shoppinglist.presentation.list.create.NewListScreen
import me.kofesst.android.shoppinglist.presentation.list.create.item.NewListItemScreen
import me.kofesst.android.shoppinglist.presentation.list.create.result.NewListResultScreen
import me.kofesst.android.shoppinglist.presentation.home.HomeScreen
import me.kofesst.android.shoppinglist.presentation.list.complete.ListDetailsScreen
import me.kofesst.android.shoppinglist.presentation.lists.ListsScreen

abstract class Screen<ScreenViewModel : ViewModel>(
    val routeName: String,
    val args: List<NamedNavArgument> = emptyList(),
    val topBarSettings: TopBarSettings = TopBarSettings(),
    val bottomBarSettings: BottomBarSettings = BottomBarSettings()
) {
    companion object {
        val Auth = AuthScreen(ScreenConstants.Auth.ROUTE_NAME)
        val Home = HomeScreen(ScreenConstants.Home.ROUTE_NAME)
        val ListDetails = ListDetailsScreen(ScreenConstants.ListDetails.ROUTE_NAME)
        val NewList = NewListScreen(ScreenConstants.NewList.ROUTE_NAME)
        val NewListItem = NewListItemScreen(ScreenConstants.NewListItem.ROUTE_NAME)
        val NewListResult = NewListResultScreen(ScreenConstants.NewListResult.ROUTE_NAME)
        val ListsScreen = ListsScreen(ScreenConstants.Lists.ROUTE_NAME)

        val values = listOf(
            Auth,
            Home,
            ListDetails,
            NewList,
            NewListItem,
            NewListResult,
            ListsScreen
        )
    }

    protected abstract val viewModelProducer:
            @Composable (NavHostController, NavBackStackEntry) -> ScreenViewModel

    protected abstract val content:
            @Composable BoxScope.(NavBackStackEntry, ScreenViewModel, Modifier) -> Unit

    private fun getArgDefaultValue(name: String): Any? = args.firstOrNull { arg ->
        arg.name == name
    }?.argument?.defaultValue

    protected fun getStringArg(
        name: String,
        backStackEntry: NavBackStackEntry
    ): String = backStackEntry.arguments?.getString(name) ?: getArgDefaultValue(
        name = name
    )?.toString() ?: ""

    protected fun getIntArg(
        name: String,
        backStackEntry: NavBackStackEntry
    ): Int = backStackEntry.arguments?.getInt(
        name,
        getArgDefaultValue(
            name = name
        )?.toString()?.toIntOrNull() ?: -1
    ) ?: getArgDefaultValue(
        name = name
    )?.toString()?.toIntOrNull() ?: -1

    @Composable
    private fun ApplyScaffoldSettings() {
        val appState = LocalAppState.current
        appState.topBarState.applySettings(topBarSettings)
        appState.bottomBarState.applySettings(bottomBarSettings)
    }

    @Composable
    fun ScreenContent(
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        modifier: Modifier = Modifier
    ) {
        ApplyScaffoldSettings()

        val viewModel = viewModelProducer(navController, backStackEntry)
        Box(modifier = modifier) {
            content(
                this,
                backStackEntry,
                viewModel,
                Modifier
            )
        }
    }
}

val Screen<*>.route: String
    get() = buildString {
        append(routeName)
        args.filter { !it.argument.isNullable }.forEach {
            append("/{${it.name}}")
        }

        args.filter { it.argument.isNullable }.also {
            if (it.isEmpty()) return@also

            append(
                "?${
                    it.joinToString("&") { arg ->
                        "${arg.name}={${arg.name}}"
                    }
                }"
            )
        }
    }

fun Screen<*>.withArgs(vararg arguments: Pair<String, Any>): String {
    val argsRoute = arguments.fold(route) { acc, (key, value) ->
        acc.replace("{$key}", value.toString(), true)
    }

    return args.fold(argsRoute) { acc, arg ->
        acc.replace(
            "{${arg.name}}",
            arg.argument.defaultValue?.toString() ?: ""
        )
    }
}