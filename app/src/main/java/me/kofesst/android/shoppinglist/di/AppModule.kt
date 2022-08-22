package me.kofesst.android.shoppinglist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.kofesst.android.shoppinglist.data.repository.NotificationsRepositoryImpl
import me.kofesst.android.shoppinglist.data.repository.ShoppingListRepositoryImpl
import me.kofesst.android.shoppinglist.domain.repository.NotificationsRepository
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("app_preferences")
        }
    }

    @Provides
    @Singleton
    fun provideRepository(dataStore: DataStore<Preferences>): ShoppingListRepository {
        return ShoppingListRepositoryImpl(
            dataStore = dataStore
        )
    }

    @Provides
    @Singleton
    fun provideNotificationsRepository(
        mainRepository: ShoppingListRepository
    ): NotificationsRepository {
        return NotificationsRepositoryImpl(mainRepository)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: ShoppingListRepository): UseCases {
        return UseCases(repository)
    }
}