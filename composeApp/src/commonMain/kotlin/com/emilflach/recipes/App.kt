package com.emilflach.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
        Column(modifier = Modifier.padding(16.dp)) {
            when {
                isLoading -> {
                    // Show loading indicator
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
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
                    // Show content
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(300.dp),
                        verticalItemSpacing = 16.dp,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        content = {
                            item(span = StaggeredGridItemSpan.FullLine) {
                                Text(
                                    text = "Emil & Lucia's ${recipes.count()} recipes",
                                    style = MaterialTheme.typography.h1,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            items(recipes.count()) { i ->
                                recipeCard(recipes[i])
                            }
                        }
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun recipeCard(recipe: Recipe) {
    return Card() {
        Column() {
            AsyncImage(
                model = recipe.image,
                contentScale = ContentScale.FillWidth,
                contentDescription = recipe.name,
                modifier = Modifier.fillMaxWidth()
            )
            recipe.name?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}