package com.emilflach.recipes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.emilflach.recipes.ui.theme.LocalRecipesColors
import com.emilflach.recipes.ui.theme.RecipesColors
import com.emilflach.recipes.ui.theme.createTypography
import com.emilflach.recipes.ui.theme.toMaterialColors

/**
 * Theme for the Recipes app that automatically adapts to system dark mode preference
 */
@Composable
fun RecipesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) RecipesColors.Dark else RecipesColors.Light

    // Provide RecipesColors for backward compatibility
    CompositionLocalProvider(LocalRecipesColors provides colors) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            typography = createTypography(darkTheme),
            content = content
        )
    }
}
