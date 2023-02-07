package com.hashinology.meals2try.api

import com.example.hiltretrofitproject.model.Meals
import retrofit2.Response
import retrofit2.http.GET

interface Apiservice {
    @GET("v1/1/filter.php?a=Canadian")
    suspend fun getMealsApi(): Response<Meals>
}