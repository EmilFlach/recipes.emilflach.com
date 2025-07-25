package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun DessertRecipes(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    Text(
        text = "Desserts and baked goods",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "When the moment calls for a bake-off",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().clipToBounds()
    ) {
        val chunkedRecipes = remember { recipes.chunked(2) }
        val pagerState = rememberPagerState { chunkedRecipes.size }
        val scope = rememberCoroutineScope()
        val maxWidth = maxWidth
        val showFourPages = maxWidth >= 700.dp

        Column {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 16.dp,
                contentPadding = pagerPadding(pagerState.currentPage, chunkedRecipes.size, showFourPages),
                modifier = if(showFourPages) {
                    Modifier
                        .height(450.dp)
                        .padding(end = 16.dp)
                } else {
                    Modifier.height(350.dp)
                },
                pageSize = if (showFourPages) {
                    PageSize.Fixed((maxWidth - 48.dp) / 2) // Account for padding and spacing
                } else {
                    PageSize.Fill
                }

            ) {
                val recipesOnThisPage = chunkedRecipes[it]
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    recipesOnThisPage.forEachIndexed { indexInPage, recipe ->
                        HighlightedRecipeCard(
                            recipe = recipe,
                            onRecipeClick = onRecipeClick,
                            modifier = Modifier.weight(1f)
                        )
                        if (indexInPage < recipesOnThisPage.size - 1) {
                            Spacer(Modifier.width(16.dp))
                        }
                    }
                    if (recipesOnThisPage.size < 2) {
                        for (i in recipesOnThisPage.size until 2) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }

            if(showFourPages) {
                Spacer(modifier = Modifier.height(16.dp))
                RecipePager(scope, pagerState, chunkedRecipes.size)
            }
        }
    }
}