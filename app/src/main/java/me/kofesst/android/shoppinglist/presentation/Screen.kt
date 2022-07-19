package me.kofesst.android.shoppinglist.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.*
import me.kofesst.android.shoppinglist.presentation.auth.AuthScreen
import me.kofesst.android.shoppinglist.presentation.auth.AuthViewModel
import me.kofesst.android.shoppinglist.presentation.create_list.NewListScreen
import me.kofesst.android.shoppinglist.presentation.create_list.NewListViewModel
import me.kofesst.android.shoppinglist.presentation.create_list.create_item.CreateEditItemScreen
import me.kofesst.android.shoppinglist.presentation.create_list.result.NewListResultScreen
import me.kofesst.android.shoppinglist.presentation.home.HomeScreen
import me.kofesst.android.shoppinglist.presentation.home.HomeViewModel
import me.kofesst.android.shoppinglist.presentation.list_details.ListDetailsScreen
import me.kofesst.android.shoppinglist.presentation.list_details.ListDetailsViewModel

enum class Screen(
    val routeName: String,
    val args: List<NamedNavArgument> = listOf(),
    val content: @Composable (ViewModel?, NavBackStackEntry, Modifier) -> Unit,
    val viewModelProducer: (@Composable (NavController, NavBackStackEntry) -> ViewModel)? = null
) {
    AUTH(
        routeName = Constants.Auth.ROUTE_NAME,
        content = { viewModel, _, modifier ->
            AuthScreen(
                viewModel = viewModel as AuthViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<AuthViewModel>() }
    ),
    HOME(
        routeName = Constants.Home.ROUTE_NAME,
        content = { viewModel, _, modifier ->
            HomeScreen(
                viewModel = viewModel as HomeViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<HomeViewModel>() }
    ),
    LIST_DETAILS(
        routeName = Constants.ListDetails.ROUTE_NAME,
        content = { viewModel, backStack, modifier ->
            val listId = backStack.arguments?.getString(
                Constants.ListDetails.LIST_ID_ARG
            ) ?: ""
            ListDetailsScreen(
                listId = listId,
                viewModel = viewModel as ListDetailsViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<ListDetailsViewModel>() },
        args = listOf(
            navArgument(Constants.ListDetails.LIST_ID_ARG) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ),
    NEW_LIST(
        routeName = Constants.NewList.ROUTE_NAME,
        content = { viewModel, _, modifier ->
            NewListScreen(
                viewModel = viewModel as NewListViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { _, _ -> hiltViewModel<NewListViewModel>() }
    ),
    CREATE_EDIT_ITEM(
        routeName = Constants.CreateEditItem.ROUTE_NAME,
        content = { viewModel, backStack, modifier ->
            val itemIndex = backStack.arguments?.getInt(
                Constants.CreateEditItem.ITEM_INDEX_ARG
            ) ?: -1
            CreateEditItemScreen(
                itemIndex = itemIndex,
                viewModel = viewModel as NewListViewModel,
                modifier = modifier
            )
        },
        viewModelProducer = { navController, backStack ->
            val newListScreenEntry = remember(backStack) {
                navController.getBackStackEntry(Constants.NewList.ROUTE_NAME)
            }
            hiltViewModel<NewListViewModel>(newListScreenEntry)
        },
        args = listOf(
            navArgument(name = Constants.CreateEditItem.ITEM_INDEX_ARG) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ),
    NEW_LIST_RESULT(
        routeName = Constants.NewListResult.ROUTE_NAME,
        content = { _, backStack, modifier ->
            val listId = backStack.arguments?.getString(
                Constants.NewListResult.LIST_ID_ARG
            ) ?: "null"
            NewListResultScreen(
                listId = listId,
                modifier = modifier
            )
        },
        args = listOf(
            navArgument(Constants.NewListResult.LIST_ID_ARG) {
                type = NavType.StringType
                defaultValue = "null"
            }
        )
    );

    class Constants private constructor() {
        class Auth private constructor() {
            companion object {
                const val ROUTE_NAME = "auth"
            }
        }

        class Home private constructor() {
            companion object {
                const val ROUTE_NAME = "home"
            }
        }

        class ListDetails private constructor() {
            companion object {
                const val ROUTE_NAME = "details"
                const val LIST_ID_ARG = "listId"
            }
        }

        class NewList private constructor() {
            companion object {
                const val ROUTE_NAME = "newList"
            }
        }

        class CreateEditItem private constructor() {
            companion object {
                const val ROUTE_NAME = "createEditItem"
                const val ITEM_INDEX_ARG = "itemIndex"
            }
        }

        class NewListResult private constructor() {
            companion object {
                const val ROUTE_NAME = "newListResult"
                const val LIST_ID_ARG = "listId"
            }
        }
    }
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