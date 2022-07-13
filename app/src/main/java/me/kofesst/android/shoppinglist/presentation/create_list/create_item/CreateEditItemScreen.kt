package me.kofesst.android.shoppinglist.presentation.create_list.create_item

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.kofesst.android.shoppinglist.presentation.LocalAppState
import me.kofesst.android.shoppinglist.presentation.create_list.NewListViewModel
import me.kofesst.android.shoppinglist.ui.components.ValidatedOutlinedTextField

@Composable
fun CreateEditItemScreen(
    itemIndex: Int,
    viewModel: NewListViewModel,
    modifier: Modifier = Modifier
) {
    val appState = LocalAppState.current
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            ValidatedOutlinedTextField(
                value = formState.name,
                onValueChange = {
                    formViewModel.onFormAction(
                        CreateEditItemAction.NameChanged(it)
                    )
                },
                errorMessage = formState.nameError?.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            ValidatedOutlinedTextField(
                value = formState.amountStr,
                onValueChange = {
                    formViewModel.onFormAction(
                        CreateEditItemAction.AmountChanged(it)
                    )
                },
                errorMessage = formState.amountError?.asString(),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    formViewModel.onFormAction(
                        CreateEditItemAction.Submit
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Сохранить")
            }
        }
    }

    LaunchedEffect(Unit) {
        formViewModel.formResult.collect {
            when (it) {
                is CreateEditItemResult.Success -> {
                    if (isEditing) {
                        viewModel.updateItem(itemIndex, it.item)
                    } else {
                        viewModel.addItem(it.item)
                    }

                    navController.navigateUp()
                }
            }
        }
    }
}