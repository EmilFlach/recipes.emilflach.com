package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun WeeknightRecipes(recipes: List<Recipe>) {
    Text(
        text = "Weeknights",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "Less than 20 minutes and vegetarian",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val itemWidth = maxWidth * 0.92f
        val rows = 3
        LazyHorizontalGrid(
            modifier = Modifier.height(500.dp),
            rows = GridCells.Fixed(rows),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            itemsIndexed(recipes, key = {_, recipe -> recipe.slug}) { _, recipe ->
                val isLastPage = recipes.indexOf(recipe) >= recipes.size - rows
                RecipeCard(recipe, itemWidth, isLastPage)
            }
        }
    }
}