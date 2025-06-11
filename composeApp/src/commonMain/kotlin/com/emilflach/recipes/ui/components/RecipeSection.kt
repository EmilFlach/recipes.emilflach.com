package com.emilflach.recipes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.InstructionSection
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeSection(
    section: InstructionSection,
    isCookingMode: Boolean = false,
    currentInstruction: Int? = null,
    startIndex: Int = 0,
    onInstructionClick: (Int) -> Unit = {}

) {
    var isExpanded by rememberSaveable(section.title) { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 360f,
    )
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.recipesColors.backgroundBrandSubtle)


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(
                        end = 16.dp,
                    )
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded)
                        "Collapse section" else
                        "Expand section",
                    tint = MaterialTheme.recipesColors.foregroundDefault,
                    modifier = Modifier.graphicsLayer {
                        rotationZ = rotationState
                    }

                )
            }
            if(section.subtitle.isNotEmpty()) {
                Text(
                    text = section.subtitle,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                )
            }

        }
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Column {
            section.instructions.forEachIndexed { localIndex, instruction ->
                val globalIndex = startIndex + localIndex
                RecipeInstruction(
                    index = globalIndex,
                    size = section.instructions.size,
                    instruction = instruction,
                    isCookingMode = isCookingMode,
                    isCurrentInstruction = currentInstruction == globalIndex,
                    onInstructionClick = onInstructionClick
                )

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
