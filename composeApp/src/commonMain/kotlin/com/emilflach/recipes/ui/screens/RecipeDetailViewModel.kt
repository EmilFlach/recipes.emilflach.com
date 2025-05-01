package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val formattedIngredients: StateFlow<List<Pair<String, String?>>> =
        recipe.map { it?.formatIngredients() ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun setRecipe(recipe: Recipe) {
        _recipe.value = recipe
    }

    fun getRecipeBySlug(recipeSlug: String) {
        viewModelScope.launch {
            _recipe.value = null
            _isError.value = false
            _errorMessage.value = null
            try {
                _recipe.value = recipeRepository.getRecipeBySlug(recipeSlug)
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            }
        }
    }

    fun enrichRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _recipe.value = recipe
            _isError.value = false
            _errorMessage.value = null
            try {
                _recipe.value = recipeRepository.enrichRecipe(recipe)
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            }
        }
    }
}
