package me.kofesst.android.shoppinglist.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import me.kofesst.android.shoppinglist.R

val appFontFamily = FontFamily(
    // Normal
    Font(R.font.exo2_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.exo2_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.exo2_extra_light, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.exo2_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.exo2_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.exo2_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.exo2_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.exo2_extra_bold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.exo2_black, FontWeight.Black, FontStyle.Normal),

    // Italic
    Font(R.font.exo2_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.exo2_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.exo2_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.exo2_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.exo2_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.exo2_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.exo2_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.exo2_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.exo2_black_italic, FontWeight.Black, FontStyle.Italic)
)

val Typography = Typography(
    defaultFontFamily = appFontFamily
)