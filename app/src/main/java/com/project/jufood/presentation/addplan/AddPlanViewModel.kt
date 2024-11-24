package com.project.jufood.presentation.addplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Created
import com.project.jufood.data.local.entities.Plan
import com.project.jufood.data.local.entities.Products
import com.project.jufood.data.local.entities.Recipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddPlanViewModel(private val db: RecipesDatabase) : ViewModel(), AddPlanInterface {

    private val _filteredRecipes = MutableStateFlow<List<Recipes>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipes>> get() = _filteredRecipes

    private val _favoriteRecipes = MutableStateFlow<List<Recipes>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipes>> get() = _favoriteRecipes

    private val _createdRecipes = MutableStateFlow<List<Created>>(emptyList())
    val createdRecipes: StateFlow<List<Created>> get() = _createdRecipes

    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products: StateFlow<List<Products>> get() = _products

    private val _filteredRecipesByProducts = MutableStateFlow<List<Recipes>>(emptyList())
    val filteredRecipesByProducts: StateFlow<List<Recipes>> get() = _filteredRecipesByProducts

    private val _selectedRecipes = MutableStateFlow<List<Any>>(emptyList())
    val selectedRecipes: StateFlow<List<Any>> get() = _selectedRecipes

    private val _buttonStates = MutableStateFlow<List<Boolean>>(emptyList())
    val buttonStates: StateFlow<List<Boolean>> get() = _buttonStates

    init {
        loadRecipes()
        loadCreatedRecipes()
        loadFavoriteRecipes()
        loadProducts()
    }

    override fun loadRecipes() {
        viewModelScope.launch {
            db.recipesDao().getAllRecipes().collect { recipes ->
                _filteredRecipes.value = recipes
                filterRecipesByProducts()
            }
        }
    }

    override fun loadFavoriteRecipes() {
        viewModelScope.launch {
            db.recipesDao().getFavoriteRecipes().collect { favorites ->
                _favoriteRecipes.value = favorites
            }
        }
    }

    override fun loadCreatedRecipes() {
        viewModelScope.launch {
            db.createdDao().getAllCreated().collect { created ->
                _createdRecipes.value = created
            }
        }
    }

    override fun loadProducts() {
        viewModelScope.launch {
            db.productsDao().getAllProducts().collect { products ->
                _products.value = products
                filterRecipesByProducts()
            }
        }
    }

    override fun filterRecipesByProducts() {
        val currentRecipes = _filteredRecipes.value
        val currentProducts = _products.value

        val filtered = currentRecipes.filter { recipe ->
            recipe.ingredients.any { ingredient ->
                currentProducts.any { product ->
                    product.name == ingredient.title
                }
            }
        }

        _filteredRecipesByProducts.value = filtered
        updateButtonStates()
    }

    override fun updateButtonStates() {
        val allRecipes = _favoriteRecipes.value + _createdRecipes.value + _filteredRecipesByProducts.value
        _buttonStates.value = List(allRecipes.size) { false }
    }

    override fun toggleButtonState(index: Int) {
        val currentStates = _buttonStates.value.toMutableList()
        val currentSelected = _selectedRecipes.value.toMutableList()
        val allRecipes = _favoriteRecipes.value + _createdRecipes.value + _filteredRecipesByProducts.value

        currentStates[index] = !currentStates[index]
        if (currentStates[index]) {
            currentSelected += allRecipes[index]
        } else {
            currentSelected -= allRecipes[index]
        }

        _buttonStates.value = currentStates
        _selectedRecipes.value = currentSelected
    }

    override fun addPlan(selectedDate: String, recipe: Any) {
        viewModelScope.launch {
            when (recipe) {
                is Recipes -> {
                    val plan = Plan(date = selectedDate, recipesFk = recipe.id_rec, createdFk = null)
                    db.planDao().insert(plan)
                }
                is Created -> {
                    val plan = Plan(date = selectedDate, recipesFk = null, createdFk = recipe.id_cre)
                    db.planDao().insert(plan)
                }
                else -> {
                    throw IllegalArgumentException("Unsupported recipe type")
                }
            }
        }
    }
}
