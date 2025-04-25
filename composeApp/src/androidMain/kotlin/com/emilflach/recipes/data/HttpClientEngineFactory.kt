package com.emilflach.recipes.data

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

// Actual implementation for Android platform
actual fun createHttpClientEngine(): HttpClientEngineFactory<*> = CIO