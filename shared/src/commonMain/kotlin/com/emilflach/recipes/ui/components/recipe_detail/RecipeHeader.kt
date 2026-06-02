package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.data.Recipe

@Composable
fun RecipeHeader(
        recipe: Recipe?,
        scrollState: LazyListState,
        modifier: Modifier
    ) {
        val maxHeight = 500.dp
        val minHeight = 400.dp

        val density = LocalDensity.current
        val headerHeight by remember {
            derivedStateOf {
                with(density) {
                    val totalScrollOffset = scrollState.firstVisibleItemIndex * maxHeight.toPx() +
                            scrollState.firstVisibleItemScrollOffset.toFloat()
                    val height = (maxHeight.toPx() - totalScrollOffset).coerceIn(
                        minHeight.toPx(),
                        maxHeight.toPx()
                    )
                    height.toDp()
                }
            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) {
            if (recipe != null) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }