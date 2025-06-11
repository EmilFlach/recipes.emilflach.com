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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction
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

    val isExpanded = isCurrentInstruction && isCookingMode
    val isDisabled = isCookingMode && !isCurrentInstruction

    val padding by animateDpAsState(
        targetValue = expandedPadding(!isExpanded),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val paddingWithCheckboxes by animateDpAsState(
        targetValue = expandedPaddingWithCheckboxes(!isExpanded),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(getRoundedCornerShape(index, size, !isExpanded)).background(
                color = if (isDisabled) MaterialTheme.recipesColors.backgroundSurface1
                else MaterialTheme.recipesColors.backgroundPage)
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
            Text(
                text = "${index + 1}",
                style = MaterialTheme.typography.h4,
                color = if (isDisabled) MaterialTheme.recipesColors.foregroundDisabled
                    else MaterialTheme.recipesColors.foregroundDefault,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.recipesColors.backgroundSurface2)
                    .height(40.dp)
                    .width(40.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            Column {
                Text(
                    text = instruction.text,
                    color = if (isDisabled) MaterialTheme.recipesColors.foregroundDisabled
                        else MaterialTheme.recipesColors.foregroundDefault,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                if (instruction.ingredients.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    instruction.ingredients.forEach { ingredient ->
                        RecipeIngredient(ingredient, displayBasicsOnly = true, disabled = isDisabled)
                    }
                    Spacer(modifier = Modifier.height(paddingWithCheckboxes))
                } else {
                    Spacer(modifier = Modifier.height(padding))
                }
            }
        }
    }
}

fun expandedPadding(selected: Boolean) : Dp {
    return if(!selected) {
        100.dp
    } else {
        24.dp
    }
}

fun expandedPaddingWithCheckboxes(selected: Boolean) : Dp {
    return if(!selected) {
        88.dp
    } else {
        12.dp
    }
}

@Composable
private fun getRoundedCornerShape(index: Int, size: Int, isExpanded: Boolean): Shape {
    val cornerRadius by animateDpAsState(
        targetValue = if (isExpanded) 4.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    return when {
        index == 0 && size == 1 -> RoundedCornerShape(cornerRadius)
        index == 0 -> RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = cornerRadius,
            bottomEnd = cornerRadius
        )
        index == size - 1 -> RoundedCornerShape(
            bottomStart = 16.dp,
            bottomEnd = 16.dp,
            topStart = cornerRadius,
            topEnd = cornerRadius
        )
        else -> RoundedCornerShape(cornerRadius)
    }
}

