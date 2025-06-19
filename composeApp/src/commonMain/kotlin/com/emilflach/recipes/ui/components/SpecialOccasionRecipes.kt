package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Recipe

@Composable
fun SpecialOccasionRecipes(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    Text(
        text = "Special occasions",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "Complex ingredients and prep, but worth the effort",
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    )
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().clipToBounds()
    ) {
        val pagerState = rememberPagerState { recipes.size }
        val scope = rememberCoroutineScope()
        val maxWidth = maxWidth
        val showTwoPages = maxWidth >= 700.dp

        Column {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 16.dp,
                contentPadding = pagerPadding(pagerState.currentPage, showTwoPages),
                modifier = Modifier
                    .height(if (showTwoPages) 400.dp else 250.dp)
                    .padding(end = if(showTwoPages) 16.dp else 0.dp),
                pageSize = if (showTwoPages) {
                    PageSize.Fixed((maxWidth - 48.dp) / 2) // Account for padding and spacing
                } else {
                    PageSize.Fill
                }

            ) {
                HighlightedRecipeCard(recipes[it], onRecipeClick)
            }

            if(showTwoPages) {
                Spacer(modifier = Modifier.height(16.dp))
                RecipePager(scope, pagerState, recipes.size)
            }
        }

    }
}