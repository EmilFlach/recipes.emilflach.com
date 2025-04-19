package com.emilflach.recipes

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun RecipesAppTheme(
    content: @Composable () -> Unit
) {

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

    MaterialTheme(
        typography = typography, content = content
    )
}
