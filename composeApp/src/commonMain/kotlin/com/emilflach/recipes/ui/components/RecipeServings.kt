package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun RecipeServings(recipe: Recipe) {
    val servingsText = when {
        recipe.yieldCount > 0 -> "${recipe.yieldCount} ${recipe.recipeYield}"
        recipe.servingsCount > 0 -> "Serves ${recipe.servingsCount}"
        else -> return
    }

    Row {
        Text(
            text = servingsText,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        )
        recipe.calories?.let {
            Text(
                text = "($it)",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 4.dp, end = 16.dp, top = 18.dp),
            )
        }
    }
}