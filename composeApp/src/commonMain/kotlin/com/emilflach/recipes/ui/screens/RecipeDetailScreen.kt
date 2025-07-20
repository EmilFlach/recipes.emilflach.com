package com.emilflach.recipes.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.ui.components.RecipeIngredient
import com.emilflach.recipes.ui.components.RecipeInstruction
import com.emilflach.recipes.ui.components.RecipeServingsScaler
import com.emilflach.recipes.ui.components.recipe_detail.RecipeHeader
import com.emilflach.recipes.ui.components.recipe_detail.RecipeMethodSection
import com.emilflach.recipes.ui.components.recipe_detail.RecipeTopAppBar
import com.emilflach.recipes.ui.components.recipe_detail.SectionHeader
import com.emilflach.recipes.ui.components.recipe_detail.recipeInstructions
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    recipeSlug: String,
    onBackClick: () -> Unit,
    onCookingModeClick: (Recipe) -> Unit,
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
        BoxWithConstraints(
            modifier = Modifier
                .background(MaterialTheme.recipesColors.backgroundPage)
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.TopCenter,
        ) {
            val maxWidth = maxWidth
            val showTwoColumns = maxWidth >= 700.dp
            val breakWidth = 1000.dp
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if(showTwoColumns) {Alignment.CenterHorizontally} else {Alignment.Start},
            ) {

                item {
                    Column (
                        modifier = Modifier.widthIn(max = breakWidth)
                    ) {
                        RecipeHeader(
                            recipe = recipe,
                            scrollState = listState,
                            modifier = Modifier.zIndex(0f)
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .offset(y = (-16).dp)
                            .zIndex(1f)
                            .widthIn(max = breakWidth)
                    ) {
                        Text(
                            text = recipe?.name ?: "",
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.recipesColors.foregroundDefault,
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 16.dp,
                                        topStart = 16.dp
                                    )
                                )
                                .fillMaxWidth()
                                .background(MaterialTheme.recipesColors.backgroundPage)
                                .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 8.dp)
                        )
                    }
                }

                when {
                    isLoading -> {
                        item {
                            Column (
                                modifier = Modifier.widthIn(max = breakWidth)
                            ) {
                                BoxWithConstraints(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }
                    }

                    isError -> {
                        item {
                            Column (
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
                            if (showTwoColumns) {
                                item {
                                    Row (
                                        modifier = Modifier.widthIn(max = breakWidth)
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "Ingredients",
                                                style = MaterialTheme.typography.h2,
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(
                                                        start = 24.dp,
                                                        end = 24.dp,
                                                        bottom = 16.dp
                                                    ),
                                            )
                                            RecipeServingsScaler(
                                                recipe,
                                                viewModel,
                                                currentServings
                                            )
                                            ingredients.forEachIndexed { _, ingredient ->
                                                RecipeIngredient(ingredient)
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))
                                        }
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            RecipeMethodSection(
                                                isCookingMode = isCookingMode,
                                                onToggleCookingMode = { onCookingModeClick(recipe) },
                                                showCookingButton = true
                                            )
                                            if (recipe.hasInstructionSections) {
                                                sectionedInstructions.forEachIndexed { index, section ->
                                                    val isExpanded =
                                                        expandedSections.contains(section.title) || isCookingMode
                                                    Box(
                                                        Modifier
                                                            .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                            .padding(
                                                                start = 16.dp,
                                                                end = 16.dp,
                                                                top = 16.dp,
                                                                bottom = when {
                                                                    isExpanded -> 16.dp
                                                                    else -> 0.dp
                                                                }
                                                            )
                                                    ) {
                                                        SectionHeader(
                                                            title = section.title,
                                                            subtitle = section.subtitle,
                                                            isExpanded = isExpanded,
                                                            isSticking = false,
                                                            isClickable = !isCookingMode,
                                                            onToggleExpanded = {
                                                                viewModel.toggleSectionExpanded(
                                                                    section.title
                                                                )
                                                            }
                                                        )
                                                    }
                                                    if (index == sectionedInstructions.size - 1 && !isExpanded) {
                                                        Box(
                                                            modifier = Modifier
                                                                .height(16.dp)
                                                                .fillMaxWidth()
                                                                .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                        )
                                                    }

                                                    if (isExpanded) {
                                                        section.instructions.forEachIndexed { index, instruction ->
                                                            Box(
                                                                Modifier
                                                                    .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                                    .padding(
                                                                        start = 16.dp,
                                                                        end = 16.dp,
                                                                        bottom = 4.dp
                                                                    )
                                                            ) {
                                                                val globalInstructionIndex =
                                                                    instruction.globalIndex
                                                                RecipeInstruction(
                                                                    index = index,
                                                                    size = section.instructions.size,
                                                                    instruction = instruction,
                                                                    isCookingMode = isCookingMode,
                                                                    isCurrentInstruction = currentInstruction == globalInstructionIndex,
                                                                    onInstructionClick = {
                                                                        viewModel.setCurrentInstruction(
                                                                            globalInstructionIndex
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        }
                                                        Box(
                                                            modifier = Modifier
                                                                .height(32.dp)
                                                                .fillMaxWidth()
                                                                .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                        )
                                                    }
                                                }
                                            } else {

                                                instructions.forEachIndexed { index, instruction ->
                                                    Box(
                                                        Modifier
                                                            .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                            .padding(
                                                                start = 16.dp,
                                                                end = 16.dp,
                                                                bottom = 4.dp
                                                            )
                                                    ) {
                                                        RecipeInstruction(
                                                            index = index,
                                                            size = instructions.size,
                                                            instruction = instruction,
                                                            isCookingMode = isCookingMode,
                                                            isCurrentInstruction = currentInstruction == index,
                                                            onInstructionClick = viewModel::setCurrentInstruction
                                                        )
                                                    }
                                                }
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .height(16.dp)
                                                    .fillMaxWidth()
                                                    .background(
                                                        color = MaterialTheme.recipesColors.backgroundSurface1,
                                                        shape = RoundedCornerShape(
                                                            bottomStart = 16.dp,
                                                            bottomEnd = 16.dp
                                                        )
                                                    )
                                            )
                                            Spacer(modifier = Modifier.height(16.dp))
                                        }
                                    }
                                }
                            } else {
                                item {
                                    Text(
                                        text = "Ingredients",
                                        style = MaterialTheme.typography.h2,
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
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
            RecipeTopAppBar(
                recipe = recipeData,
                listState = listState,
                onBackClick = onBackClick
            )
        }
    }
}
