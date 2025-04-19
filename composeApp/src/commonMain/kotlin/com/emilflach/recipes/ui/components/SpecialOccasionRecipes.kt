package com.emilflach.recipes.ui.components

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
fun SpecialOccasionRecipes(recipes: List<Recipe>) {
    Text(
        text = "Special occasions",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "Complex ingredients and prep, but worth the effort",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val itemWidth = maxWidth * 0.46f
        LazyHorizontalGrid(
            modifier = Modifier.height(250.dp),
            rows = GridCells.Fixed(1)
        ) {
            itemsIndexed(recipes, key = {_, recipe -> recipe.slug}) { _, recipe ->
                val isLastItem = recipes.indexOf(recipe) >= recipes.size - 1
                HighlightedRecipeCard(recipe, itemWidth, isLastItem)
            }
        }
    }
}