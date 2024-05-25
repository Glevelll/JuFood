package com.project.jufood

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.project.jufood.data.Ingredients
import com.project.jufood.data.RecipeType
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.data.daos.RecipesDao
import com.project.jufood.data.entities.Recipes
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RecipesDaoTest {

    private lateinit var recipesDao: RecipesDao
    private lateinit var recipesDatabase: RecipesDatabase

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        recipesDatabase = Room.inMemoryDatabaseBuilder(appContext, RecipesDatabase::class.java).build()
        recipesDao = recipesDatabase.recipesDao()
    }

    @After
    fun cleanup() {
        recipesDatabase.close()
    }

    @Test
    fun testUpdateFavoriteStatus() = runBlocking {
        val recipe = Recipes(id_rec = 1, name = "Test Recipe", type = RecipeType.Завтраки, time = "30 mins",
            ingredients = listOf(Ingredients("Ingredient 1", "100g")), description = "Test Description",
            favorite = false, imageResId = R.drawable.recipe1)
        recipesDao.insertRecipe(recipe)

        recipesDao.updateFavoriteStatus(1, true)
        var updatedRecipe = recipesDao.getRecipeById(1)
        assertEquals(true, updatedRecipe?.favorite)

        recipesDao.updateFavoriteStatus(1, false)
        updatedRecipe = recipesDao.getRecipeById(1)
        assertEquals(false, updatedRecipe?.favorite)
    }
}
