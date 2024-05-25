package com.project.jufood.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.jufood.data.entities.Plan

@Dao
interface PlanDao {

    @Insert
    suspend fun insert(plan: Plan)

    @Query("SELECT * FROM plans")
    suspend fun getAllPlans(): List<Plan>

    @Query("SELECT * FROM plans WHERE date = :date")
    suspend fun getPlansByDate(date: String): List<Plan>

    @Delete
    suspend fun delete(plan: Plan)
}

