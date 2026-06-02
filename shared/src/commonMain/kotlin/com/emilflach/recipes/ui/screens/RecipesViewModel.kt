package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _weeknightRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val weeknightRecipes: StateFlow<List<Recipe>> = _weeknightRecipes.asStateFlow()

    private val _specialOccasionRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val specialOccasionRecipes: StateFlow<List<Recipe>> = _specialOccasionRecipes.asStateFlow()

    private val _bakingRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val bakingRecipes: StateFlow<List<Recipe>> = _bakingRecipes.asStateFlow()

    fun loadRecipes(refresh: Boolean = false) {
        viewModelScope.launch {
            if (!refresh) {
                _isLoading.value = true
            } else {
                _isRefreshing.value = true
            }
            try {
                _recipes.value = recipeRepository.getRecipes(refresh).items
                _weeknightRecipes.value = recipesByCategory(_recipes.value, "Weeknight")
                _specialOccasionRecipes.value = recipesByCategory(_recipes.value, "Special Occasion")
                _bakingRecipes.value = recipesByCategory(_recipes.value, "Baking")
            } catch(e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                if (!refresh) {
                    _isLoading.value = false
                } else {
                    _isRefreshing.value = false
                }
            }
        }
    }

    private fun recipesByCategory(recipes: List<Recipe>, keyword: String): List<Recipe> =
        recipes.filter { recipe ->
            recipe.recipeCategory.any { category ->
                category.name?.equals(keyword) ?: false
            }
        }
}
