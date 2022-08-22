package me.kofesst.android.shoppinglist.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.kofesst.android.shoppinglist.data.BuildConfig
import me.kofesst.android.shoppinglist.data.models.ShoppingListDto
import me.kofesst.android.shoppinglist.data.repository.constants.RepositoryConstants.Companion.LISTS_DB_PATH
import me.kofesst.android.shoppinglist.data.utils.ChildReferenceEvent
import me.kofesst.android.shoppinglist.data.utils.childChangesFlow
import me.kofesst.android.shoppinglist.domain.repository.NotificationsRepository
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class NotificationsRepositoryImpl(
    private val mainRepository: ShoppingListRepository
) : NotificationsRepository {
    private val database: FirebaseDatabase get() = Firebase.database(BuildConfig.DB_URL)
    private var currentUserUid: String = ""
    private var changesJob: Job? = null

    override fun subscribeToDatabaseChanges(
        coroutineScope: CoroutineScope,
        onChildChanged: suspend (String) -> Unit
    ) {
        changesJob = coroutineScope.launch {
            currentUserUid = mainRepository.getLoggedUserUid() ?: return@launch
            val listsReference = database.getReference(LISTS_DB_PATH)
            listsReference.childChangesFlow().collect { event ->
                when (event) {
                    is ChildReferenceEvent.ChildChanged -> {
                        val snapshot = event.snapshot
                        val listDto = snapshot.getValue<ShoppingListDto>() ?: return@collect
                        if (listDto.authorUid != currentUserUid) return@collect
                        onChildChanged(listDto.id)
                    }
                    ChildReferenceEvent.Other -> Unit
                }
            }
        }
    }

    override fun unsubscribeFromDatabaseChanges() {
        changesJob?.cancel()
    }
}