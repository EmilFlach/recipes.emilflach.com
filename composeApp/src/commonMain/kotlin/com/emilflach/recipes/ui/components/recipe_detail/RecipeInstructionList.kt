package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction
import com.emilflach.recipes.data.InstructionSection
import com.emilflach.recipes.ui.components.RecipeInstruction
import com.emilflach.recipes.ui.theme.recipesColors

/**
 * Extension function for LazyListScope to add instructions to the list
 */
fun LazyListScope.recipeInstructions(
    hasInstructionSections: Boolean,
    sectionedInstructions: List<InstructionSection>,
    instructions: List<Instruction>,
    expandedSections: Set<String>,
    isCookingMode: Boolean,
    currentInstruction: Int,
    listState: LazyListState,
    onInstructionClick: (Int) -> Unit,
    onToggleSectionExpanded: (String) -> Unit
) {
    if (hasInstructionSections) {
        sectionedInstructions.forEachIndexed { index, section ->
            val isExpanded = expandedSections.contains(section.title) || isCookingMode

            stickyHeader { headerIndex ->
                val isSticking by remember(listState) { listState.isSticking(headerIndex) }
                val offsetY by animateDpAsState(
                    targetValue = if (isSticking) 56.dp else 0.dp,
                    label = "headerOffset"
                )

                Box(
                    Modifier
                        .background(MaterialTheme.recipesColors.backgroundSurface1)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = when { isExpanded -> 16.dp else -> 0.dp })
                        .offset(y = offsetY)
                ) {
                    SectionHeader(
                        title = section.title,
                        subtitle = section.subtitle,
                        isExpanded = isExpanded,
                        isSticking = isSticking,
                        isClickable = !isCookingMode,
                        onToggleExpanded = { onToggleSectionExpanded(section.title) }
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
                        val globalInstructionIndex = instruction.globalIndex
                        RecipeInstruction(
                            index = index,
                            size = section.instructions.size,
                            instruction = instruction,
                            isCookingMode = isCookingMode,
                            isCurrentInstruction = currentInstruction == globalInstructionIndex,
                            onInstructionClick = { onInstructionClick(globalInstructionIndex) }
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
                    onInstructionClick = onInstructionClick
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