package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe


@Composable
fun RecipeCard(recipe: Recipe, itemWidth: Dp) {
    Card (modifier = Modifier
        .width(itemWidth)
        .padding(start = 16.dp, end = 16.dp)
    ){
        Row {
            Column (modifier = Modifier.fillMaxWidth(0.5f)) {
                recipe.name?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        style = MaterialTheme.typography.h3,
                        maxLines = 2
                    )
                }
                RecipeServings(recipe)
                recipe.totalTime?.let {
                    if (it.isEmpty()) return@let
                    Text(
                        text = "$it min",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            AsyncImage(
                model = recipe.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}
