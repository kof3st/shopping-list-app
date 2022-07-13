package me.kofesst.android.shoppinglist.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import me.kofesst.android.shoppinglist.data.BuildConfig
import me.kofesst.android.shoppinglist.data.models.ShoppingListDto
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository

class ShoppingListRepositoryImpl : ShoppingListRepository {
    private val database: FirebaseDatabase get() = Firebase.database(BuildConfig.DB_URL)

    override suspend fun saveList(list: ShoppingList) {
        val listDto = ShoppingListDto.fromDomain(list)
        database.getReference(list.id).setValue(listDto).await()
    }

    override suspend fun getList(id: String): ShoppingList? {
        val reference = database.getReference(id)
        val listDto = reference.get().await().getValue<ShoppingListDto>()
        return listDto?.toDomain()
    }

    override suspend fun deleteList(id: String) {
        val reference = database.getReference(id)
        reference.setValue(null).await()
    }
}