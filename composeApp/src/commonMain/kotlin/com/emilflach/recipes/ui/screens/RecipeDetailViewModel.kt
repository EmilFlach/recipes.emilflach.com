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

    private val _isCookingMode = MutableStateFlow(false)
    val isCookingMode: StateFlow<Boolean> = _isCookingMode.asStateFlow()

    private val _currentInstruction = MutableStateFlow(0)
    val currentInstruction = _currentInstruction.asStateFlow()

    private val _currentServings = MutableStateFlow<Double?>(null)
    val currentServings = _currentServings.asStateFlow()

    private val _expandedSections = MutableStateFlow<Set<String>>(emptySet())
    val expandedSections = _expandedSections.asStateFlow()

    private val scaledRecipe: StateFlow<Recipe?> =
        combine(recipe, currentServings) { recipe, servings ->
            when {
                recipe == null -> null
                servings == null -> recipe
                else -> {
                    val scalingFactor = servings / (recipe.recipeServings ?: servings)
                    recipe.copy(
                        recipeIngredient = recipe.recipeIngredient.map { ingredient ->
                            ingredient.copy(
                                quantity = ingredient.quantity?.let { it * scalingFactor }
                            )
                        }
                    )
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val formattedIngredients: StateFlow<List<Ingredient>> =
        scaledRecipe.map { recipe ->
            recipe?.formatIngredients() ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val sectionedInstructions: StateFlow<List<InstructionSection>> =
        scaledRecipe.map { recipe ->
            recipe?.sectionedInstructions() ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val instructions: StateFlow<List<Instruction>> =
        scaledRecipe.map { recipe ->
            recipe?.instructionsWithIngredients() ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getRecipeBySlug(recipeSlug: String) {
        viewModelScope.launch {
            resetRecipe(null)
            try {
                setRecipe(recipeRepository.getRecipeBySlug(recipeSlug))
                initializedRecipeSlug = recipeSlug
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
                initializedRecipeSlug = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun enrichRecipe(recipe: Recipe) {
        viewModelScope.launch {
            resetRecipe(recipe)
            try {
                setRecipe(recipeRepository.enrichRecipe(recipe))
                initializedRecipeSlug = recipe.slug
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    private var initializedRecipeSlug: String? = null

    fun setRecipe(recipe: Recipe, servings: Double? = recipe.recipeServings) {
        _recipe.value = recipe
        _currentServings.value = servings
    }

    private fun resetRecipe(recipe: Recipe?) {
        _recipe.value = recipe
        _isError.value = false
        _isLoading.value = true
        _errorMessage.value = null
        _isCookingMode.value = false
        _currentInstruction.value = 0
        _expandedSections.value = emptySet()
    }

    fun initialize(recipeSlug: String) {
        val currentRecipe = _recipe.value
        if (currentRecipe != null && currentRecipe.slug == recipeSlug) {
            enrichRecipe(currentRecipe)
        } else {
            getRecipeBySlug(recipeSlug)
        }
    }


    fun toggleSectionExpanded(sectionTitle: String) {
        val currentExpanded = _expandedSections.value
        _expandedSections.value = if (currentExpanded.contains(sectionTitle)) {
            currentExpanded - sectionTitle
        } else {
            currentExpanded + sectionTitle
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

    fun toggleCookingMode() {
        _isCookingMode.value = !_isCookingMode.value
        if (_isCookingMode.value) {
            if (recipe.value?.hasInstructionSections == true) {
                // For recipes with sections, find the first instruction in the first section
                val sections = sectionedInstructions.value
                if (sections.isNotEmpty() && sections[0].instructions.isNotEmpty()) {
                    _currentInstruction.value = sections[0].instructions[0].globalIndex
                } else {
                    _currentInstruction.value = 0
                }
            } else {
                // For recipes without sections, just use index 0
                _currentInstruction.value = 0
            }
        }
    }

    fun setCurrentInstruction(index: Int) {
        if (_isCookingMode.value) {
            _currentInstruction.value = index
        }
    }
}
