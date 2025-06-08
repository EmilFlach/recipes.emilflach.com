package com.emilflach.recipes.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.ui.components.RecipeIngredient
import com.emilflach.recipes.ui.components.RecipeInstruction
import com.emilflach.recipes.ui.components.RecipeSection
import com.emilflach.recipes.ui.components.RecipeServingsScaler
import com.emilflach.recipes.ui.theme.recipesColors


@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel, recipeSlug: String, onBackClick: () -> Unit
) {
    val isError by viewModel.isError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val viewModelRecipe by viewModel.recipe.collectAsState()
    val currentServings by viewModel.currentServings.collectAsState()
    val ingredients by viewModel.formattedIngredients.collectAsState()
    val instructions by viewModel.instructions.collectAsState()
    val sectionedInstructions by viewModel.sectionedInstructions.collectAsState()

    val recipeData = viewModelRecipe
    LaunchedEffect(recipeSlug, recipeData) {
        if (recipeData != null) {
            viewModel.enrichRecipe(recipeData)
        } else {
            viewModel.getRecipeBySlug(recipeSlug)
        }
    }
    AnimatedContent(
        targetState = recipeData,
        contentKey = { it?.slug ?: "" }
    ) { recipe ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.recipesColors.backgroundPage)
                .fillMaxHeight()
                .fillMaxWidth()
                .safeDrawingPadding()
        ) {
            item {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().height(400.dp)
                ) {
                    if (recipe != null) {
                        AsyncImage(
                            model = recipe.imageUrl,
                            contentDescription = recipe.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(
                            Brush.verticalGradient(
                                // Gradient hacks, onSecondary is always black
                                0f to MaterialTheme.colors.onSecondary.copy(alpha = 0.9f),
                                0.3f to MaterialTheme.colors.onSecondary.copy(alpha = 0f),
                            )
                        )
                    )
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.recipesColors.onBackgroundBrand
                        )
                    }
                }
                Text(
                    text = recipe?.name ?: "",
                    style = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                )
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
                            color = MaterialTheme.recipesColors.foregroundDanger,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {
                    recipe?.let { recipe ->
                        item {
                            Text(
                                text = "Ingredients",
                                style = MaterialTheme.typography.h2,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                            )
                        }
                        item {
                            RecipeServingsScaler(
                                recipe,
                                viewModel,
                                currentServings
                            )
                        }
                        itemsIndexed(ingredients) { _, ingredient ->
                            RecipeIngredient(ingredient)
                        }

                        item {
                            Spacer(modifier = Modifier.height(32.dp))
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.recipesColors.backgroundSurface1,
                                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                    )
                                    .padding(16.dp)

                            ) {
                                Text(
                                    text = "Procedure",
                                    style = MaterialTheme.typography.h2,
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                )
                                if(recipe.hasInstructionSections) {
                                    sectionedInstructions.forEach { section ->
                                        RecipeSection(section)
                                    }
                                } else {
                                    instructions.forEachIndexed { index, instruction ->
                                        RecipeInstruction(index, instructions.size, instruction)
                                        Spacer(modifier = Modifier.height(4.dp))
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
