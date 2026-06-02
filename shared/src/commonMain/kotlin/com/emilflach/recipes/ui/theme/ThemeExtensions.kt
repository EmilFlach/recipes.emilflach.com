package com.emilflach.recipes.ui.theme

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Colors
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import recipes.composeapp.generated.resources.Res
import recipes.composeapp.generated.resources.RobotoSerif_Bold
import recipes.composeapp.generated.resources.RobotoSerif_Medium



/**
 * Extension properties to access RecipesColors from MaterialTheme
 */
@Suppress("UnusedReceiverParameter")
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
        secondary = backgroundBrand, // Don't use secondary, it's a lame concept
        secondaryVariant = backgroundBrandHover, // Don't use secondary, it's a lame concept
        background = backgroundPage,
        surface = backgroundSurface1,
        error = backgroundDanger,
        onPrimary = onBackgroundBrand,
        onSecondary = Color.Black, // Same for both themes
        onBackground = foregroundDefault,
        onSurface = foregroundDefault,
        onError = onBackgroundDanger,
        isLight = !isDark
    )
}

/**
 * Creates Typography directly for MaterialTheme using semantic colors
 */
@Composable
fun createTypography(darkTheme: Boolean): Typography {
    // Use the appropriate RecipesColors instance based on the theme
    val colors = if (darkTheme) RecipesColors.Dark else RecipesColors.Light

    val serifFontFamily = FontFamily(
        Font( Res.font.RobotoSerif_Medium, FontWeight.Medium),
        Font(Res.font.RobotoSerif_Bold, FontWeight.Bold),
    )

    return Typography(
        h1 = TextStyle(
            fontFamily = serifFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = colors.foregroundDefault
        ),
        h2 = TextStyle(
            fontFamily = serifFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            color = colors.foregroundDefault
        ),
        h3 = TextStyle(
            fontFamily = serifFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = colors.foregroundDefault
        ),
        h4 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colors.foregroundDefault
        ),
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = colors.foregroundDefault
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = colors.foregroundSupport
        ),
        subtitle1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colors.foregroundDefault
        ),
        subtitle2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = colors.foregroundSupport
        ),
        button = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colors.foregroundDefault
        ),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = colors.foregroundSupport
        ),
        overline = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = colors.foregroundSupport
        )
    )
}

/**
 * Creates larger Typography for cooking mode with better readability
 */
@Composable
fun createCookingTypography(darkTheme: Boolean): Typography {
    val baseTypography = createTypography(darkTheme)
    return baseTypography.copy(
        body1 = baseTypography.body1.copy(fontSize = 22.sp),
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
