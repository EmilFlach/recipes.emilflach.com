package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Ingredient
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun recipeIngredient(ingredient: Ingredient, displayBasicsOnly: Boolean = false) {
    val checkedState = remember { mutableStateOf(false) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp, end = 16.dp)
    ){
        Checkbox(
            onCheckedChange = { checkedState.value = it},
            checked = checkedState.value,
            modifier = Modifier.height(24.dp)
        )
        Column {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = ingredient.ingredient,
                    style = MaterialTheme.typography.body1,
                )
                if (!ingredient.url.isNullOrEmpty() && !displayBasicsOnly) {
                    val uriHandler = LocalUriHandler.current
                    IconButton(
                        onClick = {
                            uriHandler.openUri(ingredient.url)
                        },
                        modifier = Modifier.height(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Open URL",
                            tint = MaterialTheme.recipesColors.foregroundSupport,
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
            if (!ingredient.note.isNullOrEmpty()  && !displayBasicsOnly) {
                Text(
                    text = ingredient.note,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

        }
    }

}
