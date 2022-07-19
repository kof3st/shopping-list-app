package me.kofesst.android.shoppinglist.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import me.kofesst.android.shoppinglist.data.BuildConfig
import me.kofesst.android.shoppinglist.data.models.ShoppingListDto
import me.kofesst.android.shoppinglist.data.models.UserProfileDto
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository
import me.kofesst.android.shoppinglist.domain.utils.AuthResult

class ShoppingListRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : ShoppingListRepository {
    companion object {
        private val EMAIL_SESSION_KEY = stringPreferencesKey("session_email")
        private val PASSWORD_SESSION_KEY = stringPreferencesKey("session_password")
    }

    private val database: FirebaseDatabase get() = Firebase.database(BuildConfig.DB_URL)

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthResult {
        return try {
            val auth = Firebase.auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return AuthResult.Failed.InvalidUser

            val profile = UserProfileDto.fromFirebase(
                user = user,
                firstName = firstName,
                lastName = lastName
            )

            database.getReference(profile.uid).setValue(profile).await()
            AuthResult.Success.Registered(profile.toDomain())
        } catch (exception: Exception) {
            handleAuthException(exception)
        }
    }

    override suspend fun logIn(email: String, password: String): AuthResult {
        return try {
            val auth = Firebase.auth
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return AuthResult.Failed.InvalidUser

            val reference = database.getReference(user.uid)
            val profile = reference.get().await().getValue<UserProfileDto>()
                ?: return AuthResult.Failed.InvalidUser

            return AuthResult.Success.LoggedIn(profile.toDomain())
        } catch (exception: Exception) {
            handleAuthException(exception)
        }
    }

    override suspend fun saveSession(email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_SESSION_KEY] = email
            preferences[PASSWORD_SESSION_KEY] = password
        }
    }

    override suspend fun restoreSession(): Pair<String, String>? {
        return dataStore.data.map { preferences ->
            val email = preferences[EMAIL_SESSION_KEY] ?: return@map null
            val password = preferences[PASSWORD_SESSION_KEY] ?: return@map null

            email to password
        }.firstOrNull()
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(EMAIL_SESSION_KEY)
            preferences.remove(PASSWORD_SESSION_KEY)
        }
    }

    private fun handleAuthException(exception: Exception) = when (exception) {
        is FirebaseAuthInvalidUserException -> AuthResult.Failed.InvalidUser
        is FirebaseAuthInvalidCredentialsException -> AuthResult.Failed.IncorrectPassword
        is FirebaseAuthUserCollisionException -> AuthResult.Failed.EmailAlreadyExists
        else -> AuthResult.Failed.Unexpected(exception)
    }

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