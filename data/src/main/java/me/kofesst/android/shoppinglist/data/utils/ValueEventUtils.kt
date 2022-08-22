package me.kofesst.android.shoppinglist.data.utils

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

suspend fun DatabaseReference.childChangesFlow() = callbackFlow {
    val listener = addChildEventListener(object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            trySendBlocking(ChildReferenceEvent.Other)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            trySendBlocking(ChildReferenceEvent.ChildChanged(snapshot))
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            trySendBlocking(ChildReferenceEvent.Other)
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            trySendBlocking(ChildReferenceEvent.Other)
        }

        override fun onCancelled(error: DatabaseError) {
            trySendBlocking(ChildReferenceEvent.Other)
        }

    })
    awaitClose {
        removeEventListener(listener)
    }
}

sealed class ChildReferenceEvent {
    data class ChildChanged(val snapshot: DataSnapshot) : ChildReferenceEvent()
    object Other : ChildReferenceEvent()
}