package utils

object Utilities {
    @JvmStatic
    val mealTypes = setOf("breakfast", "dinner", "lunch", "other")
    @JvmStatic
    val diets = setOf("vegan", "vegetarian", "meat")
    @JvmStatic
    fun isValidMealType(mealType: String): Boolean = mealTypes.contains(mealType.lowercase())
    @JvmStatic
    fun isValidDiet(diet: String): Boolean = diets.contains(diet.lowercase())
    @JvmStatic
    fun isPositive(num: Int): Boolean = num >= 0
}
