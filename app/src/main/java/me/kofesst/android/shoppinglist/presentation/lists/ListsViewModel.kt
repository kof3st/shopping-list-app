package me.kofesst.android.shoppinglist.presentation.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.models.done.DoneShoppingList
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import me.kofesst.android.shoppinglist.presentation.SuspendViewModel
import me.kofesst.android.shoppinglist.presentation.utils.LoadingState
import javax.inject.Inject

typealias UserSelfLists = Pair<List<ShoppingList>, List<DoneShoppingList>>

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val useCases: UseCases
) : SuspendViewModel() {
    private val _lists = mutableStateOf<LoadingState<UserSelfLists>>(LoadingState.Idle())
    val lists: State<LoadingState<UserSelfLists>> get() = _lists

    fun loadLists() {
        runSuspend {
            _lists.value = LoadingState.Loading()
            _lists.value = LoadingState.Loaded(
                value = useCases.getSelfActiveLists() to useCases.getSelfDoneLists()
            )
        }
    }
}