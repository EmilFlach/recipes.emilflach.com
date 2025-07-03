import axios from 'axios';
import type { Recipe, RecipesListResponse } from './models';

// API configuration
const BASE_URL = 'https://mealie.emilflach.com/api';

// Create axios instance
const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Recipe service
export const RecipeService = {
  // Cache for recipes list
  recipesCache: null as RecipesListResponse | null,

  // Cache for individual recipes
  recipeCache: new Map<string, Recipe>(),

  // Get all recipes
  async getRecipes(forceRefresh: boolean = false): Promise<RecipesListResponse> {
    // Return cached data if available and not forcing refresh
    if (!forceRefresh && this.recipesCache) {
      return this.recipesCache;
    }

    try {
      const response = await api.get<RecipesListResponse>(
        '/explore/groups/home/recipes?requireAllCategories=true&orderBy=updatedAt'
      );

      // Cache the response
      this.recipesCache = response.data;

      return response.data;
    } catch (error) {
      console.error('Error fetching recipes:', error);
      throw error;
    }
  },

  // Get recipe by slug
  async getRecipeBySlug(slug: string, forceRefresh: boolean = false): Promise<Recipe> {
    // Return cached data if available and not forcing refresh
    if (!forceRefresh && this.recipeCache.has(slug)) {
      return this.recipeCache.get(slug)!;
    }

    try {
      const response = await api.get<Recipe>(`/explore/groups/home/recipes/${slug}`);

      // Cache the response
      this.recipeCache.set(slug, response.data);

      return response.data;
    } catch (error) {
      console.error(`Error fetching recipe with slug ${slug}:`, error);
      throw error;
    }
  },

  // Enrich recipe with additional data
  async enrichRecipe(recipe: Recipe, forceRefresh: boolean = false): Promise<Recipe> {
    // Return cached data if available and not forcing refresh
    if (!forceRefresh && this.recipeCache.has(recipe.slug)) {
      return this.recipeCache.get(recipe.slug)!;
    }

    try {
      const response = await api.get<Recipe>(`/explore/groups/home/recipes/${recipe.slug}`);

      // Cache the response
      this.recipeCache.set(recipe.slug, response.data);

      return response.data;
    } catch (error) {
      console.error(`Error enriching recipe with slug ${recipe.slug}:`, error);
      throw error;
    }
  }
};

// React Query keys
export const queryKeys = {
  recipes: 'recipes',
  recipe: (slug: string) => ['recipe', slug],
};
