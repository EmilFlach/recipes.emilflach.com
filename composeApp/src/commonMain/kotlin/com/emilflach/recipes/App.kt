package com.emilflach.recipes

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.emilflach.recipes.data.RecipeRepository
import com.emilflach.recipes.ui.screens.CookingModeScreen
import com.emilflach.recipes.ui.screens.RecipeDetailScreen
import com.emilflach.recipes.ui.screens.RecipeDetailViewModel
import com.emilflach.recipes.ui.screens.RecipesScreen
import com.emilflach.recipes.ui.screens.RecipesViewModel
import com.emilflach.recipes.ui.screens.Screen

@Composable
fun App(
    onNavHostReady: suspend (NavHostController) -> Unit = {}
) {
    val navController = rememberNavController()
    val recipesViewModel = RecipesViewModel(RecipeRepository)
    val recipeDetailViewModel = RecipeDetailViewModel(RecipeRepository)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).crossfade(true).build()
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    RecipesAppTheme {
        NavHost(navController = navController, startDestination = Screen.RecipesList.route) {
            composable(Screen.RecipesList.route,
                // Use standard fade transitions with appropriate durations for the home screen
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(50)
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(300)
                    )
                }
            ) {
                RecipesScreen(
                    viewModel = recipesViewModel, onRecipeClick = { recipe ->
                        recipeDetailViewModel.setRecipe(recipe)
                        navController.navigate(Screen.RecipeDetail.createRoute(recipe.slug))
                    })
            }

            composable(
                route = Screen.RecipeDetail.route,
                arguments = listOf(navArgument("recipeSlug") { type = NavType.StringType }),
                // Forward navigation (list to detail)
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                // Exit when navigating to cooking mode
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(300)
                    )
                },
                // Enter when returning from cooking mode
                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(50)
                    )
                },
                // Exit when returning to list
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            ) { backStackEntry ->
                val recipeSlug = backStackEntry.arguments?.getString("recipeSlug")
                recipeSlug?.let {
                    RecipeDetailScreen(
                        viewModel = recipeDetailViewModel,
                        recipeSlug = it,
                        onBackClick = { navController.popBackStack() },
                        onCookingModeClick = { navController.navigate(Screen.CookingMode.createRoute(recipeSlug)) })
                }
            }
            composable(
                route = Screen.CookingMode.route,
                arguments = listOf(navArgument("recipeSlug") { type = NavType.StringType }),
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
                },
                // Exit with a slide down animation
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
                }
            ) { backStackEntry ->
                val recipeSlug = backStackEntry.arguments?.getString("recipeSlug")
                recipeSlug?.let {
                    CookingModeScreen(
                        viewModel = recipeDetailViewModel,
                        recipeSlug = it,
                        onBackClick = { navController.popBackStack() })
                }
            }
        }
    }
}
