package com.emilflach.recipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

fun main() {
    singleWindowApplication(
        title = "My CHR App",
        state = WindowState(width = 480.dp, height = 1080.dp),
        alwaysOnTop = true
    ) {
        MainPage()
    }
}

@Composable
fun MainPage() {
    App()
}