package com.project.jufood.presentation.addplan

interface AddPlanInterface {
    fun loadRecipes()
    fun loadCreatedRecipes()
    fun loadFavoriteRecipes()
    fun loadProducts()
    fun filterRecipesByProducts()
    fun updateButtonStates()
    fun toggleButtonState(index: Int)
    fun addPlan(selectedDate: String, recipe: Any)
}