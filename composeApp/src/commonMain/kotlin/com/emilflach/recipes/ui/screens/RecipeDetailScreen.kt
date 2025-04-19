package com.emilflach.recipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            item {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    if (recipe != null) {
                        AsyncImage(
                            model = recipe?.imageUrl,
                            contentDescription = recipe?.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(
                                Brush.verticalGradient(
                                    0f to Color.Black,
                                    0.3f to Color.Transparent,
                                )
                            )
                    )

                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }
                }

            }

            when {
                isLoading -> {
                    item {
                        BoxWithConstraints(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                }

                isError -> {
                    item {
                        Text(
                            text = "Error: $errorMessage",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(16.dp)
                        )

                    }

                }

                else -> {
                    item {
                        Text(
                            text = recipe?.name ?: "",
                            style = MaterialTheme.typography.h1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                        )
                    }
                    item {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                        )
                    }
                    recipe?.let {
                        itemsIndexed(it.recipeIngredient) { _, ingredient ->
                            Text(
                                text = "${ingredient.quantity} ${ingredient.unit?.name} ${ingredient.food?.name}",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Procedure",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        )
                    }
                    recipe?.let {
                        itemsIndexed(it.recipeInstructions) { index, instruction ->
                            Text(
                                text = "${index + 1}. ${instruction.text}",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                            )
                        }
                    }

                }
            }
        }
    }
}
