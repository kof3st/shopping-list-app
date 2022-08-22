package me.kofesst.android.shoppinglist.domain.repository

import kotlinx.coroutines.CoroutineScope

interface NotificationsRepository {
    fun subscribeToDatabaseChanges(
        coroutineScope: CoroutineScope,
        onChildChanged: suspend (String) -> Unit
    )
    fun unsubscribeFromDatabaseChanges()
}