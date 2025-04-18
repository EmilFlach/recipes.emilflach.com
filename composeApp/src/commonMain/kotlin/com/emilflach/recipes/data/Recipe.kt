package com.emilflach.recipes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipesListResponse(
    val page: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    val total: String? = null,
    @SerialName("total_pages") val totalPages: String? = null,
    val items: List<Recipe>,
    val next: String? = null,
    val previous: String? = null
)

@Serializable
data class Recipe(
    val id: String? = null,
    val userId: String? = null,
    val householdId: String? = null,
    val groupId: String? = null,
    val name: String? = null,
    val slug: String? = null,
    var image: String? = null,
    val recipeServings: String? = null,
    val recipeYieldQuantity: String? = null,
    val recipeYield: String? = null,
    val totalTime: String? = null,
    val prepTime: String? = null,
    val cookTime: String? = null,
    val performTime: String? = null,
    val description: String = "",
    val recipeCategory: List<RecipeCategory> = emptyList(),
    val tags: List<RecipeTag> = emptyList(),
    val tools: List<String> = emptyList(), //TODO Convert to correct type
    val rating: String? = null,
    val orgURL: String? = null,
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val lastMade: String? = null,
    val recipeIngredient: List<RecipeIngredient> = emptyList(),
    val recipeInstructions: List<RecipeInstruction> = emptyList(),
    val nutrition: Nutrition? = null,
    val settings: RecipeSettings? = null,
    val assets: List<String> = emptyList(), //TODO Convert to correct type
    val notes: List<String> = emptyList(), //TODO Convert to correct type
    val extras: Map<String, String> = emptyMap(), //TODO Convert to correct type
    val comments: List<String> = emptyList() //TODO Convert to correct type
) {
    init {
        image = "http://192.168.1.111:9925/api/media/recipes/$id/images/min-original.webp?rnd=1&version=$image"
    }
}

@Serializable
data class RecipeCategory (
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class RecipeTag(
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class RecipeIngredient(
    val quantity: Double? = null,
    val unit: Unit? = null,
    val food: Food,
    val note: String = "",
    val isFood: Boolean = true,
    val disableAmount: Boolean = false,
    val display: String,
    val title: String? = null,
    val originalText: String? = null,
    val referenceId: String? = null,
    val ingredientReferences: List<IngredientReference> = emptyList()
)

@Serializable
data class Unit(
    val id: String,
    val name: String,
    val pluralName: String? = null,
    val description: String = "",
    val extras: Map<String, String> = emptyMap(), //TODO Convert to correct type
    val fraction: Boolean = false,
    val abbreviation: String? = null,
    val pluralAbbreviation: String? = null,
    val useAbbreviation: Boolean = false,
    val aliases: List<String> = emptyList(), //TODO Convert to correct type
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class Food(
    val id: String,
    val name: String,
    val pluralName: String? = null,
    val description: String = "",
    val extras: Map<String, String> = emptyMap(), //TODO Convert to correct type
    val labelId: String? = null,
    val aliases: List<String> = emptyList(),
    val householdsWithIngredientFood: List<String> = emptyList(), //TODO Convert to correct type
    val label: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class RecipeInstruction(
    val id: String,
    val title: String = "",
    val summary: String = "",
    val text: String,
    val ingredientReferences: List<IngredientReference> = emptyList()
)

@Serializable
data class Nutrition(
    val calories: String? = null,
    val carbohydrateContent: String? = null,
    val cholesterolContent: String? = null,
    val fatContent: String? = null,
    val fiberContent: String? = null,
    val proteinContent: String? = null,
    val saturatedFatContent: String? = null,
    val sodiumContent: String? = null,
    val sugarContent: String? = null,
    val transFatContent: String? = null,
    val unsaturatedFatContent: String? = null
)

@Serializable
data class RecipeSettings(
    val public: Boolean = false,
    val showNutrition: Boolean = false,
    val showAssets: Boolean = false,
    val landscapeView: Boolean = false,
    val disableComments: Boolean = false,
    val disableAmount: Boolean = false,
    val locked: Boolean = false
)

@Serializable
data class IngredientReference(
    val referenceId: String? = null
)