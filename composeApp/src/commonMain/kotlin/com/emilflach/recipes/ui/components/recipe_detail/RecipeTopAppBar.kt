package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeTopAppBar (
    recipe: Recipe?,
    listState: LazyListState,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        elevation = if (listState.firstVisibleItemIndex > 0) 16.dp else 0.dp,
        title = {
            AnimatedVisibility(
                visible = listState.firstVisibleItemIndex > 0,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = recipe?.name ?: "",
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.recipesColors.onBackgroundBrand
                )
            }
        },
        backgroundColor =
            if (listState.firstVisibleItemIndex > 0)
                MaterialTheme.recipesColors.backgroundSurface1
            else
                MaterialTheme.recipesColors.backgroundSurface1.copy(
                    alpha = lerp(
                        0f,
                        1f,
                        listState.firstVisibleItemScrollOffset / 600f
                    ).coerceIn(0f, 1f)
                )
    )
}