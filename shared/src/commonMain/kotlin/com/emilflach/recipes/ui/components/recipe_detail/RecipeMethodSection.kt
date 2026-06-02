package com.emilflach.recipes.ui.components.recipe_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeMethodSection(
    isCookingMode: Boolean,
    onToggleCookingMode: () -> Unit,
    showCookingButton: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.recipesColors.backgroundSurface1,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(start = 8.dp, end = 24.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Method",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(16.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        if(showCookingButton) {
            if (isCookingMode) {
                OutlinedButton(
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.recipesColors.borderBrand
                    ),
                    onClick = onToggleCookingMode
                ) {
                    Text(
                        text = "Stop cooking",
                        color = MaterialTheme.recipesColors.foregroundDefault
                    )
                }
            } else {
                Button(
                    onClick = onToggleCookingMode
                ) {
                    Text("Start cooking", color = MaterialTheme.recipesColors.onBackgroundBrand)
                }
            }
        }
    }
}