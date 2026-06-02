package com.emilflach.recipes.ui.components.instruction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Instruction
import com.emilflach.recipes.ui.components.RecipeIngredient
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun InstructionContent(
    instruction: Instruction,
    isDisabled: Boolean,
    padding: Dp,
    paddingWithCheckboxes: Dp
) {
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