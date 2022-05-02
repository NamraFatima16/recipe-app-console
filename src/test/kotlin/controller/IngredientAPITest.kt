package controller

import controllers.IngredientAPI
import models.Ingredient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IngredientAPITest {

    private var numCakeIngredients = 8
    private var oil: Ingredient? = null
    private var flour: Ingredient? = null
    private var sugar: Ingredient? = null
    private var cocoa: Ingredient? = null
    private var bicarbSoda: Ingredient? = null
    private var milk: Ingredient? = null
    private var eggs: Ingredient? = null
    private var syrup: Ingredient? = null

    private var cakeIngredients: IngredientAPI? = IngredientAPI()
    private var emptyIngredients: IngredientAPI? = IngredientAPI()

    @BeforeEach
    fun setup() {
        oil = Ingredient("sunflower oil", "150 ml")
        flour = Ingredient("self-raising flour", "175 g")
        cocoa = Ingredient("cocoa powder", "2 tbsp")
        bicarbSoda = Ingredient("bicarbonate of soda", "1 tsp")
        sugar = Ingredient("caster sugar", "150 g")
        syrup = Ingredient("golden syrup", "2 tbsp")
        eggs = Ingredient("large eggs, lightly beaten", "2")
        milk = Ingredient("semi-skimmed milk", "150 ml")

        // Add all the Ingredients to an IngredientAPI instance
        cakeIngredients!!.add(oil!!); cakeIngredients!!.add(flour!!); cakeIngredients!!.add(cocoa!!)
        cakeIngredients!!.add(bicarbSoda!!); cakeIngredients!!.add(sugar!!); cakeIngredients!!.add(syrup!!)
        cakeIngredients!!.add(milk!!); cakeIngredients!!.add(eggs!!)
    }

    @AfterEach
    fun teardown() {
        oil = null
        flour = null
        cocoa = null
        bicarbSoda = null
        sugar = null
        syrup = null
        eggs = null
        milk = null
        cakeIngredients = null
    }

    @Nested
    inner class AddIngredients {
        @Test
        fun `adding an Ingredient to a populated IngredientAPI adds to the internal ArrayList`() {
            val newIngredient = Ingredient("unsalted butter", "100g")

            assertEquals(numCakeIngredients, cakeIngredients!!.numberOfIngredients())
            assertTrue(cakeIngredients!!.add(newIngredient))
            assertEquals(numCakeIngredients + 1, cakeIngredients!!.numberOfIngredients())
            assertEquals(
                newIngredient,
                cakeIngredients!!.findIngredient(cakeIngredients!!.numberOfIngredients() - 1)
            )
        }

        @Test
        fun `adding an Ingredient to an empty IngredientAPI adds to the internal ArrayList`() {
            val newIngredient = Ingredient("unsalted butter", "100g")

            assertEquals(0, emptyIngredients!!.numberOfIngredients())
            assertTrue(emptyIngredients!!.add(newIngredient))
            assertEquals(1, emptyIngredients!!.numberOfIngredients())
            assertEquals(
                newIngredient,
                emptyIngredients!!.findIngredient(emptyIngredients!!.numberOfIngredients() - 1)
            )
        }
    }

    @Nested
    inner class ListIngredients {

        @Test
        fun `listAllIngredients returns None when internal ArrayList is empty`() {
            assertEquals(0, emptyIngredients!!.numberOfIngredients())
            assertTrue(emptyIngredients!!.listAllIngredients().lowercase().contains("none"))
        }

        @Test
        fun `listAllIngredients returns ingredients when internal ArrayList is populated`() {
            assertEquals(numCakeIngredients, cakeIngredients!!.numberOfIngredients())
            val ingredientListStr = cakeIngredients!!.listAllIngredients()
            assertTrue(ingredientListStr.contains(oil!!.ingredientName))
            assertTrue(ingredientListStr.contains(flour!!.ingredientName))
            assertTrue(ingredientListStr.contains(cocoa!!.ingredientName))
            assertTrue(ingredientListStr.contains(bicarbSoda!!.ingredientName))
            assertTrue(ingredientListStr.contains(sugar!!.ingredientName))
            assertTrue(ingredientListStr.contains(milk!!.ingredientName))
            assertTrue(ingredientListStr.contains(eggs!!.ingredientName))
            assertTrue(ingredientListStr.contains(syrup!!.ingredientName))
        }

        @Test
        fun `findIngredient returns null when index does not exist`() {
            assertNull(emptyIngredients!!.findIngredient(0))
            assertNull(cakeIngredients!!.findIngredient(-1))
            assertNull(cakeIngredients!!.findIngredient(numCakeIngredients))
        }

        @Test
        fun `findIngredient returns ingredient when it does exist`() {
            assertEquals(oil, cakeIngredients!!.findIngredient(0))
        }
    }

    @Nested
    inner class DeleteIngredients {

        @Test
        fun `delete returns null when Ingredient does not exist`() {
            assertNull(emptyIngredients!!.delete(0))
            assertNull(cakeIngredients!!.delete(-1))
            assertNull(cakeIngredients!!.delete(numCakeIngredients))
        }

        @Test
        fun `deleting an ingredient that exists returns that ingredient and removes it from the internal ArrayList`() {
            assertEquals(numCakeIngredients, cakeIngredients!!.numberOfIngredients())
            assertEquals(oil, cakeIngredients!!.delete(0))
            assertEquals(numCakeIngredients - 1, cakeIngredients!!.numberOfIngredients())
        }
    }

    @Nested
    inner class UpdateIngredient {

        @Test
        fun `updating an ingredient that does not exist returns false`() {
            val newIngredient = Ingredient("unsalted butter", "100g")
            assertFalse(emptyIngredients!!.update(0, newIngredient))
            assertFalse(cakeIngredients!!.update(-1, newIngredient))
            assertFalse(cakeIngredients!!.update(numCakeIngredients, newIngredient))
        }

        @Test
        fun `updating an ingredient with an ingredient that does not exist returns false`() {
            val newIngredient = null
            assertFalse(cakeIngredients!!.update(0, newIngredient))
        }

        @Test
        fun `updating an ingredient that does exist returns true and updates`() {
            val newOilName = oil!!.copy(ingredientName = "Canola Oil")
            val newOilNameAndQuantity = newOilName.copy(ingredientName = "Coconut Oil", ingredientQuantity = "120 ml")

            // Check ingredient matches before update
            assertEquals(oil, cakeIngredients!!.findIngredient(0))
            assertEquals(oil!!.ingredientName, cakeIngredients!!.findIngredient(0)!!.ingredientName)
            assertEquals(oil!!.ingredientQuantity, cakeIngredients!!.findIngredient(0)!!.ingredientQuantity)

            // Check ingredient matches after updates
            assertTrue(cakeIngredients!!.update(0, newOilName))
            assertEquals(newOilName.ingredientName, cakeIngredients!!.findIngredient(0)!!.ingredientName)
            assertTrue(cakeIngredients!!.update(0, newOilNameAndQuantity))
            assertEquals(
                newOilNameAndQuantity.ingredientName,
                cakeIngredients!!.findIngredient(0)!!.ingredientName
            )
            assertEquals(
                newOilNameAndQuantity.ingredientQuantity,
                cakeIngredients!!.findIngredient(0)!!.ingredientQuantity
            )
        }
    }
}
