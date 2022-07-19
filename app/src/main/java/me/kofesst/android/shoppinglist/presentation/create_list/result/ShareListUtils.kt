package me.kofesst.android.shoppinglist.presentation.create_list.result

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