package com.emilflach.recipes.ui.screens

sealed class Screen(val route: String) {
    data object RecipesList : Screen("/")
    data object RecipeDetail : Screen("recipe/{recipeSlug}") {
        fun createRoute(recipeSlug: String) = "recipe/$recipeSlug"
    }
    data object CookingMode : Screen("recipe/{recipeSlug}/cook") {
        fun createRoute(recipeSlug: String) = "recipe/$recipeSlug/cook"
    }
}
