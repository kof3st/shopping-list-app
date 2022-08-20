package me.kofesst.android.shoppinglist.presentation.list.create.result

import android.content.Intent

fun getShareIntent(
    title: String,
    sharing: String
): Intent = Intent.createChooser(
    Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, sharing)
    },
    title
)