package com.emilflach.recipes.ui.components.instruction

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Get the appropriate corner shape for an instruction based on its position and expansion state
 */
@Composable
fun getRoundedCornerShape(index: Int, size: Int, isExpanded: Boolean): Shape {
    val cornerRadius by animateDpAsState(
        targetValue = if (!isExpanded) 4.dp else 16.dp,
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