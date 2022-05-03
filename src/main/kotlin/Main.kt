import controllers.IngredientAPI
import controllers.RecipeAPI
import models.Ingredient
import models.Recipe
import persistence.XMLSerializer
import utils.ScannerInput.readNextDietType
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readNextMealType
import utils.ScannerInput.readNextPositiveInt
import java.io.File
import kotlin.system.exitProcess

private val recipeApi = RecipeAPI(XMLSerializer(File("recipes.xml")))

fun main() {
    runMenu()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addRecipe()
            2 -> listAllRecipes()
            3 -> listRecipesByMealType()
            4 -> listRecipesByDiet()
            5 -> listRecipesUnderTime()
            6 -> updateRecipe()
            7 -> deleteRecipe()
            8 -> searchByName()
            20 -> saveRecipes()
            21 -> loadRecipes()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun mainMenu(): Int {
    return readNextInt(
        """ 
         > ------------------------------------
         > |        RECIPE KEEPER APP         |
         > ------------------------------------
         > | MAIN MENU                        |
         > |   1) Add a recipe                |
         > |   2) List all recipes            |
         > |   3) List all recipes for meal   |
         > |   4) List all recipes with diet  |
         > |   5) List all recipes under time |
         > |   6) Update a recipe             |
         > |   7) Delete a recipe             |
         > |   8) Find a recipe by name       |
         > |   20) Save recipes               |
         > |   21) Load recipes               |
         > ------------------------------------
         > |   0) Exit                        |
         > ------------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun addRecipe() {
    val ingredients = IngredientAPI()
    val recipeName = readNextLine("Enter the name of the recipe: ")
    val prepTime = readNextPositiveInt("Enter the prep time (in mins): ")
    val cookTime = readNextPositiveInt("Enter the cook time (in mins): ")
    val mealType = readNextMealType("Enter the recipe meal type: ")
    val diet = readNextDietType("Enter the diet (vegan, vegetarian or meat): ")
    val instruction = readNextLine("Enter the instructions: ")

    val numIngredients = readNextInt("Enter the number of ingredients in the recipe: ")
    repeat(numIngredients) {
        val ingrName = readNextLine("Enter the name of the ingredient: ")
        val ingrQuantity = readNextLine("Enter the quantity: ")
        val isAdded = ingredients.add(Ingredient(ingrName, ingrQuantity))
        if (!isAdded) {
            println("Adding Ingredient failed")
        }
    }

    val isAdded = recipeApi.add(
        Recipe(
            recipeName, instruction, prepTime, cookTime, mealType, diet, ingredients
        )
    )
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun deleteRecipe() {
    listAllRecipes()
    if (recipeApi.numberOfRecipes() > 0) {
        val indexToDelete = readNextInt("Enter the index of the recipe to delete: ")
        if (recipeApi.isValidIndex(indexToDelete)) {
            val recipeToDelete = recipeApi.delete(indexToDelete)
            if (recipeToDelete != null) {
                println("Delete successful! Deleted recipe: ${recipeToDelete.recipeName}")
            } else {
                println("Delete failed")
            }
        } else {
            println("There are no recipes for this index number $indexToDelete")
        }
    }
}

fun updateRecipe() {
    listAllRecipes()
    if (recipeApi.numberOfRecipes() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the recipe to update: ")
        if (recipeApi.isValidIndex(indexToUpdate)) {
            val ingredients = IngredientAPI()
            val recipeName = readNextLine("Enter the name of the recipe: ")
            val prepTime = readNextPositiveInt("Enter the prep time (in mins): ")
            val cookTime = readNextPositiveInt("Enter the cook time (in mins): ")
            val mealType = readNextMealType("Enter the recipe meal type: ")
            val diet = readNextDietType("Enter the diet (vegan, vegetarian or meat): ")
            val instruction = readNextLine("Enter the instructions: ")

            val numIngredients = readNextInt("Enter the number of ingredients in the recipe: ")
            repeat(numIngredients) {
                val ingrName = readNextLine("Enter the name of the ingredient: ")
                val ingrQuantity = readNextLine("Enter the quantity: ")
                val isAdded = ingredients.add(Ingredient(ingrName, ingrQuantity))
                if (!isAdded) {
                    println("Adding Ingredient failed")
                }
            }

            val updateSuccess = recipeApi.update(
                indexToUpdate, Recipe(recipeName, instruction, prepTime, cookTime, mealType, diet, ingredients)
            )
            if (updateSuccess) {
                println("Update successful")
            } else {
                println("Update failed")
            }
        } else {
            println("There are no recipes for this index number $indexToUpdate")
        }
    }
}

fun listAllRecipes() {
    println(recipeApi.listAllRecipes())
}

fun listRecipesUnderTime() {
    val time = readNextInt("Enter total time in mins: ")
    println("Following recipes can be made in under $time mins")
    println(recipeApi.listRecipesUnderTime(time))
}

fun listRecipesByDiet() {
    val diet = readNextLine("Enter the diet type(s): ")
    println("Following recipes were found for diet type $diet")
    println(recipeApi.listRecipesByDiet(diet))
}

fun listRecipesByMealType() {
    val meal = readNextLine("Enter the meal type(s): ")
    println("Following recipes were found for meal type $meal")
    println(recipeApi.listRecipesByMealType(meal))
}

fun searchByName() {
    val recipeName = readNextLine("Enter the name to search: ")
    println("Following recipes were found for with name containg $recipeName")
    println(recipeApi.findRecipeByName(recipeName))
}

fun loadRecipes() {
    try {
        recipeApi.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
    println("Loaded ${recipeApi.numberOfRecipes()} from file")
}

fun saveRecipes() {
    try {
        recipeApi.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}
