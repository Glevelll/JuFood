package com.project.jufood.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.jufood.data.local.entities.Created
import kotlinx.coroutines.flow.Flow

@Dao
interface CreatedDao {

    @Insert
    suspend fun insertCreated(created: Created)

    @Query("SELECT * FROM created")
    fun getAllCreated(): Flow<List<Created>>

    @Query("SELECT * FROM created WHERE name = :name")
    suspend fun getCreatedByName(name: String): Created?


    @Query("SELECT * FROM created WHERE id_cre = :id")
    suspend fun getCreatedById(id: Int): Created?

    @Delete
    suspend fun deleteCreated(created: Created)
}
