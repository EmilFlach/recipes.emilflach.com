package com.emilflach.recipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeInstruction(index: Int, size: Int, instruction: Instruction) {
    val checkedState = rememberSaveable(instruction.text) { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                when {
                    index == 0 && size == 1 -> RoundedCornerShape(16.dp)
                    index == 0 -> RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 4.dp, bottomStart = 4.dp)
                    index == (size - 1) -> RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp, topStart = 4.dp, topEnd = 4.dp)
                    else -> RoundedCornerShape(4.dp)
                }
            ).background(
                color = if (checkedState.value)
                    MaterialTheme.recipesColors.backgroundSurface1
                else MaterialTheme.recipesColors.backgroundPage)
            .clickable { checkedState.value = !checkedState.value }
    ) {
        Text(
            text = "Step ${index + 1}:",
            style = MaterialTheme.typography.h4,
            color = if (checkedState.value)
                MaterialTheme.recipesColors.foregroundDisabled
            else MaterialTheme.recipesColors.foregroundDefault,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        )
        Text(
            text = instruction.text,
            color = if (checkedState.value)
                MaterialTheme.recipesColors.foregroundDisabled
            else MaterialTheme.recipesColors.foregroundDefault,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        )
        instruction.ingredients.forEach { ingredient ->
            RecipeIngredient(ingredient, displayBasicsOnly = true, disabled = checkedState.value)
        }
    }

}