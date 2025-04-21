package com.emilflach.recipes

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.emilflach.recipes.data.RecipeRepository
import com.emilflach.recipes.ui.screens.RecipeDetailScreen
import com.emilflach.recipes.ui.screens.RecipeDetailViewModel
import com.emilflach.recipes.ui.screens.RecipesScreen
import com.emilflach.recipes.ui.screens.RecipesViewModel
import com.emilflach.recipes.ui.screens.Screen

@Composable
fun App() {
    val navController = rememberNavController()
    val recipesViewModel = RecipesViewModel(RecipeRepository)
    val recipeDetailViewModel = RecipeDetailViewModel(RecipeRepository)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).crossfade(true).build()
    }

    NavHost(navController = navController, startDestination = Screen.RecipesList.route) {
        composable(Screen.RecipesList.route, enterTransition = {
            fadeIn(
                animationSpec = tween(50)
            )
        }, exitTransition = {

            fadeOut(
                animationSpec = tween(1000)
            )
        }) {
            RecipesScreen(
                viewModel = recipesViewModel, onRecipeClick = { recipe ->
                    recipeDetailViewModel.setRecipe(recipe)
                    navController.navigate(Screen.RecipeDetail.createRoute(recipe.slug))
                })
        }

        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeSlug") { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(200)
                )
            },
        ) { backStackEntry ->
            val recipeSlug = backStackEntry.arguments?.getString("recipeSlug")
            recipeSlug?.let {
                RecipeDetailScreen(
                    viewModel = recipeDetailViewModel,
                    recipeSlug = it,
                    onBackClick = { navController.popBackStack() })
            }
        }
    }
}
