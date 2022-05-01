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

    fun numberOfRecipes(): Int {
        return recipes.size
    }

    // TODO implement other CRUD methods
}
