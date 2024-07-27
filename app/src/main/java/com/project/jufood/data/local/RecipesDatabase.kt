package com.project.jufood.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.jufood.data.local.utils.Converters
import com.project.jufood.data.local.daos.CreatedDao
import com.project.jufood.data.local.daos.PlanDao
import com.project.jufood.data.local.daos.ProductsDao
import com.project.jufood.data.local.daos.RecipesDao
import com.project.jufood.data.local.entities.Created
import com.project.jufood.data.local.entities.Plan
import com.project.jufood.data.local.entities.Products
import com.project.jufood.data.local.entities.Recipes

@Database(entities = [Recipes::class, Created::class, Plan::class, Products::class], version = 2)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
    abstract fun createdDao(): CreatedDao
    abstract fun planDao(): PlanDao
    abstract fun productsDao(): ProductsDao

}

data class Ingredients(
    val title: String,
    val substring: String
)
