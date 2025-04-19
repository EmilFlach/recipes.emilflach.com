package com.emilflach.recipes

import androidx.compose.runtime.Composable
import com.emilflach.recipes.data.RecipeRepository
import com.emilflach.recipes.ui.screens.RecipesScreen
import com.emilflach.recipes.ui.screens.RecipesViewModel

@Composable
fun App() {
    val recipesViewModel = RecipesViewModel(RecipeRepository())
    RecipesScreen(recipesViewModel)
}
