package com.emilflach.recipes.ui.screens

import com.emilflach.recipes.data.Ingredient
import com.emilflach.recipes.data.Instruction
import com.emilflach.recipes.data.InstructionSection
import com.emilflach.recipes.data.Recipe
import com.emilflach.recipes.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _currentServings = MutableStateFlow<Double?>(null)
    val currentServings = _currentServings.asStateFlow()

    val formattedIngredients: StateFlow<List<Ingredient>> =
        combine(recipe, currentServings) { recipe, servings ->
            when {
                recipe == null -> emptyList()
                servings == null -> recipe.formatIngredients()
                else -> {
                    val scalingFactor = servings / (recipe.recipeServings ?: servings)
                    val scaledRecipe = recipe.copy(
                        recipeIngredient = recipe.recipeIngredient.map { ingredient ->
                            ingredient.copy(
                                quantity = ingredient.quantity?.let { it * scalingFactor }
                            )
                        }
                    )
                    scaledRecipe.formatIngredients()
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    val sectionedInstructions: StateFlow<List<InstructionSection>> =
        recipe.map { it?.sectionedInstructions() ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val instructions: StateFlow<List<Instruction>> =
        recipe.map { it?.instructionsWithIngredients() ?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun setRecipe(recipe: Recipe, servings: Double? = recipe.recipeServings) {
        _recipe.value = recipe
        _currentServings.value = servings
    }

    fun getRecipeBySlug(recipeSlug: String) {
        viewModelScope.launch {
            _recipe.value = null
            _isError.value = false
            _isLoading.value = true
            _errorMessage.value = null
            try {
                setRecipe(recipeRepository.getRecipeBySlug(recipeSlug))
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
            _recipe.value = recipe
            _isError.value = false
            _isLoading.value = true
            _errorMessage.value = null
            try {
                setRecipe(recipeRepository.enrichRecipe(recipe))
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun decreaseServings() {
        setServings(_currentServings.value?.minus(1.0))
    }

    fun increaseServings() {
        setServings(_currentServings.value?.plus(1.0))
    }

    fun setServings(value: Double?) {
        _currentServings.value = value?.coerceAtLeast(1.0)
    }

}
