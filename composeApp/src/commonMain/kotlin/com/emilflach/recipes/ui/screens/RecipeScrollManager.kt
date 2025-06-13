package com.emilflach.recipes.ui.screens

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.emilflach.recipes.data.Ingredient
import com.emilflach.recipes.data.InstructionSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A class that manages scrolling in the recipe detail screen.
 * It encapsulates the logic for calculating the index of an instruction in the LazyList
 * and scrolling to that instruction.
 */
class RecipeScrollManager(
    private val listState: LazyListState,
    private val coroutineScope: CoroutineScope
) {
    /**
     * Scrolls to the current instruction if cooking mode is enabled.
     */
    fun scrollToCurrentInstruction(
        isCookingMode: Boolean,
        currentInstruction: Int,
        ingredients: List<Ingredient>,
        hasInstructionSections: Boolean,
        sectionedInstructions: List<InstructionSection>
    ) {
        if (!isCookingMode) return

        coroutineScope.launch {
            val staticListItems = 5 // Headers, spacers, etc.
            val ingredientListItems = ingredients.size

            val listItemIndex = calculateLazyListIndex(
                staticItemsCount = staticListItems,
                ingredientsCount = ingredientListItems,
                globalInstructionIndex = currentInstruction,
                hasInstructionSections = hasInstructionSections,
                sectionedInstructions = sectionedInstructions
            )

            // Scroll to the instruction
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

    /**
     * Calculates the LazyList index for a given instruction.
     */
    private fun calculateLazyListIndex(
        staticItemsCount: Int,
        ingredientsCount: Int,
        globalInstructionIndex: Int,
        hasInstructionSections: Boolean,
        sectionedInstructions: List<InstructionSection>
    ): Int {
        if (!hasInstructionSections) {
            // For non-sectioned instructions, the calculation is simple
            return globalInstructionIndex + staticItemsCount + ingredientsCount
        }

        // For sectioned instructions, we need to calculate the position in the LazyList
        var itemCount = staticItemsCount + ingredientsCount

        // Find which section contains the instruction
        val (sectionIndex, instructionIndexInSection) = findSectionAndInstructionIndex(
            globalIndex = globalInstructionIndex,
            sectionedInstructions = sectionedInstructions
        )

        // Add items for all sections before the target section
        // Each section adds: 1 header + instructions.size + 1 spacer
        for (i in 0 until sectionIndex) {
            itemCount += 1 // Header
            itemCount += sectionedInstructions[i].instructions.size // Instructions
            itemCount += 1 // Spacer
        }

        // Add header for the target section
        itemCount += 1

        // Add the instruction index within the target section
        itemCount += instructionIndexInSection

        return itemCount
    }

    /**
     * Finds the section and instruction index for a given global instruction index.
     * Returns Pair(sectionIndex, instructionIndexInSection)
     */
    private fun findSectionAndInstructionIndex(
        globalIndex: Int,
        sectionedInstructions: List<InstructionSection>
    ): Pair<Int, Int> {
        for ((sectionIndex, section) in sectionedInstructions.withIndex()) {
            // Find the instruction with the matching globalIndex
            val instructionIndexInSection = section.instructions.indexOfFirst { it.globalIndex == globalIndex }
            if (instructionIndexInSection != -1) {
                // Found the instruction in this section
                return Pair(sectionIndex, instructionIndexInSection)
            }
        }

        // If we didn't find the instruction (e.g., globalIndex is invalid),
        // default to the last instruction of the last section
        if (sectionedInstructions.isNotEmpty()) {
            val lastSectionIndex = sectionedInstructions.size - 1
            val lastInstructionIndex = sectionedInstructions.last().instructions.size - 1
            return Pair(lastSectionIndex, lastInstructionIndex)
        }

        return Pair(0, 0)
    }
}

/**
 * Extension function for LazyListState to scroll to a specific instruction.
 * This is imported from RecipeDetailScreen.kt to avoid duplication.
 */
// The scrollToInstruction extension function is defined in RecipeDetailScreen.kt

/**
 * Composable function to create and remember a RecipeScrollManager.
 */
@Composable
fun rememberRecipeScrollManager(
    listState: LazyListState,
    coroutineScope: CoroutineScope
): RecipeScrollManager {
    return remember(listState, coroutineScope) {
        RecipeScrollManager(listState, coroutineScope)
    }
}

/**
 * Composable function that sets up a LaunchedEffect to scroll to the current instruction.
 */
@Composable
fun ScrollToCurrentInstructionEffect(
    scrollManager: RecipeScrollManager,
    isCookingMode: Boolean,
    currentInstruction: Int,
    ingredients: List<Ingredient>,
    hasInstructionSections: Boolean,
    sectionedInstructions: List<InstructionSection>
) {
    LaunchedEffect(currentInstruction, isCookingMode) {
        scrollManager.scrollToCurrentInstruction(
            isCookingMode = isCookingMode,
            currentInstruction = currentInstruction,
            ingredients = ingredients,
            hasInstructionSections = hasInstructionSections,
            sectionedInstructions = sectionedInstructions
        )
    }
}
