package me.kofesst.android.shoppinglist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kofesst.android.shoppinglist.data.repository.ShoppingListRepositoryImpl
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository
import me.kofesst.android.shoppinglist.domain.usecases.DeleteList
import me.kofesst.android.shoppinglist.domain.usecases.GetList
import me.kofesst.android.shoppinglist.domain.usecases.SaveList
import me.kofesst.android.shoppinglist.domain.usecases.UseCases
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForEmptyField
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForIntRange
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForInteger
import me.kofesst.android.shoppinglist.domain.usecases.validation.ValidateForLength
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(): ShoppingListRepository {
        return ShoppingListRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: ShoppingListRepository): UseCases {
        return UseCases(
            saveList = SaveList(repository),
            getList = GetList(repository),
            deleteList = DeleteList(repository),
            validateForEmptyField = ValidateForEmptyField(),
            validateForLength = ValidateForLength(),
            validateForInteger = ValidateForInteger(),
            validateForIntRange = ValidateForIntRange()
        )
    }
}