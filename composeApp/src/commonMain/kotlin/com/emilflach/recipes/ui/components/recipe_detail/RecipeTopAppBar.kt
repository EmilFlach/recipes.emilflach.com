package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeTopAppBar (
    recipe: Recipe?,
    listState: LazyListState,
    onBackClick: () -> Unit,
    maxWidth: Dp = 1000.dp,
) {
    val isSticking = listState.firstVisibleItemIndex > 0

    TopAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .widthIn(max = maxWidth),
        elevation = if (isSticking) 0.dp else 0.dp,
        title = {
            AnimatedVisibility(
                visible = isSticking,
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
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.background(
                    shape = RoundedCornerShape(50),
                    color = Black.copy (
                        alpha = if(isSticking) 0f else
                            lerp(
                                0.5f,
                                0f,
                                (listState.firstVisibleItemScrollOffset / 450f) - 0.95f
                            ).coerceIn(0f, 0.5f)
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isSticking)
                        MaterialTheme.recipesColors.foregroundDefault
                    else MaterialTheme.recipesColors.onBackgroundBrand
                )
            }
        },
        backgroundColor =
            if (isSticking)
                MaterialTheme.recipesColors.backgroundPage
            else
                MaterialTheme.recipesColors.backgroundPage.copy(
                    alpha = lerp(
                        0f,
                        1f,
                        (listState.firstVisibleItemScrollOffset / 450f) - 0.95f
                    ).coerceIn(0f, 1f)
                )
    )
}