package me.kofesst.android.shoppinglist.presentation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.*
import me.kofesst.android.shoppinglist.presentation.create_list.NewListScreen
import me.kofesst.android.shoppinglist.presentation.create_list.NewListViewModel
import me.kofesst.android.shoppinglist.presentation.create_list.create_item.CreateEditItemScreen
import me.kofesst.android.shoppinglist.presentation.home.HomeScreen
import me.kofesst.android.shoppinglist.presentation.list_details.ListDetailsScreen
import me.kofesst.android.shoppinglist.presentation.list_details.ListDetailsViewModel
import me.kofesst.android.shoppinglist.presentation.utils.UiText

enum class Screen(
    val routeName: String,
    val args: List<NamedNavArgument> = listOf(),
    val content: @Composable (ViewModel?, NavBackStackEntry, Modifier) -> Unit,
    val viewModelProducer: (@Composable (NavController, NavBackStackEntry) -> ViewModel)? = null,
    val bottomBarConfig: ScreenBottomBarConfig? = null
) {
    HOME(
        routeName = "home",
        content = { _, _, modifier ->
            HomeScreen(
                modifier = modifier
            )
        }
    ),
    LIST_DETAILS(
        routeName = "details",
        content = { viewModel, backStack, modifier ->
            val listId = backStack.arguments?.getString("listId") ?: ""
            ListDetailsScreen(
                listId = listId,
                viewModel = viewModel as ListDetailsViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<ListDetailsViewModel>() },
        args = listOf(
            navArgument("listId") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ),
    NEW_LIST(
        routeName = "new_list",
        content = { viewModel, _, modifier ->
            NewListScreen(
                viewModel = viewModel as NewListViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<NewListViewModel>() }
    ),
    CREATE_EDIT_ITEM(
        routeName = "create_edit_item",
        content = { viewModel, backStack, modifier ->
            val itemIndex = backStack.arguments?.getInt("itemIndex") ?: -1
            CreateEditItemScreen(
                itemIndex = itemIndex,
                viewModel = viewModel as NewListViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { navController, backStack ->
            val newListScreenEntry = remember(backStack) {
                navController.getBackStackEntry(NEW_LIST.routeName)
            }
            hiltViewModel<NewListViewModel>(newListScreenEntry)
        },
        args = listOf(
            navArgument(name = "itemIndex") {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    )
}

val Screen.route: String
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

fun Screen.withArgs(vararg arguments: Pair<String, Any>): String {
    val argsRoute = arguments.fold(route) { acc, (key, value) ->
        acc
            .replace("{$key}", value.toString(), true)
            .replace(key, value.toString(), true)
    }

    return args.fold(argsRoute) { acc, arg ->
        acc.replace(
            "{${arg.name}}",
            arg.argument.defaultValue?.toString() ?: ""
        )
    }
}

data class ScreenBottomBarConfig(
    @DrawableRes val iconResId: Int,
    val title: UiText
)