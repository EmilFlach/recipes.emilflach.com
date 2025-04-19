package com.emilflach.recipes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emilflach.recipes.data.RecipeRepository
import com.emilflach.recipes.ui.screens.RecipesScreen
import com.emilflach.recipes.ui.screens.RecipeDetailScreen
import com.emilflach.recipes.ui.screens.RecipeDetailViewModel
import com.emilflach.recipes.ui.screens.RecipesViewModel
import com.emilflach.recipes.ui.screens.Screen

@Composable
fun App() {
    val navController = rememberNavController()
    val recipesViewModel = RecipesViewModel(RecipeRepository())
    val recipeDetailViewModel = RecipeDetailViewModel(RecipeRepository())

    NavHost(navController = navController, startDestination = Screen.RecipesList.route) {
        composable(Screen.RecipesList.route) {
            RecipesScreen(
                viewModel = recipesViewModel,
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.RecipeDetail.createRoute(recipeId))
                }
            )
        }

        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeSlug") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeSlug = backStackEntry.arguments?.getString("recipeSlug")
            recipeSlug?.let {
                RecipeDetailScreen(
                    viewModel = recipeDetailViewModel,
                    recipeSlug = it,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

