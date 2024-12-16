package com.first.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.first.foodapp.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal : Meal)

    @Delete
    suspend fun delete(meal : Meal)

    @Query("SELECT * FROM MealInformation")
    fun getAllMeals() : LiveData<List<Meal>>

}