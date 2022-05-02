package controllers

import models.Recipe

class RecipeApi {
    private var recipes = ArrayList<Recipe>()

    fun add(recipe: Recipe): Boolean {
        return recipes.add(recipe)
    }

    fun listAllRecipes(): String {
        return if (recipes.isEmpty()) {
            "None"
        } else {
            var recipeList = ""
            for (i in recipes.indices) {
                recipeList += "$i: ${recipes[i]}\n"
            }
            return recipeList
        }
    }

    fun listRecipesByMealType(mealType: String): String {
        val count = recipes.count { recipe: Recipe -> recipe.mealType == mealType }
        return if (count == 0) {
            "No recipes for meal type '$mealType'"
        } else {
            val matches = recipes.filter { recipe: Recipe -> recipe.mealType == mealType }
            matches.joinToString(separator = "\n") { recipe: Recipe ->
                "${recipes.indexOf(recipe)}: $recipe"
            }
        }
    }

    fun listRecipesByDiet(diet: String): String {
        val count = recipes.count { recipe: Recipe -> recipe.diet == diet }
        return if (count == 0) {
            "No recipes for diet type '$diet'"
        } else {
            val matches = recipes.filter { recipe: Recipe -> recipe.diet == diet }
            matches.joinToString(separator = "\n") { recipe: Recipe ->
                "${recipes.indexOf(recipe)}: $recipe"
            }
        }
    }

    fun listRecipesUnderTime(time: Int): String {
        val count = recipes.count { recipe: Recipe -> recipe.prepTime + recipe.cookTime <= time }
        return if (count == 0) {
            "No recipes that can be made under $time"
        } else {
            val matches = recipes.filter { recipe: Recipe -> recipe.prepTime + recipe.cookTime <= time }
            matches.joinToString(separator = "\n") { recipe: Recipe ->
                "${recipes.indexOf(recipe)}: $recipe"
            }
        }
    }

    fun findRecipe(index: Int): Recipe? {
        return if (isValidListIndex(index, recipes)) {
            recipes[index]
        } else {
            null
        }
    }

    fun findRecipeByName(name: String): String {
        val matches = recipes.filter { recipe: Recipe -> recipe.recipeName.contains(name, true) }
        return matches.joinToString(separator = "\n") { recipe: Recipe ->
            "${recipes.indexOf(recipe)}: $recipe"
        }
    }

    fun numberOfRecipes(): Int {
        return recipes.size
    }

    fun delete(index: Int): Recipe? {
        return if (isValidListIndex(index, recipes)) {
            recipes.removeAt(index)
        } else {
            null
        }
    }

    fun update(indexToUpdate: Int, recipe: Recipe?): Boolean {
        val recipeToUpdate = findRecipe(indexToUpdate)

        if ((recipeToUpdate != null) && (recipe != null)) {
            recipeToUpdate.recipeName = recipe.recipeName
            recipeToUpdate.instructions = recipe.instructions
            recipeToUpdate.diet = recipe.diet
            recipeToUpdate.ingredients = recipe.ingredients
            recipeToUpdate.prepTime = recipe.prepTime
            recipeToUpdate.cookTime = recipe.cookTime
            recipeToUpdate.mealType = recipe.mealType
            return true
        }
        return false
    }

    private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}
