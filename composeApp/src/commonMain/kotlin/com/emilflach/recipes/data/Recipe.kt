package com.emilflach.recipes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val slug: String,
    val userId: String? = null,
    val householdId: String? = null,
    val groupId: String? = null,
    val name: String? = null,
    val image: String? = null,
    val recipeServings: Double? = null,
    val recipeYieldQuantity: Double? = null,
    val recipeYield: String? = null,
    val totalTime: String? = null,
    val prepTime: String? = null,
    val cookTime: String? = null,
    val performTime: String? = null,
    val description: String = "",
    val recipeCategory: List<RecipeCategory> = emptyList(),
    val tags: List<RecipeTag> = emptyList(),
    val tools: List<RecipeTool> = emptyList(),
    val rating: Double? = null,
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
    val assets: List<RecipeAsset> = emptyList(),
    val notes: List<RecipeNote> = emptyList(),
    val extras: Map<String, String> = emptyMap(),
    val comments: List<RecipeComment> = emptyList()
) {
    val imageUrl: String
        get() = "http://192.168.1.111:9925/api/media/recipes/$id/images/min-original.webp?rnd=1&version=$image"

    val calories: String?
        get() = tags.find { it.slug.contains("kcal") }?.name

    val servingsCount: Int
        get() = recipeServings?.toInt() ?: 0

    val yieldCount: Int
        get() = recipeYieldQuantity?.toInt() ?: 0
}

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
data class RecipeCategory(
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null
)

@Serializable
data class RecipeTag(
    val id: String? = null,
    val name: String,
    val slug: String
)

@Serializable
data class RecipeIngredient(
    val quantity: Double? = null,
    val unit: Unit? = null,
    val food: Food? = null,
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
    val extras: Map<String, String> = emptyMap(),
    val fraction: Boolean = false,
    val abbreviation: String? = null,
    val pluralAbbreviation: String? = null,
    val useAbbreviation: Boolean = false,
    val aliases: List<UnitAlias> = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class UnitAlias(
    val name: String
)

@Serializable
data class Food(
    val id: String,
    val name: String,
    val pluralName: String? = null,
    val description: String = "",
    val extras: Map<String, String> = emptyMap(),
    val labelId: String? = null,
    val aliases: List<FoodAlias> = emptyList(),
    val householdsWithIngredientFood: List<String> = emptyList(),
    val label: LabelSummary? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class FoodAlias(
    val name: String
)

@Serializable
data class LabelSummary(
    val id: String,
    val name: String
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

@Serializable
data class RecipeTool(
    val id: String,
    val name: String,
    val slug: String,
    val householdsWithTool: List<String> = emptyList()
)

@Serializable
data class RecipeAsset(
    val name: String,
    val icon: String,
    val fileName: String? = null
)

@Serializable
data class RecipeNote(
    val title: String,
    val text: String
)

@Serializable
data class RecipeComment(
    val recipeId: String,
    val text: String,
    val id: String,
    val createdAt: String,
    val updatedAt: String,
    val userId: String,
    val user: UserBase
)

@Serializable
data class UserBase(
    val id: String,
    val username: String,
    val fullName: String? = null,
    val email: String? = null,
    val admin: Boolean = false,
    val group: String? = null,
    val advanced: Boolean = false,
    val favoriteRecipes: List<String> = emptyList()
)