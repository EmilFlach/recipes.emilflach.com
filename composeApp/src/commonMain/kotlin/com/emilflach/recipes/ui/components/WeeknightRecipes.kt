package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun WeeknightRecipes(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit
) {

    Text(
        text = "Weeknights",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "Low effort and vegetarian",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val chunkedRecipes = remember { recipes.chunked(3) }
        val pagerState = rememberPagerState { chunkedRecipes.size }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            // Card height is set to 160, spacing to 8 (160 x 3 + 2 x 4 = 488)
            modifier = Modifier.height(488.dp),
            contentPadding = when (pagerState.currentPage) {
                pagerState.pageCount - 1 -> PaddingValues(start = 32.dp, end = 16.dp)
                else -> PaddingValues(start = 16.dp, end = 32.dp)
            }
        ) {
            val recipesOnPage = chunkedRecipes[it]
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                recipesOnPage.forEachIndexed { index, recipe ->
                    RecipeCard(index, recipe, onRecipeClick)
                    if (index < recipesOnPage.size - 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}