package com.hashinology.meals2try.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals
import com.hashinology.meals2try.data.MealsDao

@Database(
    entities = [Meal::class],
    version = 1
)
abstract class MealsDB: RoomDatabase() {
    abstract fun getDao(): MealsDao
}