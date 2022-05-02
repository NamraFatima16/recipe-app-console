package controller

import controllers.IngredientAPI
import controllers.RecipeAPI
import models.Ingredient
import models.Recipe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RecipeAPITest {

    private var numPopulatedRecipes = 5
    private var waffles: Recipe? = null
    private var pancakes: Recipe? = null
    private var salad: Recipe? = null
    private var carbonara: Recipe? = null
    private var chickenCasserole: Recipe? = null

    private var populatedRecipes: RecipeAPI? = RecipeAPI(XMLSerializer(File("notes.xml")))
    private var emptyRecipes: RecipeAPI? = RecipeAPI(XMLSerializer(File("notes.xml")))

    @BeforeEach
    fun setup() {
        // Values for the various fields aren't accurate .. just made up for testing purposes

        //
        val ingredients = IngredientAPI()
        val magicIngredient1 = Ingredient("essence of peace", "2 tbsp")
        val magicIngredient2 = Ingredient("sunshine and flowers", "5 g")
        ingredients.add(magicIngredient1); ingredients.add(magicIngredient2)

        waffles = Recipe(
            "Belgian Waffles",
            "Make waffles!", 5, 15,
            "breakfast", "vegetarian", ingredients,
        )
        pancakes = Recipe(
            "American-style pancakes",
            "Make pancakes!", 8, 10,
            "breakfast", "vegetarian", ingredients,
        )

        salad = Recipe(
            "Chickpea salad",
            "Make salad!", 20, 0,
            "lunch", "vegan", ingredients,
        )

        carbonara = Recipe(
            "Spaghetti carbonara",
            "Make pasta!", 15, 25,
            "dinner", "meat", ingredients,
        )

        chickenCasserole = Recipe(
            "Chicken casserole",
            "Make casserole!", 20, 150,
            "dinner", "meat", ingredients,
        )

        populatedRecipes!!.add(waffles!!); populatedRecipes!!.add(pancakes!!); populatedRecipes!!.add(salad!!)
        populatedRecipes!!.add(carbonara!!); populatedRecipes!!.add(chickenCasserole!!)
    }

    @AfterEach
    fun teardown() {
        waffles = null
        pancakes = null
        salad = null
        carbonara = null
        chickenCasserole = null
        populatedRecipes = null
    }

    @Nested
    inner class AddRecipes {
        @Test
        fun `adding an Recipe to a populated RecipeAPI adds to the internal ArrayList`() {
            val newIngredient = Ingredient("unsalted butter", "100g")
            val newIngredientAPI = IngredientAPI()
            newIngredientAPI.add(newIngredient)

            val newRecipe = Recipe(
                "Meatballs",
                "Make them", 30, 35,
                "dinner", "meat", newIngredientAPI
            )

            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())
            assertTrue(populatedRecipes!!.add(newRecipe))
            assertEquals(numPopulatedRecipes + 1, populatedRecipes!!.numberOfRecipes())
            assertEquals(
                newRecipe,
                populatedRecipes!!.findRecipe(populatedRecipes!!.numberOfRecipes() - 1)
            )
        }

        @Test
        fun `adding an Ingredient to an empty IngredientAPI adds to the internal ArrayList`() {
            val newIngredient = Ingredient("unsalted butter", "100g")
            val newIngredientAPI = IngredientAPI()
            newIngredientAPI.add(newIngredient)

            val newRecipe = Recipe(
                "Meatballs",
                "Make them", 30, 35,
                "dinner", "meat", newIngredientAPI
            )

            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.add(newRecipe))
            assertEquals(1, emptyRecipes!!.numberOfRecipes())
            assertEquals(
                newRecipe,
                emptyRecipes!!.findRecipe(emptyRecipes!!.numberOfRecipes() - 1)
            )
        }
    }

    @Nested
    inner class ListRecipes {

        @Test
        fun `listAllRecipes returns None when internal ArrayList is empty`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            assertTrue(emptyRecipes!!.listAllRecipes().lowercase().contains("none"))
        }

        @Test
        fun `listAllRecipes returns recipes when internal ArrayList is populated`() {
            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())
            val recipeListStr = populatedRecipes!!.listAllRecipes()
            assertTrue(recipeListStr.contains(waffles!!.recipeName))
            assertTrue(recipeListStr.contains(pancakes!!.recipeName))
            assertTrue(recipeListStr.contains(salad!!.recipeName))
            assertTrue(recipeListStr.contains(carbonara!!.recipeName))
            assertTrue(recipeListStr.contains(chickenCasserole!!.recipeName))
        }

        @Test
        fun `listRecipesByMealType returns 'No recipes' when internal ArrayList does not contain any recipes of that mealType`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            var recipeListStr = emptyRecipes!!.listRecipesByMealType("lunch")
            assertTrue(recipeListStr.contains("no recipes", true))
            recipeListStr = populatedRecipes!!.listRecipesByMealType("apocalypse")
            assertTrue(recipeListStr.contains("no recipes", true))
        }

        @Test
        fun `listRecipesByMealType returns recipes when internal ArrayList contains recipes matching mealType`() {
            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())

            var recipeListStr = populatedRecipes!!.listRecipesByMealType("dinner")
            assertTrue(recipeListStr.contains(carbonara!!.recipeName))
            assertTrue(recipeListStr.contains(chickenCasserole!!.recipeName))
            assertFalse(recipeListStr.contains(waffles!!.recipeName))

            // get multiple types
            recipeListStr = populatedRecipes!!.listRecipesByMealType("breakfast, lunch")
            assertTrue(recipeListStr.contains(waffles!!.recipeName))
            assertTrue(recipeListStr.contains(pancakes!!.recipeName))
            assertTrue(recipeListStr.contains(salad!!.recipeName))
            assertFalse(recipeListStr.contains(carbonara!!.recipeName))
        }

        @Test
        fun `listRecipesByDiet returns 'No recipes' when internal ArrayList does not contain any recipes of that diet`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            var recipeListStr = emptyRecipes!!.listRecipesByDiet("vegan")
            assertTrue(recipeListStr.contains("no recipes", true))
            recipeListStr = populatedRecipes!!.listRecipesByDiet("blahblah")
            assertTrue(recipeListStr.contains("no recipes", true))
        }

        @Test
        fun `listRecipesByDiet returns recipes when internal ArrayList contains recipes matching diet`() {
            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())
            var recipeListStr = populatedRecipes!!.listRecipesByDiet("meat")
            assertTrue(recipeListStr.contains(carbonara!!.recipeName))
            assertTrue(recipeListStr.contains(chickenCasserole!!.recipeName))
            assertFalse(recipeListStr.contains(salad!!.recipeName))

            // get multiple
            recipeListStr = populatedRecipes!!.listRecipesByDiet("vegan + vegetarian")
            assertTrue(recipeListStr.contains(salad!!.recipeName))
            assertTrue(recipeListStr.contains(waffles!!.recipeName))
            assertTrue(recipeListStr.contains(pancakes!!.recipeName))
            assertFalse(recipeListStr.contains(chickenCasserole!!.recipeName))
        }

        @Test
        fun `listRecipesUnderTime returns 'No recipes' when internal ArrayList contains no recipes under given time`() {
            assertEquals(0, emptyRecipes!!.numberOfRecipes())
            var recipeListStr = emptyRecipes!!.listRecipesUnderTime(20)
            assertTrue(recipeListStr.contains("no recipes", true))
            recipeListStr = populatedRecipes!!.listRecipesUnderTime(-1)
            assertTrue(recipeListStr.contains("no recipes", true))
        }

        @Test
        fun `listRecipesUnderTime returns recipes under given time`() {
            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())
            val recipeListStr = populatedRecipes!!.listRecipesUnderTime(20)
            assertTrue(recipeListStr.contains(salad!!.recipeName))
            assertTrue(recipeListStr.contains(waffles!!.recipeName))
            assertTrue(recipeListStr.contains(pancakes!!.recipeName))
            assertFalse(recipeListStr.contains(carbonara!!.recipeName))
        }
    }

    @Nested
    inner class DeleteRecipes {

        @Test
        fun `delete returns null when Recipe does not exist`() {
            assertNull(emptyRecipes!!.delete(0))
            assertNull(populatedRecipes!!.delete(-1))
            assertNull(populatedRecipes!!.delete(numPopulatedRecipes))
        }

        @Test
        fun `deleting an recipe that exists returns that recipe and removes it from the internal ArrayList`() {
            assertEquals(numPopulatedRecipes, populatedRecipes!!.numberOfRecipes())
            assertEquals(waffles, populatedRecipes!!.delete(0))
            assertEquals(numPopulatedRecipes - 1, populatedRecipes!!.numberOfRecipes())
        }
    }

    @Nested
    inner class UpdateRecipes {

        @Test
        fun `updating an recipe that does not exist returns false`() {
            val newRecipe = Recipe(
                "Meatballs",
                "Make them", 30, 35,
                "dinner", "meat", IngredientAPI()
            )
            assertFalse(emptyRecipes!!.update(0, newRecipe))
            assertFalse(populatedRecipes!!.update(-1, newRecipe))
            assertFalse(populatedRecipes!!.update(numPopulatedRecipes, newRecipe))
        }

        @Test
        fun `updating an ingredient with an ingredient that does not exist returns false`() {
            val newRecipe = null
            assertFalse(populatedRecipes!!.update(0, newRecipe))
        }

        @Test
        fun `updating an ingredient that does exist returns true and updates`() {
            val newName = waffles!!.copy(recipeName = "Potato Waffle")
            val newInstructions = waffles!!.copy(instructions = "Add disarmed potatoes to the waffles")
            val newPrepTime = waffles!!.copy(prepTime = 32)
            val newCookTime = waffles!!.copy(cookTime = 7)
            val newMealType = waffles!!.copy(mealType = "lunch")
            val newDiet = waffles!!.copy(diet = "vegan")
            val newIngredientAPI = IngredientAPI()
            newIngredientAPI.add(Ingredient("Potato", "1 kg"))
            val newIngredients = waffles!!.copy(ingredients = newIngredientAPI)

            // Check recipe matches before update
            assertEquals(waffles, populatedRecipes!!.findRecipe(0))
            assertEquals(waffles!!.recipeName, populatedRecipes!!.findRecipe(0)!!.recipeName)
            assertEquals(waffles!!.instructions, populatedRecipes!!.findRecipe(0)!!.instructions)
            assertEquals(waffles!!.prepTime, populatedRecipes!!.findRecipe(0)!!.prepTime)
            assertEquals(waffles!!.cookTime, populatedRecipes!!.findRecipe(0)!!.cookTime)
            assertEquals(waffles!!.mealType, populatedRecipes!!.findRecipe(0)!!.mealType)
            assertEquals(waffles!!.diet, populatedRecipes!!.findRecipe(0)!!.diet)
            assertEquals(waffles!!.ingredients, populatedRecipes!!.findRecipe(0)!!.ingredients)

            // Check ingredient matches after updates
            assertTrue(populatedRecipes!!.update(0, newName))
            assertEquals(newName.recipeName, populatedRecipes!!.findRecipe(0)!!.recipeName)
            assertTrue(populatedRecipes!!.update(0, newInstructions))
            assertEquals(newInstructions.instructions, populatedRecipes!!.findRecipe(0)!!.instructions)
            assertTrue(populatedRecipes!!.update(0, newPrepTime))
            assertEquals(newPrepTime.prepTime, populatedRecipes!!.findRecipe(0)!!.prepTime)
            assertTrue(populatedRecipes!!.update(0, newCookTime))
            assertEquals(newCookTime.cookTime, populatedRecipes!!.findRecipe(0)!!.cookTime)
            assertTrue(populatedRecipes!!.update(0, newMealType))
            assertEquals(newMealType.mealType, populatedRecipes!!.findRecipe(0)!!.mealType)
            assertTrue(populatedRecipes!!.update(0, newDiet))
            assertEquals(newDiet.diet, populatedRecipes!!.findRecipe(0)!!.diet)
            assertTrue(populatedRecipes!!.update(0, newIngredients))
            assertEquals(newIngredients.ingredients, populatedRecipes!!.findRecipe(0)!!.ingredients)
        }
    }

    @Nested
    inner class SearchMethods {
        @Test
        fun `findRecipe returns null when index does not exist`() {
            assertNull(emptyRecipes!!.findRecipe(0))
            assertNull(populatedRecipes!!.findRecipe(-1))
            assertNull(populatedRecipes!!.findRecipe(numPopulatedRecipes))
        }

        @Test
        fun `findIngredient returns ingredient when it does exist`() {
            assertEquals(waffles, populatedRecipes!!.findRecipe(0))
        }

        @Test
        fun `findRecipeByName returns empty string when no recipe with matching name exists`() {
            assertTrue(populatedRecipes!!.findRecipeByName("pizza").isEmpty())
        }

        @Test
        fun `findRecipeByName returns recipes when matching recipe exist`() {
            assertTrue(populatedRecipes!!.findRecipeByName("chicken").contains(chickenCasserole!!.recipeName))
            assertTrue(populatedRecipes!!.findRecipeByName("chicken").contains(chickenCasserole!!.instructions))
        }
    }
}
