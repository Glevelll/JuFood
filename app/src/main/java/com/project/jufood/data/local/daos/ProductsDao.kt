package com.project.jufood.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.jufood.data.local.entities.Products
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert
    suspend fun insertProduct(product: Products)

    @Delete
    suspend fun deleteProduct(product: Products)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Products>>

    @Query("SELECT * FROM products WHERE name LIKE :keyword OR date_prod LIKE :keyword")
    fun searchProducts(keyword: String): Flow<List<Products>>

    @Query("SELECT * FROM products WHERE name LIKE :keyword AND date_prod LIKE :keyword")
    fun searchProductsByNameAndDate(keyword: String): Flow<List<Products>>
}