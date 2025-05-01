package com.emilflach.recipes.data

import kotlin.test.Test
import kotlin.test.assertEquals

class RecipeTest {

    @Test
    fun testFormatIngredients_nonFoodItem_returnsDisplay() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = false,
                    display = "Non-food item"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("Non-food item"), result)
    }

    @Test
    fun testFormatIngredients_withQuantityAndUnit_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = Unit(
                        id = "1",
                        name = "cup",
                        pluralName = "cups",
                        abbreviation = null
                    ),
                    food = Food(
                        id = "1",
                        name = "flour",
                        pluralName = null
                    ),
                    display = "2 cups flour"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 cups flour"), result)
    }

    @Test
    fun testFormatIngredients_withQuantityAndUnitAbbreviation_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = Unit(
                        id = "1",
                        name = "tablespoon",
                        pluralName = "tablespoons",
                        abbreviation = "tbsp"
                    ),
                    food = Food(
                        id = "1",
                        name = "sugar",
                        pluralName = null
                    ),
                    display = "2 tbsp sugar"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2tbsp sugar"), result)
    }

    @Test
    fun testFormatIngredients_withDecimalQuantity_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 1.5,
                    unit = Unit(
                        id = "1",
                        name = "cup",
                        pluralName = "cups",
                        abbreviation = null
                    ),
                    food = Food(
                        id = "1",
                        name = "milk",
                        pluralName = null
                    ),
                    display = "1.5 cups milk"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1.5 cups milk"), result)
    }

    @Test
    fun testFormatIngredients_withPluralFood_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = null,
                    food = Food(
                        id = "1",
                        name = "apple",
                        pluralName = "apples"
                    ),
                    display = "2 apples"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 apples"), result)
    }

    @Test
    fun testFormatIngredients_withSingularFood_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 1.0,
                    unit = null,
                    food = Food(
                        id = "1",
                        name = "apple",
                        pluralName = "apples"
                    ),
                    display = "1 apple"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1 apple"), result)
    }

    @Test
    fun testFormatIngredients_withoutQuantity_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = null,
                    unit = null,
                    food = Food(
                        id = "1",
                        name = "salt",
                        pluralName = null
                    ),
                    display = "salt to taste"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("salt"), result)
    }

    @Test
    fun testFormatIngredients_withoutQuantityWithPluralName_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = null,
                    unit = null,
                    food = Food(
                        id = "1",
                        name = "pea",
                        pluralName = "peas"
                    ),
                    display = "peas"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("peas"), result)
    }

    @Test
    fun testFormatIngredients_multipleIngredients_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = Unit(
                        id = "1",
                        name = "cup",
                        pluralName = "cups",
                        abbreviation = null
                    ),
                    food = Food(
                        id = "1",
                        name = "flour",
                        pluralName = null
                    ),
                    display = "2 cups flour"
                ),
                RecipeIngredient(
                    isFood = true,
                    quantity = 1.0,
                    unit = Unit(
                        id = "2",
                        name = "teaspoon",
                        pluralName = "teaspoons",
                        abbreviation = "tsp"
                    ),
                    food = Food(
                        id = "2",
                        name = "salt",
                        pluralName = null
                    ),
                    display = "1 tsp salt"
                ),
                RecipeIngredient(
                    isFood = false,
                    display = "A pinch of love"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 cups flour", "1tsp salt", "A pinch of love"), result)
    }

    @Test
    fun testFormatIngredients_withGrams_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 100.0,
                    unit = Unit(
                        id = "1",
                        name = "gram",
                        pluralName = "grams",
                        abbreviation = "g"
                    ),
                    food = Food(
                        id = "1",
                        name = "flour",
                        pluralName = null
                    ),
                    display = "100 g flour"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("100g flour"), result)
    }

    @Test
    fun testFormatIngredients_withSingularGram_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 1.0,
                    unit = Unit(
                        id = "1",
                        name = "gram",
                        pluralName = "grams",
                        abbreviation = "g"
                    ),
                    food = Food(
                        id = "1",
                        name = "yeast",
                        pluralName = null
                    ),
                    display = "1 g yeast"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1g yeast"), result)
    }

    @Test
    fun testFormatIngredients_withKilograms_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = Unit(
                        id = "1",
                        name = "kilogram",
                        pluralName = "kilograms",
                        abbreviation = "kg"
                    ),
                    food = Food(
                        id = "1",
                        name = "potato",
                        pluralName = "potatoes"
                    ),
                    display = "2 kg potatoes"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2kg potatoes"), result)
    }

    @Test
    fun testFormatIngredients_withSingularKilogram_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 1.0,
                    unit = Unit(
                        id = "1",
                        name = "kilogram",
                        pluralName = "kilograms",
                        abbreviation = "kg"
                    ),
                    food = Food(
                        id = "1",
                        name = "rice",
                        pluralName = null
                    ),
                    display = "1 kg rice"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1kg rice"), result)
    }

    @Test
    fun testFormatIngredients_withMilliliters_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 250.0,
                    unit = Unit(
                        id = "1",
                        name = "milliliter",
                        pluralName = "milliliters",
                        abbreviation = "ml"
                    ),
                    food = Food(
                        id = "1",
                        name = "milk",
                        pluralName = null
                    ),
                    display = "250 ml milk"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("250ml milk"), result)
    }

    @Test
    fun testFormatIngredients_withLiters_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 2.0,
                    unit = Unit(
                        id = "1",
                        name = "liter",
                        pluralName = "liters",
                        abbreviation = "l"
                    ),
                    food = Food(
                        id = "1",
                        name = "water",
                        pluralName = null
                    ),
                    display = "2 l water"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2l water"), result)
    }

    @Test
    fun testFormatIngredients_withDecimalMetricQuantity_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 0.5,
                    unit = Unit(
                        id = "1",
                        name = "liter",
                        pluralName = "liters",
                        abbreviation = "l"
                    ),
                    food = Food(
                        id = "1",
                        name = "cream",
                        pluralName = null
                    ),
                    display = "0.5 l cream"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("0.5l cream"), result)
    }

    @Test
    fun testFormatIngredients_withMultipleMetricIngredients_formatsCorrectly() {
        // Arrange
        val recipe = Recipe(
            id = "1",
            slug = "test-recipe",
            recipeIngredient = listOf(
                RecipeIngredient(
                    isFood = true,
                    quantity = 500.0,
                    unit = Unit(
                        id = "1",
                        name = "gram",
                        pluralName = "grams",
                        abbreviation = "g"
                    ),
                    food = Food(
                        id = "1",
                        name = "flour",
                        pluralName = null
                    ),
                    display = "500 g flour"
                ),
                RecipeIngredient(
                    isFood = true,
                    quantity = 250.0,
                    unit = Unit(
                        id = "2",
                        name = "milliliter",
                        pluralName = "milliliters",
                        abbreviation = "ml"
                    ),
                    food = Food(
                        id = "2",
                        name = "milk",
                        pluralName = null
                    ),
                    display = "250 ml milk"
                ),
                RecipeIngredient(
                    isFood = true,
                    quantity = 0.25,
                    unit = Unit(
                        id = "3",
                        name = "kilogram",
                        pluralName = "kilograms",
                        abbreviation = "kg"
                    ),
                    food = Food(
                        id = "3",
                        name = "butter",
                        pluralName = null
                    ),
                    display = "0.25 kg butter"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("500g flour", "250ml milk", "0.25kg butter"), result)
    }
}
