package com.project.jufood.presentation.createRecipe

import com.project.jufood.data.local.entities.Created

interface CreateRecipeInterface {
    fun addIngredient(ingredient: String, quantity: String)
    fun removeIngredient(ingredient: String, quantity: String)
    fun insertRecipe(recipe: Created, onSuccess: () -> Unit)
}