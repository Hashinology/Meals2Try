package com.hashinology.meals2try.di

import android.content.Context
import androidx.room.Room
import com.hashinology.meals2try.api.Apiservice
import com.hashinology.meals2try.data.MealsDB
import com.hashinology.meals2try.api.MyRepository
import com.hashinology.meals2try.data.MealsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    private const val BASE_URL = "https://www.themealdb.com/api/json/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideApiservice(retrofit: Retrofit): Apiservice =
        retrofit.create(Apiservice::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiservice: Apiservice, dao: MealsDao) =
        MyRepository(apiservice, dao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MealsDB::class.java,
            "Meals_Database"
        ).build()

    @Singleton
    @Provides
    fun provideDao(db: MealsDB) = db.getDao()
}