package com.project.jufood.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.jufood.data.daos.CreatedDao
import com.project.jufood.data.daos.PlanDao
import com.project.jufood.data.daos.ProductsDao
import com.project.jufood.data.daos.RecipesDao
import com.project.jufood.data.entities.Created
import com.project.jufood.data.entities.Plan
import com.project.jufood.data.entities.Products
import com.project.jufood.data.entities.Recipes

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
