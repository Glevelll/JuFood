package com.project.jufood.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Products(
    @PrimaryKey(autoGenerate = true) val id_prod: Int = 0,
    val name: String,
    val date_prod: String
)