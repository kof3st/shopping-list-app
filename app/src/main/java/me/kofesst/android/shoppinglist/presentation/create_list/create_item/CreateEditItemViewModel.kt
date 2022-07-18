package me.kofesst.android.shoppinglist.presentation.create_list.create_item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidationResult
import me.kofesst.android.shoppinglist.presentation.utils.Constraints
import me.kofesst.android.shoppinglist.presentation.utils.errorMessage
import javax.inject.Inject

@HiltViewModel
class CreateEditItemViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    var formState by mutableStateOf(CreateEditItemState())

    private val validationChannel = Channel<CreateEditItemResult>()
    val formResult = validationChannel.receiveAsFlow()

    fun setEditing(item: ShoppingItem) {
        formState = CreateEditItemState(
            name = item.name,
            amount = item.amount
        )
    }

    fun onFormAction(action: CreateEditItemAction) {
        when (action) {
            is CreateEditItemAction.NameChanged -> {
                formState = formState.copy(name = action.name)
            }
            is CreateEditItemAction.AmountChanged -> {
                formState = formState.copy(amount = action.amount)
            }
            CreateEditItemAction.Submit -> {
                onSubmit()
            }
        }
    }

    private fun onSubmit() {
        val nameResult = useCases.validateForEmptyField(
            value = formState.name
        ) + useCases.validateForLength(
            value = formState.name,
            lengthRange = Constraints.ShoppingItem.NAME_LENGTH_RANGE
        )

        val amountResult = useCases.validateForIntRange(
            value = formState.amount,
            range = Constraints.ShoppingItem.AMOUNT_RANGE
        )

        formState = formState.copy(
            nameError = nameResult.errorMessage,
            amountError = amountResult.errorMessage
        )

        val hasError = listOf(
            nameResult,
            amountResult
        ).any { it !is ValidationResult.Success }

        if (!hasError) {
            viewModelScope.launch {
                val item = ShoppingItem(
                    name = formState.name,
                    amount = formState.amount
                )
                validationChannel.send(
                    CreateEditItemResult.Success(item)
                )
            }
        }
    }
}