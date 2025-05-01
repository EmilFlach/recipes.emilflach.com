package com.emilflach.recipes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun recipeIngredient(ingredient: String, note: String? = null) {
    val checkedState = remember { mutableStateOf(false) }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp, end = 16.dp)

    ){
        Checkbox(
            onCheckedChange = { checkedState.value = it},
            checked = checkedState.value
        )
        Column {
            Text(
                text = ingredient,
                style = MaterialTheme.typography.body1,
            )
            if (!note.isNullOrEmpty()) {
                Text(
                    text = note,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

        }


    }

}