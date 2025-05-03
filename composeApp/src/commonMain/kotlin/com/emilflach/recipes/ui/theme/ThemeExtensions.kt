package com.emilflach.recipes.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

/**
 * Extension properties to access RecipesColors from MaterialTheme
 */
val MaterialTheme.recipesColors: RecipesColors
    @Composable
    @ReadOnlyComposable
    get() = LocalRecipesColors.current

/**
 * Creates a Colors object from RecipesColors to bridge with MaterialTheme
 */
fun RecipesColors.toMaterialColors(): Colors {
    return Colors(
        primary = backgroundBrand,
        primaryVariant = backgroundBrandHover,
        secondary = if (isDark) BrandSecondary else BrandSecondary,
        secondaryVariant = if (isDark) BrandSecondary.copy(alpha = 0.8f) else BrandSecondary.copy(alpha = 0.8f),
        background = backgroundPage,
        surface = backgroundSurface1,
        error = backgroundDanger,
        onPrimary = onBackgroundBrand,
        onSecondary = if (isDark) Color.Black else Color.Black,
        onBackground = foregroundDefault,
        onSurface = foregroundDefault,
        onError = onBackgroundDanger,
        isLight = !isDark
    )
}

/**
 * Provides RecipesColors to the composition
 */
@Composable
fun ProvideRecipesColors(
    colors: RecipesColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalRecipesColors provides colors) {
        content()
    }
}