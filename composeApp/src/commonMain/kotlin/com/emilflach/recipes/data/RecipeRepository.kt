package com.emilflach.recipes.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object RecipeRepository {
    private val client = HttpClientProvider.createHttpClient()
    private var recipesCache: RecipesListResponse? = null
    private val recipeCache = mutableMapOf<String, Recipe>()
    private val cacheMutex = Mutex()

    suspend fun getRecipes(forceRefresh: Boolean = false): RecipesListResponse {
        if (!forceRefresh) {
            cacheMutex.withLock {
                recipesCache?.let { return it }
            }
        }

        val recipes: RecipesListResponse =
            client.get("${HttpClientProvider.BASE_URL}/recipes?requireAllCategories=true").body()
        cacheMutex.withLock {
            recipesCache = recipes
        }
        return recipes
    }

    suspend fun getRecipeBySlug(slug: String, forceRefresh: Boolean = false): Recipe {
        if (!forceRefresh) {
            cacheMutex.withLock {
                recipeCache[slug]?.let { return it }
            }
        }

        val recipe: Recipe = client.get("${HttpClientProvider.BASE_URL}/recipes/$slug").body()
        cacheMutex.withLock {
            recipeCache[slug] = recipe
        }
        return recipe
    }

    suspend fun enrichRecipe(recipe: Recipe, forceRefresh: Boolean = false): Recipe {
        if (!forceRefresh) {
            cacheMutex.withLock {
                recipeCache[recipe.slug]?.let { return it }
            }
        }

        val enrichedRecipe: Recipe =
            client.get("${HttpClientProvider.BASE_URL}/recipes/${recipe.slug}").body()
        cacheMutex.withLock {
            recipeCache[recipe.slug] = enrichedRecipe
        }
        return enrichedRecipe
    }
}
