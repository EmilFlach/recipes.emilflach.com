package com.emilflach.recipes.ui.components.instruction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun InstructionNumber(
    index: Int,
    isDisabled: Boolean
) {
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
}