package utils

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import utils.Utilities.diets
import utils.Utilities.isPositive
import utils.Utilities.isValidDiet
import utils.Utilities.isValidMealType
import utils.Utilities.mealTypes
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilitiesTest {
    @Nested
    inner class InputValidationHelpers {
        @Test
        fun `mealTypes set contains all expected meal types`() {
            assertEquals(4, mealTypes.size)
            assertTrue(mealTypes.contains("lunch"))
            assertTrue(mealTypes.contains("dinner"))
            assertTrue(mealTypes.contains("breakfast"))
            assertTrue(mealTypes.contains("other"))
            assertFalse(mealTypes.contains(""))
        }

        @Test
        fun `isValidMealType returns true when meal type is valid`() {
            assertTrue(isValidMealType("lunch"))
            assertTrue(isValidMealType("DINNER"))
            assertTrue(isValidMealType("BreAkFast"))
            assertTrue(isValidMealType("other"))
        }

        @Test
        fun `isValidMealType returns false when meal type is not valid`() {
            assertFalse(isValidMealType("lunc"))
            assertFalse(isValidMealType("cakes"))
        }

        @Test
        fun `diets set contains all expected diet types`() {
            assertEquals(3, diets.size)
            assertTrue(diets.contains("vegan"))
            assertTrue(diets.contains("vegetarian"))
            assertTrue(diets.contains("meat"))
            assertFalse(diets.contains(""))
        }

        @Test
        fun `isValidDiet returns true when meal type is valid`() {
            assertTrue(isValidDiet("vegan"))
            assertTrue(isValidDiet("VEGETARIAN"))
            assertTrue(isValidDiet("mEAt"))
        }

        @Test
        fun `isValidDiet returns false when meal type is not valid`() {
            assertFalse(isValidDiet("fish"))
            assertFalse(isValidDiet("cakes"))
        }

        @Test
        fun `isPositive returns true when number is 0 or larger`() {
            assertTrue(isPositive(0))
            assertTrue(isPositive(10))
            assertTrue(isPositive(30213))
        }

        @Test
        fun `isPositive returns false when number is less than 0`() {
            assertFalse(isPositive(-1))
            assertFalse(isPositive(-42))
        }
    }
}
