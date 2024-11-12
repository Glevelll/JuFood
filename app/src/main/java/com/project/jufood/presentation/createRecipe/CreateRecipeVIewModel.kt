package com.project.jufood.presentation.createRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Created
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateRecipeViewModel(private val db: RecipesDatabase) : ViewModel() {
    private val _ingredients = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val ingredients: StateFlow<List<Pair<String, String>>> get() = _ingredients

    fun addIngredient(ingredient: String, quantity: String) {
        _ingredients.value += (ingredient to quantity)
    }

    fun removeIngredient(ingredient: String, quantity: String) {
        _ingredients.value -= (ingredient to quantity)
    }

    fun insertRecipe(recipe: Created, onSuccess: () -> Unit) {
        viewModelScope.launch {
            db.createdDao().insertCreated(recipe)
            onSuccess()
        }
    }
}