package controllers

import models.Ingredient

class IngredientAPI {
    private var ingredients = ArrayList<Ingredient>()

    fun add(ingr: Ingredient): Boolean {
        return ingredients.add(ingr)
    }

    fun listAllIngredients(): String {
        return if (ingredients.isEmpty()) {
            "None"
        } else {
            var ingrList = ""
            for (i in ingredients.indices) {
                ingrList += "$i: ${ingredients[i]}\n"
            }
            return ingrList
        }
    }

    fun numberOfIngredients(): Int {
        return ingredients.size
    }

    // TODO add other CRUD methods
}
