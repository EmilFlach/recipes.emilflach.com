package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    isExpanded: Boolean = false,
    isSticking: Boolean = false,
    isClickable: Boolean = false,
    onToggleExpanded: () -> Unit = {}
) {
    val showSubtitle = subtitle.isNotEmpty() && !isExpanded
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

                AnimatedVisibility(
                    visible = showSubtitle,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.body1
                    )
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

fun LazyListState.isSticking(index: Int): State<Boolean> {
    return derivedStateOf {
        val firstVisible = layoutInfo.visibleItemsInfo.firstOrNull()
        firstVisible?.index == index && firstVisible.offset == -layoutInfo.beforeContentPadding
    }
}