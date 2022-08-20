package me.kofesst.android.shoppinglist.presentation.utils

import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun Long.formatHours(): String {
    val suffix: UiText = when {
        this % 100 in 10..19 -> {
            hoursThirdCase // 15 часов
        }
        else -> {
            when (this % 10) {
                1L -> hoursFirstCase
                in 2..4 -> hoursSecondCase // 22 часа
                else -> hoursThirdCase // 25 часов
            }
        }
    }

    return suffix.asString(this)
}

@Composable
fun Long.formatDate(showTime: Boolean = false): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val monthName = calendar.getMonth()
    val year = calendar.get(Calendar.YEAR)

    var formatted = dateFormat.asString(dayOfMonth, monthName, year)
    if (showTime) {
        val hours = calendar.get(Calendar.HOUR_OF_DAY).toString()
        val minutes = calendar.get(Calendar.MINUTE).toString()
        formatted = timeFormat.asString(
            formatted,
            hours.padStart(2, '0'),
            minutes.padStart(2, '0')
        )
    }

    return formatted
}

private val monthCases = listOf(
    januaryCase,
    februaryCase,
    marchCase,
    aprilCase,
    mayCase,
    juneCase,
    julyCase,
    augustCase,
    septemberCase,
    octoberCase,
    novemberCase,
    decemberCase
)

@Composable
fun Calendar.getMonth(): String {
    return monthCases[this.get(Calendar.MONTH)].asString()
}