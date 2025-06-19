package com.emilflach.recipes

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import com.emilflach.recipes.ui.screens.Screen
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {
    val body = document.body ?: return

    val isProduction = window.location.hostname.contains("emilflach.com") ||
            window.location.hostname.contains("emilflach.nl")

    // Signal that app is beginning to initialize
    ComposeViewport(body) {
        // Signal that the app has started rendering
        body.classList.add("app-ready")
        
        App(
            onNavHostReady = { navController ->
                // Handle initial route based on environment
                val initialPath = if (isProduction) {
                    window.location.pathname
                } else {
                    window.location.hash.substringAfter('#', "")
                }

                when {
                    (isProduction && initialPath.startsWith("/recipe/")) ||
                            (!isProduction && initialPath.startsWith("recipe/")) -> {
                        val slug = if (isProduction) {
                            initialPath.substringAfter("/recipe/")
                        } else {
                            initialPath.substringAfter("recipe/")
                        }
                        navController.navigate(Screen.RecipeDetail.createRoute(slug))
                    }
                    else -> {
                        navController.navigate(Screen.RecipesList.route)
                    }
                }

                if (isProduction) {
                    // HTML5 history mode
                    navController.addOnDestinationChangedListener { _, destination, arguments ->
                        val path = when {
                            destination.route == Screen.RecipesList.route -> "/"  // Changed from "/recipes" to "/"
                            destination.route?.startsWith(Screen.RecipeDetail.route) == true -> {
                                val slug = arguments?.getString("recipeSlug")
                                "/recipe/$slug"
                            }
                            else -> "/"
                        }
                        window.history.pushState(null, "", path)
                    }

                    window.addEventListener("popstate") { event ->
                        val path = window.location.pathname
                        when {
                            path.startsWith("/recipe/") -> {
                                val slug = path.substringAfter("/recipe/")
                                navController.navigate(Screen.RecipeDetail.createRoute(slug))
                            }
                            else -> navController.navigate(Screen.RecipesList.route)
                        }
                    }
                } else {
                    // Hash based navigation
                    navController.addOnDestinationChangedListener { _, destination, arguments ->
                        val hash = when {
                            destination.route == Screen.RecipesList.route -> ""  // Changed from "#recipes" to empty string
                            destination.route?.startsWith(Screen.RecipeDetail.route) == true -> {
                                val slug = arguments?.getString("recipeSlug")
                                "#recipe/$slug"
                            }
                            else -> ""
                        }
                        window.location.hash = hash
                    }

                    window.addEventListener("hashchange") { event ->
                        val hash = window.location.hash.substringAfter('#')
                        when {
                            hash.startsWith("recipe/") -> {
                                val slug = hash.substringAfter("recipe/")
                                navController.navigate(Screen.RecipeDetail.createRoute(slug))
                            }
                            else -> navController.navigate(Screen.RecipesList.route)
                        }
                    }
                }
            }
        )
    }
}


