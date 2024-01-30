package com.nikadmin.diffusiongallery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.nikadmin.diffusiongallery.R

val SofiaSans = FontFamily(
    Font(R.font.sofiasans_black, FontWeight.Black),
    Font(R.font.sofiasans_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.sofiasans_bold, FontWeight.Bold),
    Font(R.font.sofiasans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.sofiasans_extrabold, FontWeight.ExtraBold),
    Font(R.font.sofiasans_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.sofiasans_extralight, FontWeight.ExtraLight),
    Font(R.font.sofiasans_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.sofiasans_light, FontWeight.Light),
    Font(R.font.sofiasans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.sofiasans_medium, FontWeight.Medium),
    Font(R.font.sofiasans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.sofiasans_regular, FontWeight.Normal),
    Font(R.font.sofiasans_semibold, FontWeight.SemiBold),
    Font(R.font.sofiasans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.sofiasans_thin, FontWeight.Thin),
    Font(R.font.sofiasans_thinitalic, FontWeight.Thin, FontStyle.Italic),
)

// Set of Material typography styles to start with
private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = SofiaSans),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = SofiaSans),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = SofiaSans),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = SofiaSans),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = SofiaSans),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = SofiaSans),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = SofiaSans),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = SofiaSans),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = SofiaSans),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = SofiaSans),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = SofiaSans),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = SofiaSans),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = SofiaSans),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = SofiaSans),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = SofiaSans),
)