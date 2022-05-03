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

    fun findIngredient(index: Int): Ingredient? {
        return if (isValidListIndex(index, ingredients)) {
            ingredients[index]
        } else {
            null
        }
    }

    fun numberOfIngredients(): Int {
        return ingredients.size
    }

    fun delete(index: Int): Ingredient? {
        return if (isValidListIndex(index, ingredients)) {
            ingredients.removeAt(index)
        } else {
            null
        }
    }

    fun update(indexToUpdate: Int, ingredient: Ingredient?): Boolean {
        val ingredientToUpdate = findIngredient(indexToUpdate)

        if ((ingredientToUpdate != null) && (ingredient != null)) {
            ingredientToUpdate.ingredientName = ingredient.ingredientName
            ingredientToUpdate.ingredientQuantity = ingredient.ingredientQuantity
            return true
        }
        return false
    }

    private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}
