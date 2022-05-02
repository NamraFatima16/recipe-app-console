import controllers.RecipeAPI
import utils.ScannerInput.readNextInt
import kotlin.system.exitProcess

private val recipeApi = RecipeAPI()

fun main() {
    runMenu()
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addRecipe()
            2 -> listAllRecipes()
            3 -> updateRecipe()
            4 -> deleteRecipe()
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
         > |   3) Update a recipe             |
         > |   4) Delete a recipe             |
         > |   20) Save recipes               |
         > |   21) Load recipes               |
         > ------------------------------------
         > |   0) Exit                        |
         > ------------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun loadRecipes() {
    TODO("Not yet implemented")
}

fun saveRecipes() {
    TODO("Not yet implemented")
}

fun deleteRecipe() {
    TODO("Not yet implemented")
}

fun updateRecipe() {
    TODO("Not yet implemented")
}

fun listAllRecipes() {
    TODO("Not yet implemented")
}

fun addRecipe() {
    TODO("Not yet implemented")
}

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}
