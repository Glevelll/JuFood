package com.project.jufood.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.jufood.data.entities.Created

@Dao
interface CreatedDao {

    @Insert
    suspend fun insertCreated(created: Created)

    @Query("SELECT * FROM created")
    fun getAllCreated(): LiveData<List<Created>>

    @Query("SELECT * FROM created WHERE name = :name")
    suspend fun getCreatedByName(name: String): Created?


    @Query("SELECT * FROM created WHERE id_cre = :id")
    suspend fun getCreatedById(id: Int): Created?

    @Delete
    suspend fun deleteCreated(created: Created)
}
