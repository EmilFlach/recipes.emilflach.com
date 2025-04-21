package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun setRecipe(recipe: Recipe) {
        _recipe.value = recipe
    }

    fun getRecipeBySlug(recipeSlug: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _recipe.value = null
            _isError.value = false
            _errorMessage.value = null
            try {
                _recipe.value = recipeRepository.getRecipeBySlug(recipeSlug)
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun enrichRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _isLoading.value = true
            _recipe.value = recipe
            _isError.value = false
            _errorMessage.value = null
            try {
                _recipe.value = recipeRepository.enrichRecipe(recipe)
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
