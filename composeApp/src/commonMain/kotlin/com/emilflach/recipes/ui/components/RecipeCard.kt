package com.emilflach.recipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeCard(
    index: Int,
    recipe: Recipe,
    onRecipeClick: (Recipe) -> Unit
) {
    Card (
        shape = cardRounding(index, 16.dp),
        modifier = Modifier
        .clickable {
            onRecipeClick(recipe)
        }
        .height(160.dp)
    ){
        Row {
            AsyncImage(
                model = recipe.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
            )
            Column (modifier = Modifier.fillMaxWidth(1f)) {
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
                    Box (
                        modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.recipesColors.backgroundBrand)
                    ) {
                        Text(
                            text = "$it min",
                            modifier = Modifier.padding(12.dp, 8.dp)
                        )
                    }

                }
            }

        }
    }
}

private fun cardRounding(index: Int, size: Dp): RoundedCornerShape {
    return when (index) {
        0 -> RoundedCornerShape(size, size, 0.dp, 0.dp)
        2 -> RoundedCornerShape(0.dp, 0.dp, size, size)
        else -> RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
    }
}
