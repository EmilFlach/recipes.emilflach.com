package com.emilflach.recipes.data

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

// Actual implementation for iOS platform
actual fun createHttpClientEngine(): HttpClientEngineFactory<*> = Darwin