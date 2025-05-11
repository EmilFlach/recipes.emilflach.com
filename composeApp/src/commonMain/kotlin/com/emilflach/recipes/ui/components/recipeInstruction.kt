package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction

@Composable
fun recipeInstruction(index: Int, instruction: Instruction) {
    Text(
        text = "Step ${index + 1}:",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 24.dp,
            bottom = 8.dp
        )
    )
    Text(
        text = instruction.text,
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    )
    instruction.ingredients.forEach { ingredient ->
        recipeIngredient(ingredient, displayBasicsOnly = true)
    }
}