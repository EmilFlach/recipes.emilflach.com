package com.emilflach.recipes.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.random.Random

class RecipeRepository {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    // Load your JWT token here
                    BearerTokens(
                        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODA3ZTVkYS1mZmZjLTQ5YzUtYmRlZi1jZTI1YmU1OWFlODgiLCJleHAiOjE3NDUyMzcxMzksImlzcyI6Im1lYWxpZSJ9.jn_dQwnppvQdrbx1H9zmZ-RE5_sus62jb_87rNPFyHQ",
                        refreshToken = ""
                    )
                }
            }

        }
    }

    suspend fun getRecipes(random: Boolean = false): RecipesListResponse {
        return if(random) {
            generateRandomRecipes()
        } else {
            fetchAllRecipes()
        }
    }

    suspend fun getRecipeBySlug(slug: String): Recipe {
        val recipe: Recipe = client.get("http://192.168.1.111:9925/api/recipes/$slug").body()
        return recipe
    }

    private suspend fun fetchAllRecipes(): RecipesListResponse {
        val recipes: RecipesListResponse = client.get("http://192.168.1.111:9925/api/recipes").body()
        return recipes
    }



    private fun generateRandomRecipes (count: Int = 10, minSize: Int = 500, maxSize: Int = 800): RecipesListResponse {
        val result = mutableListOf<Recipe>()
        for (i in 1..count) {
            val randomSeed = Random.nextInt(until = 5000)
            val randomWidth = Random.nextInt(minSize, maxSize)
            val randomHeight = Random.nextInt(minSize, maxSize)
            val image =  "https://picsum.photos/seed/$randomSeed/$randomWidth/$randomHeight.jpg"

            result.add(Recipe(name = "Example title: $i", id = "$i", slug = "slug-$i", image = image))
        }
        return RecipesListResponse(
            items = result
        )
    }
}