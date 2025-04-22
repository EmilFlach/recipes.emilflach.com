package com.emilflach.recipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

fun main() {
    singleWindowApplication(
        title = "My CHR App",
        state = WindowState(
            position = WindowPosition(580.dp, 50.dp),
            width = 450.dp,
            height = 1000.dp
        ),
        alwaysOnTop = true
    ) {
        MainPage()
    }
}

@Composable
fun MainPage() {
    App()
}