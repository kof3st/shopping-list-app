package me.kofesst.android.shoppinglist.presentation.utils

sealed class LoadingState<T : Any> {
    data class Failed<T : Any>(val exception: Exception) : LoadingState<T>()
    data class Loaded<T : Any>(val value: T?) : LoadingState<T>()
    class Idle<T : Any> : LoadingState<T>()
    class Loading <T : Any>: LoadingState<T>()
}