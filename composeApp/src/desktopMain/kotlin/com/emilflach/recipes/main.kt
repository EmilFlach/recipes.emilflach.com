package com.emilflach.recipes

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        title = "Recipes",
        state = WindowState(
            position = WindowPosition(580.dp, 50.dp),
            width = 450.dp,
            height = 1000.dp
        ),
        alwaysOnTop = true,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}