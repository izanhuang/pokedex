package com.example.pokedex.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.pokedex.R

object AppFont {
    val pressStart2PFontFamily = FontFamily(
        Font(R.font.pressstart2p_regular, FontWeight.Normal)
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.pressStart2PFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.pressStart2PFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.pressStart2PFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.pressStart2PFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.pressStart2PFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.pressStart2PFontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.pressStart2PFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.pressStart2PFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.pressStart2PFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.pressStart2PFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.pressStart2PFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.pressStart2PFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.pressStart2PFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.pressStart2PFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.pressStart2PFontFamily)
)