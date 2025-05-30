package com.emilflach.recipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.screens.RecipeDetailViewModel
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeServingsScaler(
    recipe: Recipe,
    viewModel: RecipeDetailViewModel,
    currentServings: Double?
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.recipesColors.backgroundSurface1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Servings",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = "(${recipe.calories} each)",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { viewModel.decreaseServings() }) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease servings",
                tint = MaterialTheme.recipesColors.foregroundDefault
            )
        }
        Text(
            text = currentServings?.toInt().toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.width(30.dp),
            textAlign = Center
        )
        IconButton(onClick = { viewModel.increaseServings() }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase servings",
                tint = MaterialTheme.recipesColors.foregroundDefault,
            )
        }
    }
}