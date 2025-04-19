package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RecipeDetailViewModel (
    private val recipeRepository: RecipeRepository
): BaseViewModel() {
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadRecipeBySlug(slug: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _recipe.value = recipeRepository.getRecipeBySlug(slug)
            } catch(e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
