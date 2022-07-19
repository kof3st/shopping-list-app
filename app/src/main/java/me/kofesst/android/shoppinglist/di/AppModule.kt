package me.kofesst.android.shoppinglist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kofesst.android.shoppinglist.data.repository.ShoppingListRepositoryImpl
import me.kofesst.android.shoppinglist.domain.repository.ShoppingListRepository
import me.kofesst.android.shoppinglist.domain.usecases.*
import me.kofesst.android.shoppinglist.domain.usecases.validation.*
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
            registerUser = RegisterUser(repository),
            logInUser = LogInUser(repository),
            saveList = SaveList(repository),
            getList = GetList(repository),
            deleteList = DeleteList(repository),
            validateForEmptyField = ValidateForEmptyField(),
            validateForLength = ValidateForLength(),
            validateForInteger = ValidateForInteger(),
            validateForIntRange = ValidateForIntRange(),
            validateForEmail = ValidateForEmail(),
            validateForPassword = ValidateForPassword()
        )
    }
}