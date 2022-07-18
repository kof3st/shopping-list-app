package me.kofesst.android.shoppinglist.presentation.create_list.create_item

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import me.kofesst.android.shoppinglist.presentation.AppState
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.create_list.NewListViewModel
import me.kofesst.android.shoppinglist.presentation.utils.createEditItemScreenTitle
import me.kofesst.android.shoppinglist.presentation.utils.itemAmountLabel
import me.kofesst.android.shoppinglist.presentation.utils.itemNameLabel
import me.kofesst.android.shoppinglist.presentation.utils.submitItemText
import me.kofesst.android.shoppinglist.ui.components.Buttons
import me.kofesst.android.shoppinglist.ui.components.TextFields

@Composable
fun CreateEditItemScreen(
    itemIndex: Int,
    viewModel: NewListViewModel,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
    CreateEditItemScreenSettings(appState)

    val navController = appState.navController

    val formViewModel = hiltViewModel<CreateEditItemViewModel>()
    val formState = formViewModel.formState
    val isEditing = itemIndex >= 0

    LaunchedEffect(Unit) {
        if (isEditing) {
            val items = viewModel.items
            formViewModel.setEditing(items[itemIndex])
        }
    }

    Box(modifier = modifier) {
        CreateEditForm(
            formState = formState,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) { action ->
            formViewModel.onFormAction(action)
        }
    }

    FormResultListener(
        result = formViewModel.formResult
    ) {
        if (isEditing) {
            viewModel.updateItem(itemIndex, it.item)
        } else {
            viewModel.addItem(it.item)
        }

        navController.navigateUp()
    }
}

@Composable
private fun CreateEditItemScreenSettings(appState: AppState) {
    appState.topBarState.title = createEditItemScreenTitle
    appState.topBarState.visible = true
    appState.topBarState.hasBackButton = true
    appState.topBarState.actions = listOf()
}

@Composable
private fun CreateEditForm(
    formState: CreateEditItemState,
    modifier: Modifier = Modifier,
    onFormAction: (CreateEditItemAction) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 7.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
    ) {
        TextFields.OutlinedTextField(
            value = formState.name,
            onValueChange = {
                onFormAction(
                    CreateEditItemAction.NameChanged(it)
                )
            },
            errorMessage = formState.nameError?.asString(),
            textStyle = MaterialTheme.typography.body1,
            label = itemNameLabel.asString(),
            modifier = Modifier.fillMaxWidth()
        )
        TextFields.OutlinedNumericTextField(
            value = formState.amount,
            onValueChange = {
                onFormAction(
                    CreateEditItemAction.AmountChanged(it)
                )
            },
            errorMessage = formState.amountError?.asString(),
            textStyle = MaterialTheme.typography.body1,
            label = itemAmountLabel.asString(),
            modifier = Modifier.fillMaxWidth()
        )
        Buttons.Button(
            text = submitItemText.asString(),
            modifier = Modifier.fillMaxWidth()
        ) {
            onFormAction(
                CreateEditItemAction.Submit
            )
        }
    }
}

@Composable
private fun FormResultListener(
    result: Flow<CreateEditItemResult>,
    onSuccess: (CreateEditItemResult.Success) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        result.collect {
            when (it) {
                is CreateEditItemResult.Success -> {
                    onSuccess(it)
                }
            }
        }
    }
}