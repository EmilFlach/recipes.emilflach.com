package com.emilflach.recipes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.RecipesAppTheme


@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    recipeSlug: String,
    onBackClick: () -> Unit
) {
    val recipe by viewModel.recipe.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRecipeBySlug(recipeSlug)
    }
    RecipesAppTheme {
        Column {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = recipe?.name ?: "") },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
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
                        // Add your recipe detail content here
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Text(
                                text = "Recipe detail screen for ${recipe?.name}"
                            )
                            AsyncImage(
                                model = recipe?.imageUrl,
                                contentDescription = recipe?.name,
                                modifier = Modifier.height(200.dp).width(200.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
