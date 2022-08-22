package me.kofesst.android.shoppinglist.presentation.utils

import androidx.compose.runtime.Composable
import java.util.*

@Composable
fun Long.formatHours(): String {
    val suffix: UiText = when {
        this % 100 in 10..19 -> {
            AppText.Case.hoursThirdCase // 15 часов
        }
        else -> {
            when (this % 10) {
                1L -> AppText.Case.hoursFirstCase
                in 2..4 -> AppText.Case.hoursSecondCase // 22 часа
                else -> AppText.Case.hoursThirdCase // 25 часов
            }
        }
    }

    return suffix(this)
}

@Composable
fun Long.formatDate(showTime: Boolean = false): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val monthName = calendar.getMonth()
    val year = calendar.get(Calendar.YEAR)

    var formatted = AppText.Format.dateFormat(dayOfMonth, monthName, year)
    if (showTime) {
        val hours = calendar.get(Calendar.HOUR_OF_DAY).toString()
        val minutes = calendar.get(Calendar.MINUTE).toString()
        formatted = AppText.Format.timeFormat(
            formatted,
            hours.padStart(2, '0'),
            minutes.padStart(2, '0')
        )
    }

    return formatted
}

private val monthCases = listOf(
    AppText.Case.januaryCase,
    AppText.Case.februaryCase,
    AppText.Case.marchCase,
    AppText.Case.aprilCase,
    AppText.Case.mayCase,
    AppText.Case.juneCase,
    AppText.Case.julyCase,
    AppText.Case.augustCase,
    AppText.Case.septemberCase,
    AppText.Case.octoberCase,
    AppText.Case.novemberCase,
    AppText.Case.decemberCase
)

@Composable
fun Calendar.getMonth(): String {
    return monthCases[this.get(Calendar.MONTH)]()
}