package models

import controllers.IngredientAPI

data class Recipe(
    var recipeName: String,
    var instructions: String,
    var prepTime: Int,
    var cookTime: Int,
    var mealType: String,
    var diet: String,
    var ingredients: IngredientAPI
)
