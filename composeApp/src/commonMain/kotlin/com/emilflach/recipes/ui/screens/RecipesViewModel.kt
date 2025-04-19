package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel {
    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}


class RecipesViewModel(
    private val recipeRepository: RecipeRepository
): BaseViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadRecipes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _recipes.value = recipeRepository.getRecipes().items
            } catch(e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
