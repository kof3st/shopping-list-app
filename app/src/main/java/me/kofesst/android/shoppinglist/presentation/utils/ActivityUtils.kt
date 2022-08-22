package me.kofesst.android.shoppinglist.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

val Context.activity: ComponentActivity? get() {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}