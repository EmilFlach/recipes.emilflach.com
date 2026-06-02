package com.emilflach.recipes

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
    var isInitialNavigation by remember { mutableStateOf(true) }
    val recipesViewModel = RecipesViewModel(RecipeRepository)
    val recipeDetailViewModel = RecipeDetailViewModel(RecipeRepository)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).crossfade(true).build()
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)

        // Use this to detect whether we need to show transition animations
        navController.addOnDestinationChangedListener { _, _, _ ->
            if (isInitialNavigation) {
                isInitialNavigation = false
            }
        }
    }

    fun getEnterTransition(transition: EnterTransition): EnterTransition {
        return if (isInitialNavigation) {
            EnterTransition.None
        } else {
            transition
        }
    }


    RecipesAppTheme {
        NavHost(navController = navController, startDestination = Screen.RecipesList) {
            composable<Screen.RecipesList>(
                enterTransition = { fadeIn(animationSpec = tween(50)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                RecipesScreen(
                    viewModel = recipesViewModel,
                    onRecipeClick = { recipe ->
                        recipeDetailViewModel.setRecipe(recipe)
                        navController.navigate(Screen.RecipeDetail(recipe.slug))
                    }
                )
            }
            composable<Screen.RecipeDetail>(
                enterTransition = { getEnterTransition(
                    slideIntoContainer(SlideDirection.Left) ) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
                popEnterTransition = {getEnterTransition(
                        fadeIn(animationSpec = tween(50)))},
                popExitTransition = { slideOutOfContainer(SlideDirection.Right) }
            ) { backStackEntry ->
                val recipeDetail: Screen.RecipeDetail = backStackEntry.toRoute()
                RecipeDetailScreen(
                    viewModel = recipeDetailViewModel,
                    recipeSlug = recipeDetail.recipeSlug,
                    onBackClick = { navController.popBackStack() },
                    onCookingModeClick = { navController.navigate(Screen.CookingMode(recipeDetail.recipeSlug)) }
                )
            }
            composable<Screen.CookingMode>(
                enterTransition = { getEnterTransition(
                    slideIntoContainer(SlideDirection.Up)) },
                exitTransition = { slideOutOfContainer(SlideDirection.Down) }
            ) { backStackEntry ->
                val cookingMode: Screen.CookingMode = backStackEntry.toRoute()
                CookingModeScreen(
                    viewModel = recipeDetailViewModel,
                    recipeSlug = cookingMode.recipeSlug,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
