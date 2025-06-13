package com.emilflach.recipes.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.emilflach.recipes.ui.components.RecipeIngredient
import com.emilflach.recipes.ui.components.RecipeInstruction
import com.emilflach.recipes.ui.components.RecipeServingsScaler
import com.emilflach.recipes.ui.theme.recipesColors


@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel, recipeSlug: String, onBackClick: () -> Unit
) {
    val listState = rememberLazyListState()
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

    LaunchedEffect(recipeSlug, recipeData) {
        if (recipeData != null) {
            viewModel.enrichRecipe(recipeData)
        } else {
            viewModel.getRecipeBySlug(recipeSlug)
        }
    }

    LaunchedEffect(currentInstruction, isCookingMode) {
        if(isCookingMode) {
            val staticListItems = 5
            val ingredientListItems = ingredients.size

            // Use the ViewModel's utility function to calculate the LazyList index
            val listItemIndex = viewModel.calculateLazyListIndex(
                staticItemsCount = staticListItems,
                ingredientsCount = ingredientListItems,
                globalInstructionIndex = currentInstruction,
                hasInstructionSections = recipeData!!.hasInstructionSections
            )

            val layoutInfo = listState.layoutInfo
            val viewportHeight = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset
            val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == listItemIndex }
            if (itemInfo != null) {
                val itemHeight = itemInfo.size
                val centerOffset = (viewportHeight - itemHeight) / 2

                listState.animateScrollToItem(
                    index = listItemIndex,
                    scrollOffset = -centerOffset
                )
            }
        }
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
                        }

                        item {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.recipesColors.backgroundSurface1,
                                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                    )
                                    .padding(start = 4.dp, end = 16.dp, top = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Method",
                                    style = MaterialTheme.typography.h2,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                if (isCookingMode) {
                                    OutlinedButton(
                                        border = BorderStroke (
                                            1.dp,
                                            MaterialTheme.recipesColors.borderBrand
                                        ),
                                        onClick = { viewModel.toggleCookingMode() }
                                    ) {
                                        Text(
                                            text = "Stop cooking",
                                            color = MaterialTheme.recipesColors.foregroundDefault
                                        )
                                    }
                                } else {
                                    Button(
                                        onClick = { viewModel.toggleCookingMode() }
                                    ) {
                                        Text("Start cooking")
                                    }
                                }
                            }
                        }
                        if(recipe.hasInstructionSections) {
                            sectionedInstructions.forEachIndexed { index, section ->
                                val isExpanded = expandedSections.contains(section.title) || isCookingMode

                                stickyHeader { headerIndex ->
                                    val isSticking by remember(listState) { listState.isSticking(headerIndex) }
                                    Box(
                                        Modifier
                                            .background(MaterialTheme.recipesColors.backgroundSurface1)
                                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = when { isExpanded -> 16.dp else -> 0.dp })
                                    ) {
                                        SectionHeader(
                                            title = section.title,
                                            subtitle = section.subtitle,
                                            isExpanded = isExpanded,
                                            isSticking = isSticking,
                                            isClickable = !isCookingMode,
                                            onToggleExpanded = { viewModel.toggleSectionExpanded(section.title) }
                                        )
                                    }
                                    if (index == sectionedInstructions.size - 1 && !isExpanded) {
                                        Box(modifier = Modifier
                                            .height(16.dp)
                                            .fillMaxWidth()
                                            .background(MaterialTheme.recipesColors.backgroundSurface1))
                                    }
                                }

                                if (isExpanded) {
                                    itemsIndexed(section.instructions) { index, instruction ->
                                        Box(
                                            Modifier
                                                .background(MaterialTheme.recipesColors.backgroundSurface1)
                                                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                                        ) {
                                            // Use the globalIndex property from the instruction
                                            val globalInstructionIndex = instruction.globalIndex

                                            RecipeInstruction(
                                                index = index,
                                                size = section.instructions.size,
                                                instruction = instruction,
                                                isCookingMode = isCookingMode,
                                                isCurrentInstruction = currentInstruction == globalInstructionIndex,
                                                onInstructionClick = { viewModel.setCurrentInstruction(globalInstructionIndex) }
                                            )
                                        }
                                    }
                                    item {
                                        Box(modifier = Modifier
                                            .height(32.dp)
                                            .fillMaxWidth()
                                            .background(MaterialTheme.recipesColors.backgroundSurface1))
                                    }
                                }
                            }

                        } else {
                            itemsIndexed(instructions) { index, instruction ->
                                Box(Modifier
                                    .background(MaterialTheme.recipesColors.backgroundSurface1)
                                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)) {
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
                            item {
                                Box(modifier = Modifier
                                    .height(32.dp)
                                    .fillMaxWidth()
                                    .background(MaterialTheme.recipesColors.backgroundSurface1))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun LazyListState.isSticking(index: Int): State<Boolean> {
    return derivedStateOf {
        val firstVisible = layoutInfo.visibleItemsInfo.firstOrNull()
        firstVisible?.index == index && firstVisible.offset == -layoutInfo.beforeContentPadding
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    isExpanded: Boolean = false,
    isSticking: Boolean = false,
    isClickable: Boolean = false,
    onToggleExpanded: () -> Unit = {}
) {
    val showSubtitle = subtitle.isNotEmpty() && isClickable
    val showSubtitleSpacing = showSubtitle && !isSticking

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.recipesColors.backgroundSurface2)
            .then(
                if (isClickable) {
                    Modifier.clickable {
                        onToggleExpanded()
                    }
                } else {
                    Modifier
                }
            ).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = if (showSubtitleSpacing) 8.dp else 0.dp)
            )
            if (showSubtitle) {
                AnimatedVisibility(
                    visible = !isSticking,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }

        if (isClickable) {
            val rotationState by animateFloatAsState(
                targetValue = if (isExpanded) 180f else 360f,
            )
            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.recipesColors.foregroundDefault,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .graphicsLayer {
                        rotationZ = rotationState
                    }
            )
        }
    }
}
