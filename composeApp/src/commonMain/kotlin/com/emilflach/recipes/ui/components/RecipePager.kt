package com.emilflach.recipes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.ui.theme.recipesColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipePager(scope: CoroutineScope, pagerState: PagerState, pageCount: Int)  {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PagerButton(
            isEnabled = pagerState.currentPage > 0,
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            contentDescription = "Previous page",
            onClick = { scope.launch {
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage - 1).coerceIn(0, pageCount - 1)
                )
            }}
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            repeat(pageCount - 1) { index ->
                PageIndicator(
                    isSelected = index == pagerState.currentPage,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        PagerButton(
            isEnabled = pagerState.currentPage < pageCount - 2,
            icon = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "Next page",
            onClick = {scope.launch {
                pagerState.animateScrollToPage(
                    page = (pagerState.currentPage + 1).coerceIn(0, pageCount - 1)
                )
            }}
        )
    }
}

@Composable
private fun PageIndicator(
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(if (isSelected) 8.dp else 6.dp)
            .background(
                color = if (isSelected) {
                    MaterialTheme.recipesColors.foregroundSupport
                } else {
                    MaterialTheme.recipesColors.foregroundDisabled
                },
                shape = CircleShape
            )
    )
}

@Composable
private fun PagerButton (
    isEnabled: Boolean,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .clip(CircleShape)
            .background(if(isEnabled) MaterialTheme.recipesColors.backgroundSurface1
            else Transparent)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if(isEnabled) MaterialTheme.recipesColors.foregroundDefault
            else MaterialTheme.recipesColors.foregroundDisabled
        )
    }
}
