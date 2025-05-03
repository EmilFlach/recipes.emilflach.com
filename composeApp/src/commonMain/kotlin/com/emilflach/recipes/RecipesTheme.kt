package com.emilflach.recipes

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.emilflach.recipes.ui.theme.ProvideRecipesColors
import com.emilflach.recipes.ui.theme.RecipesColors
import com.emilflach.recipes.ui.theme.toMaterialColors

@Composable
internal fun RecipesAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) RecipesColors.Dark else RecipesColors.Light

    val typography = remember {
        Typography(
            h1 = TextStyle(
                fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 44.sp
            ),
            h2 = TextStyle(
                fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal, fontSize = 24.sp
            ),
            h3 = TextStyle(
                fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 18.sp
            ),
            body1 = TextStyle(
                fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp
            ),
            body2= TextStyle(
                fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp
            )
        )
    }

    ProvideRecipesColors(colors = colors) {
        MaterialTheme(
            colors = colors.toMaterialColors(),
            typography = typography, 
            content = content
        )
    }
}
