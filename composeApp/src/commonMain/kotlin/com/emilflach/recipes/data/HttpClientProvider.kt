package com.emilflach.recipes.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    const val BASE_URL = "http://192.168.1.111:9925/api"
    private const val JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5ODA3ZTVkYS1mZmZjLTQ5YzUtYmRlZi1jZTI1YmU1OWFlODgiLCJleHAiOjE3NDU0MjgwMDcsImlzcyI6Im1lYWxpZSJ9.fx-8eZZi7jsnm1kAlcQsUi_wwaSOmZjx6Oqp8yKEzwE"

    fun createHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = JWT_TOKEN,
                            refreshToken = ""
                        )
                    }
                }
            }
        }
    }
}