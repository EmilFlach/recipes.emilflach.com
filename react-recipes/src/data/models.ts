// Data models for the Recipes application
// Based on the Kotlin data classes in the original application

export interface Recipe {
  id: string;
  slug: string;
  userId?: string;
  householdId?: string;
  groupId?: string;
  name?: string;
  image?: string;
  recipeServings?: number;
  recipeYieldQuantity?: number;
  recipeYield?: string;
  totalTime?: string;
  prepTime?: string;
  cookTime?: string;
  performTime?: string;
  description: string;
  recipeCategory: RecipeCategory[];
  tags: RecipeTag[];
  tools: RecipeTool[];
  rating?: number;
  orgURL?: string;
  dateAdded?: string;
  dateUpdated?: string;
  createdAt?: string;
  updatedAt?: string;
  lastMade?: string;
  recipeIngredient: RecipeIngredient[];
  recipeInstructions: RecipeInstruction[];
  nutrition?: Nutrition;
  settings?: RecipeSettings;
  assets: RecipeAsset[];
  notes: RecipeNote[];
  extras: Record<string, string>;
  comments: RecipeComment[];
}

export interface RecipesListResponse {
  page?: string;
  perPage?: string;
  total?: string;
  totalPages?: string;
  items: Recipe[];
  next?: string;
  previous?: string;
}

export interface RecipeCategory {
  id?: string;
  name?: string;
  slug?: string;
}

export interface RecipeTag {
  id?: string;
  name: string;
  slug: string;
}

export interface RecipeIngredient {
  quantity?: number;
  unit?: Unit;
  food?: Food;
  note: string;
  isFood: boolean;
  disableAmount: boolean;
  display: string;
  title?: string;
  originalText?: string;
  referenceId: string;
  ingredientReferences: IngredientReference[];
}

export interface Ingredient {
  id: string;
  text: string;
  sectionTitle?: string;
  note?: string;
  url?: string;
}

export interface Instruction {
  id: string;
  text: string;
  section?: string;
  ingredients: Ingredient[];
  globalIndex: number;
}

export interface InstructionSection {
  title: string;
  subtitle: string;
  instructions: Instruction[];
}

export interface Unit {
  id: string;
  name: string;
  pluralName?: string;
  description: string;
  extras: Record<string, string>;
  fraction: boolean;
  abbreviation?: string;
  pluralAbbreviation?: string;
  useAbbreviation: boolean;
  aliases: UnitAlias[];
  createdAt?: string;
  updatedAt?: string;
}

export interface UnitAlias {
  name: string;
}

export interface Food {
  id: string;
  name: string;
  pluralName?: string;
  description: string;
  extras: Record<string, string>;
  labelId?: string;
  aliases: FoodAlias[];
  householdsWithIngredientFood: string[];
  label?: LabelSummary;
  createdAt?: string;
  updatedAt?: string;
}

export interface FoodAlias {
  name: string;
}

export interface LabelSummary {
  id: string;
  name: string;
}

export interface RecipeInstruction {
  id: string;
  title: string;
  summary: string;
  text: string;
  ingredientReferences: IngredientReference[];
}

export interface Nutrition {
  calories?: string;
  carbohydrateContent?: string;
  cholesterolContent?: string;
  fatContent?: string;
  fiberContent?: string;
  proteinContent?: string;
  saturatedFatContent?: string;
  sodiumContent?: string;
  sugarContent?: string;
  transFatContent?: string;
  unsaturatedFatContent?: string;
}

export interface RecipeSettings {
  public: boolean;
  showNutrition: boolean;
  showAssets: boolean;
  landscapeView: boolean;
  disableComments: boolean;
  disableAmount: boolean;
  locked: boolean;
}

export interface IngredientReference {
  referenceId?: string;
}

export interface RecipeTool {
  id: string;
  name: string;
  slug: string;
  householdsWithTool: string[];
}

export interface RecipeAsset {
  name: string;
  icon: string;
  fileName?: string;
}

export interface RecipeNote {
  title: string;
  text: string;
}

export interface RecipeComment {
  recipeId: string;
  text: string;
  id: string;
  createdAt: string;
  updatedAt: string;
  userId: string;
  user: UserBase;
}

export interface UserBase {
  id: string;
  username: string;
  fullName?: string;
  email?: string;
  admin: boolean;
  group?: string;
  advanced: boolean;
  favoriteRecipes: string[];
}

// Helper functions for Recipe model
export const getImageUrl = (recipe: Recipe): string => {
  const baseUrl = "https://mealie.emilflach.com/api";
  return `${baseUrl}/media/recipes/${recipe.id}/images/tiny-original.webp?rnd=1&version=${recipe.image}`;
};

export const getCalories = (recipe: Recipe): string => {
  return recipe.tags.find(tag => tag.slug.includes("kcal"))?.name || "";
};

export const getServingsCount = (recipe: Recipe): number => {
  return recipe.recipeServings ? Math.floor(recipe.recipeServings) : 0;
};

export const getYieldCount = (recipe: Recipe): number => {
  return recipe.recipeYieldQuantity ? Math.floor(recipe.recipeYieldQuantity) : 0;
};

export const hasInstructionSections = (recipe: Recipe): boolean => {
  return recipe.recipeInstructions.length > 0 && 
    recipe.recipeInstructions.some(instruction => instruction.title.length > 0);
};