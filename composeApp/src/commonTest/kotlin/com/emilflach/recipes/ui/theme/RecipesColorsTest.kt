package com.emilflach.recipes.ui.theme

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RecipesColorsTest {

    @Test
    fun testLightThemeColors() {
        val lightColors = RecipesColors.Light

        // Test that light theme is correctly identified
        assertFalse(lightColors.isDark)

        // Test that primary brand colors are defined
        assertNotNull(lightColors.backgroundBrand)
        assertEquals(BrandPrimary, lightColors.backgroundBrand)

        // Test that foreground colors are defined
        assertNotNull(lightColors.foregroundDefault)
        assertNotNull(lightColors.foregroundBrand)

        // Test that on-background colors are defined
        assertNotNull(lightColors.onBackgroundBrand)

        // Test that border colors are defined
        assertNotNull(lightColors.borderDefault)

        // Test that link colors are defined
        assertNotNull(lightColors.linkDefault)

        // Test that focus colors are defined
        assertNotNull(lightColors.focusOutline)
    }

    @Test
    fun testDarkThemeColors() {
        val darkColors = RecipesColors.Dark

        // Test that dark theme is correctly identified
        assertTrue(darkColors.isDark)

        // Test that primary brand colors are defined
        assertNotNull(darkColors.backgroundBrand)
        assertEquals(BrandPrimary, darkColors.backgroundBrand)

        // Test that foreground colors are defined
        assertNotNull(darkColors.foregroundDefault)
        assertNotNull(darkColors.foregroundBrand)

        // Test that on-background colors are defined
        assertNotNull(darkColors.onBackgroundBrand)

        // Test that border colors are defined
        assertNotNull(darkColors.borderDefault)

        // Test that link colors are defined
        assertNotNull(darkColors.linkDefault)

        // Test that focus colors are defined
        assertNotNull(darkColors.focusOutline)
    }

    @Test
    fun testMaterialColorsConversion() {
        val lightColors = RecipesColors.Light
        val materialColors = lightColors.toMaterialColors()

        // Test that material colors are correctly converted
        assertEquals(lightColors.backgroundBrand, materialColors.primary)
        assertEquals(lightColors.backgroundBrandHover, materialColors.primaryVariant)
        assertEquals(lightColors.backgroundPage, materialColors.background)
        assertEquals(lightColors.backgroundSurface1, materialColors.surface)
        assertEquals(lightColors.backgroundDanger, materialColors.error)
        assertEquals(lightColors.onBackgroundBrand, materialColors.onPrimary)
        assertEquals(lightColors.foregroundDefault, materialColors.onBackground)
        assertEquals(lightColors.foregroundDefault, materialColors.onSurface)
        assertEquals(lightColors.onBackgroundDanger, materialColors.onError)
        assertTrue(materialColors.isLight)
    }
}
