package com.emilflach.recipes.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.ui.components.RecipeIngredient
import com.emilflach.recipes.ui.components.RecipeServingsScaler
import com.emilflach.recipes.ui.components.recipe_detail.RecipeHeader
import com.emilflach.recipes.ui.components.recipe_detail.RecipeMethodSection
import com.emilflach.recipes.ui.components.recipe_detail.recipeInstructions
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel, recipeSlug: String, onBackClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollManager = rememberRecipeScrollManager(listState, coroutineScope)

    val isError by viewModel.isError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isCookingMode by viewModel.isCookingMode.collectAsState()
    val currentInstruction by viewModel.currentInstruction.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val viewModelRecipe by viewModel.recipe.collectAsState()
    val currentServings by viewModel.currentServings.collectAsState()
    val ingredients by viewModel.formattedIngredients.collectAsState()
    val instructions by viewModel.instructions.collectAsState()
    val sectionedInstructions by viewModel.sectionedInstructions.collectAsState()
    val expandedSections by viewModel.expandedSections.collectAsState()
    val recipeData = viewModelRecipe

    LaunchedEffect(recipeSlug) {
        viewModel.initialize(recipeSlug)
    }

    // Use the ScrollToCurrentInstructionEffect to handle scrolling
    if (recipeData != null) {
        ScrollToCurrentInstructionEffect(
            scrollManager = scrollManager,
            isCookingMode = isCookingMode,
            currentInstruction = currentInstruction,
            ingredients = ingredients,
            hasInstructionSections = recipeData.hasInstructionSections,
            sectionedInstructions = sectionedInstructions
        )
    }

    AnimatedContent(
        targetState = recipeData,
        contentKey = { it?.slug ?: "" }
    ) { recipe ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(MaterialTheme.recipesColors.backgroundPage)
                .fillMaxHeight()
                .fillMaxWidth()
                .safeDrawingPadding()
        ) {
            item {
                RecipeHeader(recipe = recipe, onBackClick = onBackClick)
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
                        }

                        item {
                            RecipeMethodSection(
                                isCookingMode = isCookingMode,
                                onToggleCookingMode = { viewModel.toggleCookingMode() }
                            )
                        }

                        recipeInstructions(
                            hasInstructionSections = recipe.hasInstructionSections,
                            sectionedInstructions = sectionedInstructions,
                            instructions = instructions,
                            expandedSections = expandedSections,
                            isCookingMode = isCookingMode,
                            currentInstruction = currentInstruction,
                            listState = listState,
                            onInstructionClick = viewModel::setCurrentInstruction,
                            onToggleSectionExpanded = viewModel::toggleSectionExpanded
                        )
                    }
                }
            }
        }
    }
}
