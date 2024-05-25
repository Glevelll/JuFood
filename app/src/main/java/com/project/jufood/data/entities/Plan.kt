package com.project.jufood.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "plans",
    foreignKeys = [
        ForeignKey(
            entity = Recipes::class,
            parentColumns = ["id_rec"],
            childColumns = ["recipes_fk"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Created::class,
            parentColumns = ["id_cre"],
            childColumns = ["created_fk"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Plan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    @ColumnInfo(name = "recipes_fk") val recipesFk: Int?,
    @ColumnInfo(name = "created_fk") val createdFk: Int?
)

