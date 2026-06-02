package com.emilflach.recipes.data

import kotlin.test.Test
import kotlin.test.assertEquals

// Extension property to make tests work with the new Ingredient class
private val Ingredient.first: String
    get() = this.text

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
                    display = "Non-food item",
                    referenceId = "ref1"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("Non-food item"), result.map { it.first })
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
                    display = "2 cups flour",
                    referenceId = "ref2"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 cups flour"), result.map { it.first })
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
                    display = "2 tbsp sugar",
                    referenceId = "ref3"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2tbsp sugar"), result.map { it.first })
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
                    display = "1.5 cups milk",
                    referenceId = "ref4"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1.5 cups milk"), result.map { it.first })
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
                    display = "2 apples",
                    referenceId = "ref5"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 apples"), result.map { it.first })
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
                    display = "1 apple",
                    referenceId = "ref6"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1 apple"), result.map { it.first })
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
                    display = "salt to taste",
                    referenceId = "ref7"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("salt"), result.map { it.first })
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
                    display = "peas",
                    referenceId = "ref8"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("peas"), result.map { it.first })
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
                    display = "2 cups flour",
                    referenceId = "ref9"
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
                    display = "1 tsp salt",
                    referenceId = "ref10"
                ),
                RecipeIngredient(
                    isFood = false,
                    display = "A pinch of love",
                    referenceId = "ref11"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2 cups flour", "1tsp salt", "A pinch of love"), result.map { it.first })
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
                    display = "100 g flour",
                    referenceId = "ref12"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("100g flour"), result.map { it.first })
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
                    display = "1 g yeast",
                    referenceId = "ref13"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1g yeast"), result.map { it.first })
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
                    display = "2 kg potatoes",
                    referenceId = "ref14"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2kg potatoes"), result.map { it.first })
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
                    display = "1 kg rice",
                    referenceId = "ref15"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("1kg rice"), result.map { it.first })
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
                    display = "250 ml milk",
                    referenceId = "ref16"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("250ml milk"), result.map { it.first })
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
                    display = "2 l water",
                    referenceId = "ref17"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("2l water"), result.map { it.first })
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
                    display = "0.5 l cream",
                    referenceId = "ref18"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("0.5l cream"), result.map { it.first })
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
                    display = "500 g flour",
                    referenceId = "ref19"
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
                    display = "250 ml milk",
                    referenceId = "ref20"
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
                    display = "0.25 kg butter",
                    referenceId = "ref21"
                )
            )
        )

        // Act
        val result = recipe.formatIngredients()

        // Assert
        assertEquals(listOf("500g flour", "250ml milk", "0.25kg butter"), result.map { it.first })
    }
}