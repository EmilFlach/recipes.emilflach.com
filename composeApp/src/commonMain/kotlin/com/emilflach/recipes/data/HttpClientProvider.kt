package com.emilflach.recipes.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Platform-specific engine factory
expect fun createHttpClientEngine(): HttpClientEngineFactory<*>

object HttpClientProvider {
    val BASE_URL = AppConfig.BASE_URL
    private val JWT_TOKEN = AppConfig.JWT_TOKEN

    // Common configuration for all platforms
    fun <T : HttpClientEngineConfig> configureClient(config: HttpClientConfig<T>) {
        with(config) {
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

    // Common implementation using platform-specific engine
    fun createHttpClient(): HttpClient {
        return HttpClient(createHttpClientEngine()) {
            configureClient(this)
        }
    }
}
