package com.hashinology.meals2try.api

import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals
import com.hashinology.meals2try.data.MealsDao
import javax.inject.Inject

class MyRepository @Inject constructor(
    val apiservice: Apiservice,
    val dao: MealsDao
) {
    suspend fun getMeals() = apiservice.getMealsApi()

    suspend fun insertedMeals(meals: Meal) = dao.insertMeals(meals)

    suspend fun deletedMeals(meals: Meal) = dao.deletedMeals(meals)

    fun getDbMeals() = dao.getDbMeals()
}