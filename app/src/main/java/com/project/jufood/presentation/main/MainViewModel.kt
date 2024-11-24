package com.project.jufood.presentation.main

import android.app.Activity
import android.widget.Toast
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Created
import com.project.jufood.data.local.entities.Products
import com.project.jufood.data.local.entities.Recipes
import com.project.jufood.domain.util.convertByteArrayToImageBitmap
import com.project.jufood.domain.util.getCurrentDateDisplay
import com.project.jufood.domain.util.getCurrentDateQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val db: RecipesDatabase) : ViewModel() {

    // Рецепты
    private val _recipeItems = MutableStateFlow<List<Recipes>>(emptyList())
    val recipeItems: StateFlow<List<Recipes>> get() = _recipeItems

    // Состояние для отображаемой даты
    private val _displayDate = MutableStateFlow(getCurrentDateDisplay())
    val displayDate: StateFlow<String> get() = _displayDate

    // Состояние для даты запроса (формат базы данных)
    private val _queryDate = MutableStateFlow(getCurrentDateQuery())
    val queryDate: StateFlow<String> get() = _queryDate

    // Состояние списка карточек
    private val _cardItems = MutableStateFlow<List<Pair<String, Int>>>(emptyList())
    val cardItems: StateFlow<List<Pair<String, Int>>> get() = _cardItems

    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products: StateFlow<List<Products>> get() = _products

    // Состояние для текстовых полей
    private val _text1 = MutableStateFlow(TextFieldValue(""))
    val text1: StateFlow<TextFieldValue> get() = _text1

    private val _text2 = MutableStateFlow(TextFieldValue(""))
    val text2: StateFlow<TextFieldValue> get() = _text2

    private val _mineItems = MutableStateFlow<List<Created>>(emptyList())
    val mineItems: StateFlow<List<Created>> get() = _mineItems

    private val _favoriteRecipes = MutableStateFlow<List<Recipes>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipes>> get() = _favoriteRecipes

    // Избранное
    init {
        loadFavoriteRecipes()
    }

    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            db.recipesDao().getFavoriteRecipes().collect { recipes ->
                _favoriteRecipes.value = recipes
            }
        }
    }

    fun toggleFavoriteStatus(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            db.recipesDao().updateFavoriteStatus(recipeId, isFavorite)
            loadFavoriteRecipes()
        }
    }


    // Календарь
    init {
        // Загружаем карточки для текущей даты при инициализации
        loadPlansByDate(_queryDate.value)
    }

    // Функция для обновления даты
    fun updateDate(displayDate: String, queryDate: String) {
        _displayDate.value = displayDate
        _queryDate.value = queryDate
        loadPlansByDate(queryDate) // Загружаем карточки для новой даты
    }

    // Функция для загрузки карточек на основе даты
    private fun loadPlansByDate(date: String) {
        viewModelScope.launch {
            val plans = db.planDao().getPlansByDate(date)
            val newCardItems = mutableListOf<Pair<String, Int>>()

            for (plan in plans) {
                plan.recipesFk?.let { recipesId ->
                    val recipe = db.recipesDao().getRecipeById(recipesId)
                    recipe?.let { newCardItems.add(it.name to plan.id) }
                }
                plan.createdFk?.let { createdId ->
                    val createdRecipe = db.createdDao().getCreatedById(createdId)
                    createdRecipe?.let { newCardItems.add(it.name to plan.id) }
                }
            }

            _cardItems.value = newCardItems
        }
    }

    // Функция для удаления плана
    fun deletePlan(planId: Int) {
        viewModelScope.launch {
            val plan = db.planDao().getPlansByDate(_queryDate.value).find { it.id == planId }
            if (plan != null) {
                db.planDao().delete(plan)
                loadPlansByDate(_queryDate.value) // Перезагружаем карточки
            }
        }
    }

    // Функция для получения деталей плана
    suspend fun getPlanDetails(planId: Int): Pair<Int, Boolean>? {
        val plan = db.planDao().getPlansByDate(_queryDate.value).find { it.id == planId }
        return plan?.let {
            val recipeId = it.recipesFk ?: it.createdFk ?: -1
            val isCreated = it.createdFk != null
            recipeId to isCreated
        }
    }

    // Продукты
    init {
        // Загружаем продукты из базы данных при инициализации
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            db.productsDao().getAllProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    // Обновление текста первого поля
    fun updateText1(value: TextFieldValue) {
        _text1.value = value
    }

    // Обновление текста второго поля
    fun updateText2(value: TextFieldValue) {
        _text2.value = value
    }

    // Добавление нового продукта
    fun addProduct(activity: Activity) {
        viewModelScope.launch {
            // Валидация
            if (_text1.value.text.isEmpty()) {
                Toast.makeText(activity, "Введите название продукта", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (_text2.value.text.isEmpty()) {
                Toast.makeText(activity, "Введите дату", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (_text2.value.text.length != 5) {
                Toast.makeText(activity, "Введите корректную дату в формате дд.мм", Toast.LENGTH_SHORT).show()
                return@launch
            }

            // Создание нового продукта
            val newProduct = Products(name = _text1.value.text, date_prod = _text2.value.text)
            db.productsDao().insertProduct(newProduct)

            // Очистка текстовых полей
            _text1.value = TextFieldValue("")
            _text2.value = TextFieldValue("")
        }
    }

    // Удаление продукта
    fun deleteProduct(product: Products) {
        viewModelScope.launch {
            db.productsDao().deleteProduct(product)
        }
    }

    // Мое
    init {
        loadMineItems()
    }

    private fun loadMineItems() {
        viewModelScope.launch {
            db.createdDao().getAllCreated().collect { items ->
                _mineItems.value = items
            }
        }
    }

    fun deleteCreated(recipe: Created) {
        viewModelScope.launch {
            db.createdDao().deleteCreated(recipe)
        }
    }

    fun convertImage(image: ByteArray?): ImageBitmap? {
        return image?.let { convertByteArrayToImageBitmap(it) }
    }


    // Рецепты
    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            db.recipesDao()
                .getAllRecipes()
                .collect { recipes ->
                    _recipeItems.value = recipes
                }
        }
    }

    fun toggleFavoriteStatusRecipe(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            db.recipesDao().updateFavoriteStatus(recipeId, isFavorite)
        }
    }
}