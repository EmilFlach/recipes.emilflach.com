package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.emilflach.recipes.data.Ingredient
import com.emilflach.recipes.ui.theme.recipesColors

@Composable
fun RecipeIngredient(ingredient: Ingredient, displayBasicsOnly: Boolean = false, disabled : Boolean = false) {
    val checkedState = rememberSaveable(ingredient.text) { mutableStateOf(false) }
    if(!ingredient.sectionTitle.isNullOrEmpty() && !displayBasicsOnly) {
        Text(
            text = ingredient.sectionTitle,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 16.dp)
        )
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            bottom = 12.dp,
            start = if(displayBasicsOnly) 2.dp else 10.dp,
            end = 16.dp)
    ){
        Checkbox(
            onCheckedChange = { checkedState.value = it},
            colors = CheckboxDefaults.colors(
                checkmarkColor = if (disabled)
                    MaterialTheme.recipesColors.foregroundDisabled
                else MaterialTheme.recipesColors.onBackgroundBrand,
                uncheckedColor = if (disabled)
                    MaterialTheme.recipesColors.foregroundDisabled
                else MaterialTheme.recipesColors.foregroundSupport,
            ),
            checked = checkedState.value,
            modifier = Modifier.height(24.dp)

        )
        Column {
            Row (verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = ingredient.text,
                    style = MaterialTheme.typography.body1,
                    color = if (disabled)
                        MaterialTheme.recipesColors.foregroundDisabled
                    else MaterialTheme.recipesColors.foregroundDefault,
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
                            imageVector = Icons.Outlined.Info,
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
