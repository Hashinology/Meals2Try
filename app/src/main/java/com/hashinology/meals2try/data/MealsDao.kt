package com.hashinology.meals2try.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: Meal)

    @Delete
    suspend fun deletedMeals(meals: Meal)

    @Query("Select * from Meals_table")
    fun getDbMeals(): LiveData<List<Meal>>
}