package com.emilflach.recipes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform