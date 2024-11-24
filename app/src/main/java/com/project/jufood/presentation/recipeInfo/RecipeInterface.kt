package com.project.jufood.presentation.recipeInfo

interface RecipeInterface {
    fun loadCreatedRecipe(recipeId: Int)
    fun loadRecipe(recipeId: Int)
    fun updateFavoriteStatus(recipeId: Int, favorite: Boolean)
}