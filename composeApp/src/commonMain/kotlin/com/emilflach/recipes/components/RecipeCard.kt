package com.emilflach.recipes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe

@Composable
fun RecipeCard(recipe: Recipe) {
    Card {
        Column {
            AsyncImage(
                model = recipe.image,
                contentScale = ContentScale.FillWidth,
                contentDescription = recipe.name,
                modifier = Modifier.fillMaxWidth()
            )
            recipe.name?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h3,
                )
            }
            recipe.recipeServings?.let {
                val servings = it.toDouble().toInt()
                if (servings == 0) return@let
                Text(
                    text = "Serves $servings",
                    modifier = Modifier.padding(16.dp)
                )
            }
            recipe.recipeYieldQuantity?.let  {
                if (it.toDouble() == 0.0) return@let
                Text(
                    text = "$it ${recipe.recipeYield}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            recipe.totalTime?.let {
                if (it.isEmpty()) return@let
                Text(
                    text = "Takes $it minutes",
                    modifier = Modifier.padding(16.dp)
                )
            }
            for(category in recipe.recipeCategory) {
                Text(
                    text = "${category.name}",
                    modifier = Modifier.padding(16.dp)
                )
            }
            for(tag in recipe.tags) {
                Text(
                    text = "${tag.name}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
