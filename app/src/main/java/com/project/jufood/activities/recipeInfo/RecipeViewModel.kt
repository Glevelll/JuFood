package com.project.jufood.activities.recipeInfo

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.data.entities.Created
import com.project.jufood.data.entities.Recipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RecipeViewModel(private val db: RecipesDatabase) : ViewModel() {
    private val _createdRecipe = MutableStateFlow<Created?>(null)
    val createdRecipe: StateFlow<Created?> get() = _createdRecipe

    private val _recipe = MutableStateFlow<Recipes?>(null)
    val recipe: StateFlow<Recipes?> get() = _recipe

    fun loadCreatedRecipe(recipeId: Int) {
        viewModelScope.launch {
            _createdRecipe.value = db.createdDao().getCreatedById(recipeId)
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _recipe.value = db.recipesDao().getRecipeById(recipeId)
        }
    }

    fun updateFavoriteStatus(recipeId: Int, favorite: Boolean) {
        viewModelScope.launch {
            db.recipesDao().updateFavoriteStatus(recipeId, favorite)
            _recipe.value = _recipe.value?.copy(favorite = favorite)
        }
    }
}


