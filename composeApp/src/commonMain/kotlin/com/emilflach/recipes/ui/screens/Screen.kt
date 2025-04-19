package com.emilflach.recipes.ui.screens

sealed class Screen(val route: String) {
    object RecipesList : Screen("recipes")
    object RecipeDetail : Screen("recipe/{recipeSlug}") {
        fun createRoute(recipeSlug: String) = "recipe/$recipeSlug"
    }
}
