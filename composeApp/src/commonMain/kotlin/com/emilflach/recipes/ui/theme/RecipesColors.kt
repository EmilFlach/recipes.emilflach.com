@file:Suppress("unused")

package com.emilflach.recipes.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Light theme palette
val LightBrandPrimary = Color(0xFFF14A00) // Bright orange (brand color)
val LightBackground = Color(0xFFFAFAFA) // Very light gray
val LightSurface1 = Color(0xFFFFFFFF) // White
val LightSurface2 = Color(0xFFF5F5F5) // Light gray
val LightSurface3 = Color(0xFFEEEEEE) // Slightly darker gray
val LightDisabled = Color(0xFFEEEEEE) // Light gray for disabled state
val LightForeground = Color(0xFF212121) // Very dark gray for text
val LightForegroundSupport = Color(0xFF757575) // Medium gray for support text
val LightForegroundDisabled = Color(0xFFBDBDBD) // Light gray for disabled text

// Dark theme palette
val DarkBrandPrimary = Color(0xFFF14A00) // Bright orange (brand color)
val DarkBackground = Color(0xFF0A0119) // Darkened purple
val DarkSurface1 = Color(0xFF150038) // Darkened slightly lighter purple
val DarkSurface2 = Color(0xFF3A0055) // Darkened medium purple
val DarkSurface3 = Color(0xFF450065) // Darkened lighter purple
val DarkDisabled = Color(0xFF2A0048) // Darkened disabled state with purple hue
val DarkForeground = Color(0xFFF5F5F5) // Almost white for better readability
val DarkForegroundSupport = Color(0xFFB0B0B0) // Darkened light purple for support text
val DarkForegroundDisabled = Color(0xFF705080) // Darkened disabled text with purple hue

// Neutral palette
val Neutral50 = Color(0xFFFAFAFA)
val Neutral100 = Color(0xFFF5F5F5)
val Neutral200 = Color(0xFFEEEEEE)
val Neutral300 = Color(0xFFE0E0E0)
val Neutral400 = Color(0xFFBDBDBD)
val Neutral500 = Color(0xFF9E9E9E)
val Neutral600 = Color(0xFF757575)
val Neutral700 = Color(0xFF616161)
val Neutral800 = Color(0xFF424242)
val Neutral900 = Color(0xFF212121)

// Contextual colors
val InfoLight = Color(0xFF90CAF9)
val Info = Color(0xFF2196F3)
val InfoDark = Color(0xFF0D47A1)

val SuccessLight = Color(0xFFA5D6A7)
val Success = Color(0xFF4CAF50)
val SuccessDark = Color(0xFF1B5E20)

val WarningLight = Color(0xFFFFE082)
val Warning = Color(0xFFFFC107)
val WarningDark = Color(0xFFF57F17)

val DangerLight = Color(0xFFEF9A9A)
val Danger = Color(0xFFF44336)
val DangerDark = Color(0xFFB71C1C)

/**
 * Semantic color system for Recipes app
 */
