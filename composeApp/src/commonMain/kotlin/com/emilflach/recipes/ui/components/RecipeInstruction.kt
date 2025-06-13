package com.emilflach.recipes.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction
import com.emilflach.recipes.ui.components.instruction.InstructionContent
import com.emilflach.recipes.ui.components.instruction.InstructionNumber
import com.emilflach.recipes.ui.components.instruction.getRoundedCornerShape
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeInstruction(
    index: Int,
    size: Int,
    instruction: Instruction,
    isCookingMode: Boolean = false,
    isCurrentInstruction: Boolean = false,
    onInstructionClick: (Int) -> Unit = {}
) {
    val isExpanded = isCookingMode
    val isDisabled = isCookingMode && !isCurrentInstruction

    val padding by animateDpAsState(
        targetValue = if (isExpanded) 60.dp else 24.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val paddingWithCheckboxes by animateDpAsState(
        targetValue = if (isExpanded) 48.dp else 12.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(getRoundedCornerShape(index, size, isExpanded))
            .background(
                color = if (isDisabled) MaterialTheme.recipesColors.backgroundSurface1
                else MaterialTheme.recipesColors.backgroundPage
            )
            .then(
                if (isCookingMode) {
                    Modifier.clickable {
                        onInstructionClick(index)
                    }
                } else {
                    Modifier
                }
            )
    ) {
        Spacer(modifier = Modifier.height(padding))
        Row {
            InstructionNumber(index = index, isDisabled = isDisabled)
            InstructionContent(
                instruction = instruction,
                isDisabled = isDisabled,
                padding = padding,
                paddingWithCheckboxes = paddingWithCheckboxes
            )
        }
    }
}
