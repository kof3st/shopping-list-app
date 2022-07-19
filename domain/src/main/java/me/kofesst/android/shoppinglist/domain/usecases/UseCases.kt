package me.kofesst.android.shoppinglist.domain.usecases

import me.kofesst.android.shoppinglist.domain.usecases.validation.*

data class UseCases(
    val registerUser: RegisterUser,
    val logInUser: LogInUser,
    val saveSession: SaveSession,
    val restoreSession: RestoreSession,
    val clearSession: ClearSession,
    val saveList: SaveList,
    val getList: GetList,
    val deleteList: DeleteList,
    val validateForEmptyField: ValidateForEmptyField,
    val validateForLength: ValidateForLength,
    val validateForInteger: ValidateForInteger,
    val validateForIntRange: ValidateForIntRange,
    val validateForEmail: ValidateForEmail,
    val validateForPassword: ValidateForPassword
)
