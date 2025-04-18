package com.emilflach.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.components.RecipeCard
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val repository = RecipeRepository()
            recipes = withContext(Dispatchers.Default) {
                repository.getRecipes(false).items
            }
            isLoading = false
        } catch (e: Exception) {
            error = e.message
            isLoading = false
        }
    }
    RecipesAppTheme {
        Column {
            when {
                isLoading -> {
                    // Show loading indicator
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )

                    }
                }
                error != null -> {
                    // Show error message
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn {
                        item {
                            Text(
                                text = "Emil & Lucia's ${recipes.count()} recipes",
                                style = MaterialTheme.typography.h1,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 32.dp, vertical = 32.dp),
                            )
                        }
                        item {
                            Text(
                                text = "Weeknights",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
                            )
                            Text(
                                text = "Less than 20 minutes and vegetarian",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            )
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val itemWidth = maxWidth * 0.9f
                                LazyHorizontalGrid(
                                    modifier = Modifier.height(700.dp),
                                    rows = GridCells.Fixed(3),
                                    verticalArrangement = Arrangement.spacedBy(32.dp)
                                ) {
                                    val shuffledRecipes = recipes.shuffled()
                                    items(recipes.count()) { i ->
                                        RecipeCard(shuffledRecipes[i], itemWidth)
                                    }
                                }
                            }
                        }
                        item {
                            Text(
                                text = "Special occasions",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            )
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val itemWidth = maxWidth * 0.9f
                                LazyHorizontalGrid(
                                    modifier = Modifier.height(700.dp).padding(bottom = 32.dp),
                                    rows = GridCells.Fixed(3),
                                    verticalArrangement = Arrangement.spacedBy(32.dp),
                                ) {
                                    val shuffledRecipes = recipes.shuffled()
                                    items(recipes.count()) { i ->
                                        RecipeCard(shuffledRecipes[i], itemWidth)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
