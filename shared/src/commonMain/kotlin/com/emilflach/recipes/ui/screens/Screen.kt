package com.emilflach.recipes.ui.screens

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    @SerialName("/")
    data object RecipesList : Screen

    @Serializable
    @SerialName("recipe")
    data class RecipeDetail(val recipeSlug: String) : Screen

    @Serializable
    @SerialName("cook")
    data class CookingMode(val recipeSlug: String) : Screen
}