@Immutable
class RecipesColors(
    // Background Colors
    val backgroundPage: Color,
    val backgroundSurface1: Color,
    val backgroundSurface2: Color,
    val backgroundSurface1Hover: Color,
    val backgroundSurface1Pressed: Color,

    val backgroundBrand: Color,
    val backgroundBrandSubtle: Color,
    val backgroundBrandHover: Color,
    val backgroundBrandPressed: Color,

    val backgroundInfo: Color,
    val backgroundInfoSubtle: Color,
    val backgroundSuccess: Color,
    val backgroundSuccessSubtle: Color,
    val backgroundWarning: Color,
    val backgroundWarningSubtle: Color,
    val backgroundDanger: Color,
    val backgroundDangerSubtle: Color,

    val backgroundDisabled: Color,
    val backgroundSelected: Color,
    val backgroundLoading: Color,

    // On Background Colors
    val onBackgroundBrand: Color,
    val onBackgroundBrandSubtle: Color,
    val onBackgroundInfo: Color,
    val onBackgroundInfoSubtle: Color,
    val onBackgroundSuccess: Color,
    val onBackgroundSuccessSubtle: Color,
    val onBackgroundWarning: Color,
    val onBackgroundWarningSubtle: Color,
    val onBackgroundDanger: Color,
    val onBackgroundDangerSubtle: Color,

    // Foreground Colors
    val foregroundDefault: Color,
    val foregroundSupport: Color,
    val foregroundBrand: Color,
    val foregroundInfo: Color,
    val foregroundSuccess: Color,
    val foregroundWarning: Color,
    val foregroundDanger: Color,
    val foregroundDisabled: Color,

    // Border Colors
    val borderDefault: Color,
    val borderStrong: Color,
    val borderBrand: Color,
    val borderInfo: Color,
    val borderSuccess: Color,
    val borderWarning: Color,
    val borderDanger: Color,
    val borderDisabled: Color,
    val borderFocus: Color,
    val borderSeparator: Color,

    // Link Colors
    val linkDefault: Color,
    val linkHover: Color,
    val linkPressed: Color,
    val linkVisited: Color,

    // Is Dark Theme
    val isDark: Boolean
) {
    companion object {
        // Light theme colors
        val Light = RecipesColors(
            // Background Colors
            backgroundPage = LightBackground,
            backgroundSurface1 = LightSurface1,
            backgroundSurface2 = LightSurface2,
            backgroundSurface1Hover = LightSurface2,
            backgroundSurface1Pressed = LightSurface3,

            backgroundBrand = LightBrandPrimary,
            backgroundBrandSubtle = LightBrandPrimary.copy(alpha = 0.15f),
            backgroundBrandHover = LightBrandPrimary.copy(alpha = 0.8f),
            backgroundBrandPressed = LightBrandPrimary.copy(alpha = 0.9f),

            backgroundInfo = Info,
            backgroundInfoSubtle = InfoLight.copy(alpha = 0.15f),
            backgroundSuccess = Success,
            backgroundSuccessSubtle = SuccessLight.copy(alpha = 0.15f),
            backgroundWarning = Warning,
            backgroundWarningSubtle = WarningLight.copy(alpha = 0.15f),
            backgroundDanger = Danger,
            backgroundDangerSubtle = DangerLight.copy(alpha = 0.15f),

            backgroundDisabled = LightDisabled,
            backgroundSelected = LightBrandPrimary.copy(alpha = 0.1f),
            backgroundLoading = LightSurface3,

            // On Background Colors
            onBackgroundBrand = Color.White,
            onBackgroundBrandSubtle = LightBrandPrimary,
            onBackgroundInfo = Color.White,
            onBackgroundInfoSubtle = Info,
            onBackgroundSuccess = Color.White,
            onBackgroundSuccessSubtle = Success,
            onBackgroundWarning = LightForeground,
            onBackgroundWarningSubtle = WarningDark,
            onBackgroundDanger = Color.White,
            onBackgroundDangerSubtle = Danger,

            // Foreground Colors
            foregroundDefault = LightForeground,
            foregroundSupport = LightForegroundSupport,
            foregroundBrand = LightBrandPrimary,
            foregroundInfo = Info,
            foregroundSuccess = Success,
            foregroundWarning = Warning,
            foregroundDanger = Danger,
            foregroundDisabled = LightForegroundDisabled,

            // Border Colors
            borderDefault = LightSurface3,
            borderStrong = LightForegroundSupport,
            borderBrand = LightBrandPrimary,
            borderInfo = Info,
            borderSuccess = Success,
            borderWarning = Warning,
            borderDanger = Danger,
            borderDisabled = LightDisabled,
            borderFocus = LightBrandPrimary,
            borderSeparator = LightSurface2,

            // Link Colors
            linkDefault = LightBrandPrimary,
            linkHover = LightBrandPrimary.copy(alpha = 0.8f),
            linkPressed = LightBrandPrimary.copy(alpha = 0.9f),
            linkVisited = LightBrandPrimary.copy(alpha = 0.7f),

            isDark = false
        )

        // Dark theme colors with darkened blue/purple hues
        val Dark = RecipesColors(
            // Background Colors
            backgroundPage = DarkBackground,
            backgroundSurface1 = DarkSurface1,
            backgroundSurface2 = DarkSurface2,
            backgroundSurface1Hover = DarkSurface2,
            backgroundSurface1Pressed = DarkSurface3,

            backgroundBrand = DarkBrandPrimary,
            backgroundBrandSubtle = DarkBrandPrimary.copy(alpha = 0.2f),
            backgroundBrandHover = DarkBrandPrimary.copy(alpha = 0.8f),
            backgroundBrandPressed = DarkBrandPrimary.copy(alpha = 0.9f),

            backgroundInfo = Info,
            backgroundInfoSubtle = Info.copy(alpha = 0.2f),
            backgroundSuccess = Success,
            backgroundSuccessSubtle = Success.copy(alpha = 0.2f),
            backgroundWarning = Warning,
            backgroundWarningSubtle = Warning.copy(alpha = 0.2f),
            backgroundDanger = Danger,
            backgroundDangerSubtle = Danger.copy(alpha = 0.2f),

            backgroundDisabled = DarkDisabled,
            backgroundSelected = DarkBrandPrimary.copy(alpha = 0.3f),
            backgroundLoading = DarkDisabled,

            // On Background Colors
            onBackgroundBrand = Color.White,
            onBackgroundBrandSubtle = DarkBrandPrimary,
            onBackgroundInfo = Color.White,
            onBackgroundInfoSubtle = InfoLight,
            onBackgroundSuccess = Color.White,
            onBackgroundSuccessSubtle = SuccessLight,
            onBackgroundWarning = Neutral900,
            onBackgroundWarningSubtle = WarningLight,
            onBackgroundDanger = Color.White,
            onBackgroundDangerSubtle = DangerLight,

            // Foreground Colors
            foregroundDefault = DarkForeground,
            foregroundSupport = DarkForegroundSupport,
            foregroundBrand = DarkBrandPrimary,
            foregroundInfo = InfoLight,
            foregroundSuccess = SuccessLight,
            foregroundWarning = WarningLight,
            foregroundDanger = DangerLight,
            foregroundDisabled = DarkForegroundDisabled,

            // Border Colors
            borderDefault = DarkSurface3,
            borderStrong = DarkForegroundSupport,
            borderBrand = DarkBrandPrimary,
            borderInfo = InfoLight,
            borderSuccess = SuccessLight,
            borderWarning = WarningLight,
            borderDanger = DangerLight,
            borderDisabled = DarkDisabled,
            borderFocus = DarkBrandPrimary,
            borderSeparator = DarkSurface2,

            // Link Colors
            linkDefault = DarkBrandPrimary,
            linkHover = DarkBrandPrimary.copy(alpha = 0.8f),
            linkPressed = DarkBrandPrimary.copy(alpha = 0.9f),
            linkVisited = DarkBrandPrimary.copy(alpha = 0.7f),

            isDark = true
        )
    }
}

// Local composition for providing RecipesColors
val LocalRecipesColors = staticCompositionLocalOf { RecipesColors.Light }
