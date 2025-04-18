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
                        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODA3ZTVkYS1mZmZjLTQ5YzUtYmRlZi1jZTI1YmU1OWFlODgiLCJleHAiOjE3NDQ5ODQ4MjAsImlzcyI6Im1lYWxpZSJ9.74iySXAcCq3w-66zzrIkmnDdNxzWzxM93zso5dvWSg4",
                        refreshToken = ""
                    )
                }
            }

        }
    }

    suspend fun getRecipes(random: Boolean = true): RecipesListResponse {
        return if(random) {
            generateRandomRecipes()
        } else {
            fetchAllRecipes()
        }
    }

    private fun generateRandomRecipes (count: Int = 10, minSize: Int = 500, maxSize: Int = 800): RecipesListResponse {
        val result = mutableListOf<Recipe>()
        for (i in 1..count) {
            val randomSeed = Random.nextInt(until = 5000)
            val randomWidth = Random.nextInt(minSize, maxSize)
            val randomHeight = Random.nextInt(minSize, maxSize)
            val image =  "https://picsum.photos/seed/$randomSeed/$randomWidth/$randomHeight.jpg"

            result.add(Recipe(name = "Example title: $i", image = image))
        }
        return RecipesListResponse(
            items = result
        )
    }

    private suspend fun fetchAllRecipes(): RecipesListResponse {
        val recipes: RecipesListResponse = client.get("http://192.168.1.111:9925/api/recipes").body()
        return recipes
    }
}