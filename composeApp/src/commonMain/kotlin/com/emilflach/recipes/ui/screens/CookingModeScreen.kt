package com.emilflach.recipes.ui.screens

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.emilflach.recipes.RecipesAppTheme
import com.emilflach.recipes.ui.components.RecipeServingsScaler
import com.emilflach.recipes.ui.components.recipe_detail.RecipeMethodSection
import com.emilflach.recipes.ui.components.recipe_detail.RecipeTopAppBar
import com.emilflach.recipes.ui.components.recipe_detail.recipeInstructions
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun CookingModeScreen(
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
        viewModel.initialize(recipeSlug, cookingMode = true)
    }

    if (recipeData != null) {
        ScrollToCurrentInstructionEffect(
            scrollManager = scrollManager,
            isCookingMode = isCookingMode,
            currentInstruction = currentInstruction,
            ingredients = ingredients,
            hasInstructionSections = recipeData.hasInstructionSections,
            sectionedInstructions = sectionedInstructions,
            isCookingModeScreen = true
        )
    }



    RecipesAppTheme(cookingMode = true) {
        AnimatedContent(
            targetState = recipeData,
            contentKey = { it?.slug ?: "" }
        ) { recipe ->
            BoxWithConstraints(
                modifier = Modifier
                    .background(MaterialTheme.recipesColors.backgroundPage)
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentAlignment = Alignment.TopCenter,
            ) {
                val breakWidth = 700.dp
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .zIndex(1f)
                                .widthIn(max = breakWidth)
                        ) {
                            Text(
                                text = recipe?.name ?: "",
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.recipesColors.foregroundDefault,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 48.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    )
                            )
                        }
                    }

                    when {
                        isLoading -> {
                            item {
                                Column(
                                    modifier = Modifier.widthIn(max = breakWidth)
                                ) {
                                    BoxWithConstraints(
                                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(
                                                Alignment.Center
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        isError -> {
                            item {
                                Column(
                                    modifier = Modifier.widthIn(max = breakWidth)
                                ) {
                                    Text(
                                        text = "Error: $errorMessage",
                                        color = MaterialTheme.recipesColors.foregroundDanger,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }

                        else -> {
                            recipe?.let { recipe ->
                                item {
                                    Column(
                                        modifier = Modifier.widthIn(max = breakWidth)
                                    ) {
                                        RecipeServingsScaler(
                                            recipe,
                                            viewModel,
                                            currentServings
                                        )
                                    }
                                }
                                item {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }

                                item {
                                    Column(
                                        modifier = Modifier.widthIn(max = breakWidth)
                                    ) {
                                        RecipeMethodSection(
                                            isCookingMode = isCookingMode,
                                            onToggleCookingMode = { viewModel.toggleCookingMode() },
                                            showCookingButton = false
                                        )
                                    }
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
                RecipeTopAppBar(
                    recipe = recipeData,
                    listState = listState,
                    onBackClick = onBackClick,
                    maxWidth = breakWidth,
                )
            }
        }
    }
}
