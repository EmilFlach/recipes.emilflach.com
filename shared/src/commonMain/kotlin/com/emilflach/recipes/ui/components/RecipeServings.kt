package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun RecipeServings(recipe: Recipe) {
    Row (
        verticalAlignment = CenterVertically,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    ){
        Text(
            text = recipe.calories,
        )
        Text(
            text = "(Serves ${recipe.servingsCount})",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 4.dp, top = 3.dp)
        )
    }
}