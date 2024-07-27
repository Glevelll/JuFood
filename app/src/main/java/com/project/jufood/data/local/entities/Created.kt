package com.project.jufood.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.jufood.data.local.Ingredients

@Entity(tableName = "created")
data class Created(
    @PrimaryKey(autoGenerate = true) val id_cre: Int = 0,
    val name: String,
    val time: String,
    val ingredients: List<Ingredients>,
    val description: String,
    val image: ByteArray?
)