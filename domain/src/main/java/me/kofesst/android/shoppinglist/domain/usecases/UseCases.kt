package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForEmptyField
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForIntRange
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForInteger
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForLength

data class UseCases(
    val saveList: SaveList,
    val getList: GetList,
    val deleteList: DeleteList,
    val validateForEmptyField: ValidateForEmptyField,
    val validateForLength: ValidateForLength,
    val validateForInteger: ValidateForInteger,
    val validateForIntRange: ValidateForIntRange
)
