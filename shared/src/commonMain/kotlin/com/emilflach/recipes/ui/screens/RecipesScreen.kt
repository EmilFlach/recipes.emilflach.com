package com.emilflach.recipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.components.DessertRecipes
import com.emilflach.recipes.ui.components.SpecialOccasionRecipes
import com.emilflach.recipes.ui.components.WeeknightRecipes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    onRecipeClick: (Recipe) -> Unit
) {
    val recipes by viewModel.recipes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val weeknightRecipes by viewModel.weeknightRecipes.collectAsState()
    val specialOccasionRecipes by viewModel.specialOccasionRecipes.collectAsState()
    val bakingRecipes by viewModel.bakingRecipes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRecipes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .safeDrawingPadding()
    ) {
        when {
            isLoading -> {
                BoxWithConstraints(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            isError -> {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                val listState = rememberLazyListState()
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val breakWidth = 1000.dp
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Column  (
                                modifier = Modifier.widthIn(max = breakWidth)
                            ) {
                            Spacer(modifier = Modifier.height(60.dp))
                            Text(
                                text = "Emil & Lucia's ${recipes.count()} recipes",
                                style = MaterialTheme.typography.h1,
                                fontSize = 48.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(horizontal = 48.dp)
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(48.dp))
                            }
                        }
                        item {
                            Column (
                                modifier = Modifier.widthIn(max = breakWidth)
                            ) {
                                WeeknightRecipes(weeknightRecipes, onRecipeClick)
                                Spacer(modifier = Modifier.height(60.dp))
                            }
                        }
                        item {
                            Column (
                                modifier = Modifier.widthIn(max = breakWidth)
                            ) {
                                SpecialOccasionRecipes(specialOccasionRecipes, onRecipeClick)
                                Spacer(modifier = Modifier.height(60.dp))
                            }
                        }
                        item {
                            Column (
                                modifier = Modifier.widthIn(max = breakWidth)
                            ) {
                                DessertRecipes(bakingRecipes, onRecipeClick)
                                Spacer(modifier = Modifier.height(32.dp))
                            }

                        }
                    }
                }
            }
        }
    }
}