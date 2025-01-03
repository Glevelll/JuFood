package com.project.jufood.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.jufood.data.local.entities.Recipes
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {
    @Insert
    suspend fun insertRecipe(recipe: Recipes)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipes>>

    @Query("SELECT * FROM recipes WHERE id_rec = :id")
    suspend fun getRecipeById(id: Int): Recipes?

    @Query("SELECT * FROM recipes WHERE name = :name")
    suspend fun getRecipeByName(name: String): Recipes?

    @Query("UPDATE recipes SET favorite = :favorite WHERE id_rec = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Int, favorite: Boolean)

    @Query("SELECT * FROM recipes WHERE favorite = 1")
    fun getFavoriteRecipes(): Flow<List<Recipes>>

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

}

