package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeHeader(
    recipe: Recipe?,
    onBackClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().height(400.dp)
    ) {
        if (recipe != null) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().background(
                Brush.verticalGradient(
                    // Gradient hacks, onSecondary is always black
                    0f to MaterialTheme.colors.onSecondary.copy(alpha = 0.9f),
                    0.3f to MaterialTheme.colors.onSecondary.copy(alpha = 0f),
                )
            )
        )
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.recipesColors.onBackgroundBrand
            )
        }
    }
    Text(
        text = recipe?.name ?: "",
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    )
}