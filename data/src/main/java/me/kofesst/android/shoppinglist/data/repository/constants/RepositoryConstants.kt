package me.kofesst.android.shoppinglist.data.repository.constants

sealed class RepositoryConstants private constructor() {
    companion object {
        const val USERS_DB_PATH = "users"
        const val LISTS_DB_PATH = "lists"
    }
}