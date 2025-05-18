package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
        text = "When the moment call for a bake-off",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().clipToBounds()
    ) {
        val chunkedRecipes = remember { recipes.chunked(2) }
        val pagerState = rememberPagerState { chunkedRecipes.size }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            contentPadding = when (pagerState.currentPage) {
                pagerState.pageCount - 1 -> PaddingValues(start = 32.dp, end = 16.dp)
                else -> PaddingValues(start = 16.dp, end = 32.dp)
            },
            modifier = Modifier.height(350.dp),
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
    }
}