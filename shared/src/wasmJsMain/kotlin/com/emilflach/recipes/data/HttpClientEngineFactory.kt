package com.emilflach.recipes.data

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.Js

// Actual implementation for wasmJs platform
actual fun createHttpClientEngine(): HttpClientEngineFactory<*> = Js