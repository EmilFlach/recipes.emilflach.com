package com.emilflach.recipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.theme.recipesColors


@Composable
fun HighlightedRecipeCard(
    recipe: Recipe,
    onRecipeClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ){
        BoxWithConstraints(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onRecipeClick(recipe) }
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        Brush.verticalGradient(
                            // Gradient hacks, no semantic colors here, onSecondary is always black
                            0.6f to MaterialTheme.colors.onSecondary.copy(alpha = 0f),
                            1f to MaterialTheme.colors.onSecondary.copy(alpha = 0.9f))
                    )
            )
            recipe.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.recipesColors.onBackgroundBrand,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                )
            }
        }
    }
}
